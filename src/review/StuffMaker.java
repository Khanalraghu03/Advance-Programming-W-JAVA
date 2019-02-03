package review;

import java.util.ArrayList;

public class StuffMaker {
    private ArrayList<Stuff> stuffAL;
    public  StuffMaker() {

        stuffAL = new ArrayList<Stuff>();
//        Stuff s = new Stuff();
//        stuffAL.add(s);

        for(int i = 0; i < 5; i++) {
            stuffAL.add(new Stuff());
        }
        //add all the stuff in the ArrayList
//        stuffAL.add(stuff1);
//        stuffAL.add(stuff2);
//        stuffAL.add(stuff3);
//        stuffAL.add(stuff4);
//
//        System.out.println(stuffAL);

    }
    public ArrayList<Stuff> getStuffAL() {
        return stuffAL;
    }
}
