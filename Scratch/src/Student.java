import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Class: midterm.Student.java
 *
 * @author Ryan Cunico
 * @version 1.1
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: January 19, 2019
 *
 * This class models a midterm.Student object. Each midterm.Student has a name, and an Exception is thrown if the user tries to pass
 * in the letter "x" for the midterm.Student's name.  This class provides default and one-arg constructors, a getter and setter,
 * and a toString().
 */
public class Student
{
    //Holds name of midterm.Student
    private String name;

    Student() throws IOException
    {
        setName("default");
    }

    /**
     * midterm.Student
     * This one-arg constructor is used to set the midterm.Student's name.
     */
    Student (String name) throws IOException
    {
        setName(name);
    }

    /**
     * Method: getName
     * This method is used get the midterm.Student's name.
     *
     * @return String
     */
    public String getName ()
    {
        return name;
    }

    /**
     * Method: setName
     * This method is used to set the midterm.Student's name, and it will throw an IOException if the letter
     * "x" is entered as the name or if the name is blank.  Otherwise, it will set the name to the String that is passed in.
     *
     * @param name
     * @throws IOException
     * @throws InputMismatchException
     */
    public void setName (String name) throws IOException, InputMismatchException
    {
        //If the name is not "x" or blank, set the midterm.Student's name
        if (!name.equalsIgnoreCase("x") && !name.trim().isEmpty())
        {
            this.name = name;
        }
        //If the user enters an empty String, throw an Exception
        else if (name.trim().isEmpty())
        {
            throw new InputMismatchException("Name cannot be blank.");
        }
        //If the user enters an "x", throw an Exception
        else if (name.equalsIgnoreCase("x"))
        {
            throw new IOException("The \"x\" ends the list of student names.");
        }
    }

    /**
     * Method: toString
     * This method returns the midterm.Student's name as a String.
     *
     * @return String
     */
    public String toString ()
    {
        return getName();
    }

}

