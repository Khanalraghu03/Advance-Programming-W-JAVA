import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Class: Student.java
 *
 * @author Ryan Cunico
 * @version 1.1
 * Course : ITEC 3150 Spring 2019
 * Written: January 19, 2019
 *
 * This class models a Student object. Each Student has a name, and an Exception is thrown if the user tries to pass
 * in the letter "x" for the Student's name.  This class provides default and one-arg constructors, a getter and setter,
 * and a toString().
 */
public class Student
    {
        //Holds name of Student
        private String name;

        /**
         * Default constructor for a Student.
         * Sets the name to "default" by default.
         */
        Student () throws IOException
            {
                setName("default");
            }

        /**
         * Student
         * This one-arg constructor is used to set the Student's name.
         */
        Student (String name) throws IOException
            {
                setName(name);
            }

        /**
         * Method: getName
         * This method is used get the Student's name.
         *
         * @return String
         */
        public String getName ()
            {
                return name;
            }

        /**
         * Method: setName
         * This method is used to set the Student's name, and it will throw an IOException if the letter
         * "x" is entered as the name or if the name is blank.  Otherwise, it will set the name to the String that is passed in.
         *
         * @param name
         * @throws IOException
         * @throws InputMismatchException
         */
        public void setName (String name) throws IOException, InputMismatchException
            {
                //If the name is not "x" or blank, set the Student's name
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
         * This method returns the Student's name as a String.
         *
         * @return String
         */
        public String toString ()
            {
                return getName();
            }

    }
