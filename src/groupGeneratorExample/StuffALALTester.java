import java.util.ArrayList;
import java.util.Random;

public class StuffALALTester {

	public static void main(String[] args) {
		
		ArrayList<ArrayList<Stuff>> stuffALAL = new ArrayList<ArrayList<Stuff>>();
		
		/*
		 * StuffALMaker sam1 = new StuffALMaker();
		 * System.out.println(sam1.getStuffAL()); 
		 * StuffALMaker sam2 = new
		 * StuffALMaker(); 
		 * System.out.println(sam2.getStuffAL());
		 * stuffALAL.add(sam1.getStuffAL()); stuffALAL.add(sam2.getStuffAL());
		 */
		
		//Let's do a better job of creating the test data
		//the +1 is to make sure we always have at least one array list in stuffALAL
		//The 6 is just because...I just picked it!
		
		for (int i = 0; i < new Random().nextInt(6) + 1; i++) {
			StuffALMaker sam = new StuffALMaker();
			stuffALAL.add(sam.getStuffAL());
		}
		
		
		
		
		
		//get the 3rd element of the first array list of stuff
		
		//Print out stuff neatly
		System.out.println(" All of my stuff \n --------------- ");
		//After the title, this prints each of my array lists of Stuff
		//on a single line so it is pretty
		for (ArrayList<Stuff> arrayList : stuffALAL) {
			System.out.println(arrayList);
		}
		

	}

}
