package review;

import java.util.ArrayList;

public class SameEndsTester {
    public static void main(String[] args) {
        ArrayList<Integer> sameEnds = new ArrayList<>();

//        sameEnds.add(1); //1 4 9 10 11 12 1 4 9
//        sameEnds.add(4);
//        sameEnds.add(9);
//        sameEnds.add(10);
//        sameEnds.add(11);
//        sameEnds.add(12);
//        sameEnds.add(1);
//        sameEnds.add(4);
//        sameEnds.add(9);
        sameEnds.add(1);
        sameEnds.add(4);
        sameEnds.add(2);


        SameEnds se = new SameEnds(sameEnds);
        System.out.println(se.getMatch());

    }
}
