import java.io.*;
import java.util.Date;

// NOTE :  This example demonstrates exception handling where user is not notified as to cause of exception 
// or problem - if there is a problem with output file- the program just ends.

public class TestObjectOutputStudent
{
    public static void main(String[] args) throws IOException
    {
        try ( // Create an output stream for file object.dat
        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream("objectstudent.dat"));)
        {
            // Write a string, double value, and object to the file
        	
        	Student s0 = new Student("Evelyn", 100, new Date());
            //output.writeUTF("John");
            //output.writeDouble(85.5);
            //output.writeUTF(new java.util.Date());
            //output.writeObject(new java.util.Date());
        	output.writeObject(s0);
          	Student s1 = new Student("Hasan", 99.9, new Date());
          	output.writeObject(s1);
        }
    }
}
