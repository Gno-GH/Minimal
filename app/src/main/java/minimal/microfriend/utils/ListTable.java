package minimal.microfriend.utils;

import java.util.ArrayList;

import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;

/**
 * Created by gno on 16/7/22.
 */
public class ListTable {
    public ArrayList<Trend> keys;
    public ArrayList<ArrayList<Reply>> values;
    public ListTable(){
        this.keys = new ArrayList<Trend>();
        this.values = new ArrayList<ArrayList<Reply>>();
    }
    public void add(Trend trend,ArrayList<Reply> replies){
        this.keys.add(trend);
        this.values.add(replies);
    }
}
