import java.io.*;

// NOTE This class demonstrates good exception handling

public class TestObjectInputStudentWithLoopAndClose
{
	private ObjectInputStream input; 

	public TestObjectInputStudentWithLoopAndClose(String fileName) {
		try {
			this.input = new ObjectInputStream(new FileInputStream(fileName));
		}
		catch (IOException i) {

			System.out.println("Unable to read from file");
			i.printStackTrace();
		}

	}
	public static void main(String[] args) {

		TestObjectInputStudentWithLoopAndClose tois = new TestObjectInputStudentWithLoopAndClose("objectstudent.dat");

		try
		{ // Create an input stream for file object.dat


			while (true) {

				Student s = (Student) tois.input.readObject();
				System.out.println(s);				
			}
		} catch (EOFException eofe) {		

			System.out.println("end of file");
			//eofe.printStackTrace();
		}
		catch (IOException i) {
			System.out.println("Unable to read from file");
			i.printStackTrace();			
		} catch (ClassNotFoundException c) {
			System.out.println("Object read is not a java.util.Date instance");
		}
		try {
			tois.input.close();
		}
		catch (IOException ioe) {
			System.out.println("unable to close file");
		}
		
	}
}

