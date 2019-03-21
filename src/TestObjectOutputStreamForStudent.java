import java.io.*;

// NOTE :  This example demonstrates exception handling where user is not notified as to cause of exception 
// or problem - if there is a problem with output file- the program just ends.

public class TestObjectOutputStreamForStudent
{
    public static void main(String[] args) throws IOException
    {
        try ( // Create an output stream for file object.dat
        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream("objectstu.dat"));)
        {
            Student stu = new Student("John", 85.5, new java.util.Date());
            System.out.println(stu);
        	// Write a string, double value, and object to the file
            //output.writeUTF("John");
            //output.writeDouble(85.5);
            //output.writeUTF(new java.util.Date());
           // output.writeObject(new java.util.Date());
            output.writeObject(stu);
        }
    }
}
