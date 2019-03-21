import java.util.Random;

public class Stuff {


	private int myInt;
	private String myString;


	public Stuff() {

		Random ran = new Random();
		myInt = ran.nextInt(100);		
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder generatedString= new StringBuilder();
		for (int i = 0; i < ran.nextInt(10); i++) {
			int randonSequence = ran .nextInt(CHARACTERS.length());
			generatedString.append(CHARACTERS.charAt(randonSequence));			
		}
		myString = generatedString.toString();
	}	

	public Stuff(int myInt, String myString) {
		this.myInt = myInt;
		this.myString = myString;
	}

	public int getMyInt() {
		return myInt;
	}

	public void setMyInt(int myInt) {
		this.myInt = myInt;
	}

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	@Override
	public String toString() {
		return "Stuff [myInt=" + myInt + ", myString=" + myString + "]";
	}
}
