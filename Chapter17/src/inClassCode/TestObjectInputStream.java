package inClassCode;

import java.io.*;

// NOTE This class demonstrates good exception handling

public class TestObjectInputStream
{
    public static void main(String[] args)
    // throws ClassNotFoundException, IOException
    {
        try
        { // Create an input stream for file object.dat
            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream("object.dat"));

            // Read a string, double value, and object from the file
            String name = input.readUTF();
            double score = input.readDouble();
            java.util.Date date = (java.util.Date) (input.readObject());
            System.out.println(name + " " + score + " " + date);
        } catch (IOException i)
        {
            System.out.println("Unable to read from file");
            i.printStackTrace();
        } catch (ClassNotFoundException c)
        {
            System.out.println("Object read is not a java.util.Date instance");
        }
       
    }
}
