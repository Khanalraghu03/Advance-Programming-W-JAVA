import java.io.*;

// NOTE This class demonstrates good exception handling

public class TestObjectInputStudent
{
	public static void main(String[] args)
	// throws ClassNotFoundException, IOException
	{
		try
		{ // Create an input stream for file object.dat
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream("objectstudent.dat"));

			while (true) {
				// Read a string, double value, and object from the file
				// String name = input.readUTF();
				// double score = input.readDouble();
				//java.util.Date date = (java.util.Date) (input.readObject());
				//System.out.println(input.available());
				Student s = (Student) input.readObject();
				System.out.println(s);

				//System.out.println(input.available());
			}
			
		} catch (EOFException eofe) {
			//input.close();

				System.out.println("end of file");
				//eofe.printStackTrace();
			} 

		catch (IOException i)
		{
			System.out.println("Unable to read from file");
			i.printStackTrace();
		} catch (ClassNotFoundException c)
		{
			System.out.println("Object read is not a java.util.Date instance");
		}

	}
}
