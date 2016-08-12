package minimal.microfriend.entry;

import java.util.ArrayList;

/**
 * Created by gno on 16/8/10.
 */
public class OwnerClass {
    public ArrayList<EveryDay> weekclass;

    public class EveryDay {
        public String date;
        public ArrayList<EveryPart> dayclass;
    }

    public class EveryPart {
        public String classname;
        public String classsite;
        public String classoe;
        public String classnumber;
        public String classstart;
        public String classend;
        public String classteacher;
    }
}
