package minimal.microfriend.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import minimal.microfriend.R;
import minimal.microfriend.activity.OwnerTrendsActivity;
import minimal.microfriend.entry.Interaction;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.pager.CenterPager;
import minimal.microfriend.utils.ListTable;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.AutoListView;
import minimal.microfriend.view.CricularView;

/**
 * 动态列表适配器
 */
public class TrendsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Trend> trends;
    private ListTable allreplies;
    private User user;
    private PopupWindow mComment;
    private View popView;
    private LinearLayout ll_pop;
    private Reply reply;
    private CenterPager centerPager;
    private OwnerTrendsActivity activity;
    private int model ;
    public TrendsAdapter(Context context, ListTable allreplies, User user, LinearLayout ll_pop,CenterPager centerPager,OwnerTrendsActivity activity,int model) {
        this.context = context;
        this.allreplies = allreplies;
        this.user = user;
        this.ll_pop = ll_pop;
        this.centerPager = centerPager;
        this.activity = activity;
        this.model = model;
        trends = new ArrayList<Trend>();
        for (Trend o : allreplies.keys) {
            trends.add((Trend) o);
        }
    }

    @Override
    public int getCount() {
        return trends.size();
    }

    @Override
    public Object getItem(int position) {
        return trends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO:缓存错位BUG
        ViewHolder holder;
        convertView = View.inflate(this.context, R.layout.center_listview_item, null);
        holder = new ViewHolder();
        //查找ID
        findViewId(position, convertView, holder);
        if (!trends.get(position).getCreateUser().getObjectId().equals(user.getObjectId()))
            holder.del_ib.setVisibility(View.INVISIBLE);
        //处理单击事件
        childOnClick(holder, position);
        if (trends.get(position).getContentImg() != null) {
            holder.context_image.setVisibility(View.VISIBLE);
            downAndSetImg(position, holder);
        }
        //设置适配器

        holder.adapter = new ReplyAdapter(this.context, allreplies.getReplys(trends.get(position)), ll_pop, user, trends.get(position));
        holder.context_lv.setAdapter(holder.adapter);
        holder.context_text.setText(trends.get(position).getContentText());
        holder.create_time.setText(trends.get(position).getCreatedAt().toString());
        holder.user_name.setText(trends.get(position).getCreateUser().getPetname());
        setInteractionNumber(position, holder);
        return convertView;
    }

    private void downAndSetImg(int position, final ViewHolder holder) {
        File img = new File(context.getCacheDir() + "/bmob/" +
                trends.get(position).getContentImg().getFilename());
        if (img.exists()) {
            Drawable dimg = Drawable.createFromPath(img.getPath());
            holder.context_image.setBackground(dimg);
        } else {
            trends.get(position).getContentImg().download(context, new DownloadFileListener() {
                @Override
                public void onSuccess(String s) {
                    Drawable dimg = Drawable.createFromPath(s);
                    holder.context_image.setBackground(dimg);
                }

                @Override
                public void onFailure(int i, String s) {
                    MicroTools.toast(context, "网络错误");
                }
            });
        }
    }

    /**
     * 设置喜欢和不喜欢的数量
     *
     * @param position
     * @param holder
     */
    private void setInteractionNumber(int position, ViewHolder holder) {
        if (trends.get(position).getLike() > 0 && trends.get(position).getLike() < 99)
            holder.tv_like_number.setText(trends.get(position).getLike() + "");
        else if (trends.get(position).getLike() == 0)
            holder.tv_like_number.setText("");
        else
            holder.tv_like_number.setText("99+");
        if (trends.get(position).getDislike() > 0 && trends.get(position).getDislike() < 99)
            holder.tv_dislike_number.setText(trends.get(position).getDislike() + "");
        else if (trends.get(position).getDislike() == 0)
            holder.tv_dislike_number.setText("");
        else
            holder.tv_dislike_number.setText("99+");
    }

    private void childOnClick(final ViewHolder holder, final int position) {
        //删除
        holder.del_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trends.get(position).delete(context, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        if(model == 0)centerPager.replyRefrensh();
                        else activity.replyRefrensh();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(context,"删除失败"+s);
                    }
                });
            }
        });
        //评论
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindows();
                ImageButton pop_close = (ImageButton) popView.findViewById(R.id.pop_close);
                pop_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mComment.dismiss();
                    }
                });
                final EditText et_context = (EditText) popView.findViewById(R.id.et_context);
                Button send_yes = (Button) popView.findViewById(R.id.send_yes);
                send_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reply = new Reply();
                        reply.setObserver(user);
                        reply.setReceiver(trends.get(position).getCreateUser());
                        reply.setIsfrist(true);
                        if (et_context.getText().toString().trim().equals("")) {
                            et_context.setText("输入不能为空");
                            return;
                        }
                        reply.setReplycontent(et_context.getText().toString().trim());
                        reply.setTrend(trends.get(position));
                        reply.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                allreplies.values.get(position).add(reply);
                                mComment.dismiss();
                                holder.adapter.notifyDataSetChanged();
                                TrendsAdapter.this.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                MicroTools.toast(context, s);
                            }
                        });
                    }
                });
            }
        });
        //喜欢
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike(position, holder);
            }
        });
        //讨厌
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDisLike(position, holder);
            }
        });
    }

    private void setDisLike(final int position, final ViewHolder holder) {
        //先查讨厌是否已经存在
        BmobQuery<Interaction> interactionBmobQuery = new BmobQuery<Interaction>();
        interactionBmobQuery.include("trend");
        interactionBmobQuery.include("user");
        interactionBmobQuery.addWhereEqualTo("trend", trends.get(position));
        interactionBmobQuery.addWhereEqualTo("user", user);
        interactionBmobQuery.addWhereEqualTo("type", 0);
        interactionBmobQuery.findObjects(context, new FindListener<Interaction>() {
            @Override
            public void onSuccess(List<Interaction> list) {
                if (list.size() == 1) {
                    MicroTools.toast(context, "亲!别那么绝情嘛!");
                    return;
                }
                //没讨厌过
                Interaction interactionlike = new Interaction();
                interactionlike.setTrend(trends.get(position));
                interactionlike.setUser(user);
                interactionlike.setType(0);
                interactionlike.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //成功后更新帖子 喜欢数量和不喜欢数量
                        BmobQuery<Interaction> interactionBmobQuery = new BmobQuery<Interaction>();
                        interactionBmobQuery.include("trend");
                        interactionBmobQuery.include("user");
                        interactionBmobQuery.addWhereEqualTo("trend", trends.get(position));
                        interactionBmobQuery.addWhereEqualTo("user", user);
                        interactionBmobQuery.addWhereEqualTo("type", 1);
                        interactionBmobQuery.findObjects(context, new FindListener<Interaction>() {
                            @Override
                            public void onSuccess(List<Interaction> list) {
                                if (list.size() == 1) {
                                    list.get(0).delete(context, new DeleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            trends.get(position).setLike(trends.get(position).getLike() - 1);
                                            trends.get(position).setDislike(trends.get(position).getDislike() + 1);
                                            trends.get(position).update(context, new UpdateListener() {
                                                @Override
                                                public void onSuccess() {
                                                    setInteractionNumber(position, holder);
                                                    MicroTools.toast(context, "再接再厉!");
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    MicroTools.toast(context, s);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            MicroTools.toast(context, s);
                                        }
                                    });

                                } else {
                                    trends.get(position).setDislike(trends.get(position).getDislike() + 1);
                                    trends.get(position).update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            setInteractionNumber(position, holder);
                                            MicroTools.toast(context, "再接再厉");
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            MicroTools.toast(context, s);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                MicroTools.toast(context, s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(context, s);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, s);
            }
        });
    }

    private void setLike(final int position, final ViewHolder holder) {
        //先查喜欢是否已经存在
        BmobQuery<Interaction> interactionBmobQuery = new BmobQuery<Interaction>();
        interactionBmobQuery.include("trend");
        interactionBmobQuery.include("user");
        interactionBmobQuery.addWhereEqualTo("trend", trends.get(position));
        interactionBmobQuery.addWhereEqualTo("user", user);
        interactionBmobQuery.addWhereEqualTo("type", 1);
        interactionBmobQuery.findObjects(context, new FindListener<Interaction>() {
            @Override
            public void onSuccess(List<Interaction> list) {
                if (list.size() == 1) {
                    MicroTools.toast(context, "亲!你已经赞过了哦!");
                    return;
                }
                //没赞过
                Interaction interactionlike = new Interaction();
                interactionlike.setTrend(trends.get(position));
                interactionlike.setUser(user);
                interactionlike.setType(1);
                interactionlike.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //成功后更新帖子 喜欢数量和不喜欢数量
                        BmobQuery<Interaction> interactionBmobQuery = new BmobQuery<Interaction>();
                        interactionBmobQuery.include("trend");
                        interactionBmobQuery.include("user");
                        interactionBmobQuery.addWhereEqualTo("trend", trends.get(position));
                        interactionBmobQuery.addWhereEqualTo("user", user);
                        interactionBmobQuery.addWhereEqualTo("type", 0);
                        interactionBmobQuery.findObjects(context, new FindListener<Interaction>() {
                            @Override
                            public void onSuccess(List<Interaction> list) {
                                if (list.size() == 1) {
                                    list.get(0).delete(context, new DeleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            trends.get(position).setLike(trends.get(position).getLike() + 1);
                                            trends.get(position).setDislike(trends.get(position).getDislike() - 1);
                                            trends.get(position).update(context, new UpdateListener() {
                                                @Override
                                                public void onSuccess() {
                                                    setInteractionNumber(position, holder);
                                                    MicroTools.toast(context, "哎呦!不错哦!");
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    MicroTools.toast(context, s);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            MicroTools.toast(context, s);
                                        }
                                    });
                                } else {
                                    trends.get(position).setLike(trends.get(position).getLike() + 1);
                                    trends.get(position).update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            setInteractionNumber(position, holder);
                                            MicroTools.toast(context, "哎呦!不错哦!");
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            MicroTools.toast(context, s);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                MicroTools.toast(context, s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(context, s);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, s);
            }
        });
    }

    private void initPopWindows() {
        popView = View.inflate(context, R.layout.pop_comment, null);
        mComment = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mComment.setBackgroundDrawable(new BitmapDrawable());
        mComment.setOutsideTouchable(true);
        mComment.showAtLocation(ll_pop, Gravity.CENTER, 0, 0);
    }

    /**
     * 查找子控件
     *
     * @param position
     * @param convertView
     * @param holder
     */
    private void findViewId(int position, View convertView, ViewHolder holder) {
        holder.head_iv = (CricularView) convertView.findViewById(R.id.head_iv);
        holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
        holder.create_time = (TextView) convertView.findViewById(R.id.create_time);
        holder.del_ib = (ImageButton) convertView.findViewById(R.id.del_ib);
        holder.comment = (ImageButton) convertView.findViewById(R.id.comment);
        holder.dislike = (ImageButton) convertView.findViewById(R.id.dislike);
        holder.like = (ImageButton) convertView.findViewById(R.id.like);
        holder.context_text = (TextView) convertView.findViewById(R.id.context_text);
        holder.context_image = (ImageView) convertView.findViewById(R.id.context_image);
        holder.context_lv = (AutoListView) convertView.findViewById(R.id.context_lv);
        holder.tv_like_number = (TextView) convertView.findViewById(R.id.tv_like_number);
        holder.tv_dislike_number = (TextView) convertView.findViewById(R.id.tv_dislike_number);
    }

    class ViewHolder {
        public CricularView head_iv;//头像
        public TextView user_name;//昵称
        public TextView create_time;//创建时间
        public ImageButton del_ib;//删除按钮
        public ImageButton comment;//回复按钮
        public ImageButton dislike;//扔鸡蛋
        public ImageButton like;//喜欢
        public TextView context_text;//文本内容
        public ImageView context_image;//图片内容
        public AutoListView context_lv;//评论集合
        public ReplyAdapter adapter;
        public TextView tv_like_number, tv_dislike_number;
    }
}