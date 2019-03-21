import java.util.ArrayList;

public class StuffALMaker {

	
	private ArrayList<Stuff> stuffAL;
	
	public StuffALMaker() {
		stuffAL = new ArrayList<Stuff>();	
		
		for (int i = 0; i < 5; i++) {
			stuffAL.add(new Stuff());			
		}		
		
		//System.out.println(stuffAL);		
	}

	public ArrayList<Stuff> getStuffAL() {
		return stuffAL;
	}
}
