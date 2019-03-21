import java.util.ArrayList;

public class StuffAlAlTester {
    public static void main(String[] args) {
//        ArrayList<ArrayList<Stuff>> stuffALAL;
        StuffMaker sam1 = new StuffMaker();
        System.out.println(sam1.getStuffAL());

        StuffMaker sam2 = new StuffMaker();
        System.out.println(sam2.getStuffAL());


        ArrayList<ArrayList<Stuff>> stuffALAL = new  ArrayList<ArrayList<Stuff>>();
        stuffALAL.add(sam1.getStuffAL());
        stuffALAL.add(sam2.getStuffAL());
    }
}
