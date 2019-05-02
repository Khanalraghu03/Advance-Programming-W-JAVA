import java.util.PriorityQueue;


public class HeapsOfFun {

	/**
	 * @param args
	 */
	//Speed is the advantage
	public static void main(String[] args) {
		PriorityQueue<String> names=new PriorityQueue<String>();
		names.add("John");
		names.add("James");
		names.add("Jessie");
		names.add("Jolie");
		names.add("Jam");
		while(names.size()>0)
		{
			System.out.println("top is "+names.peek());
			names.remove();
		}
	}
}
