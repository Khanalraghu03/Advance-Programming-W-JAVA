import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class: StudentALAL.java
 *
 * @author Ryan Cunico
 * @version 1.1
 * Course : ITEC 3150 Spring 2019
 * Written: January 19, 2019
 *
 * This class is used to create an ArrayList<ArrayList<Student>>, which is used to subdivide the group of all Student objects
 * into smaller subgroups.  It uses the Random class to generate a number between 0 and the total number of Students.  Since the
 * user is allowed to select the number of groups that he/she would like to separate the Students into, a setNumGroups method is
 * provided, which will throw an Exception if the user tries to enter a number less than 0.  A createGroups() method is provided
 * to create the individual subgroups, and finally a toString() method is provided in order to output the groups' contents
 * to a String.
 */

public class StudentALAL
    {
        //ArrayList of ArrayLists to hold the subgroups of Students
        private ArrayList<ArrayList<Student>> subgroups;
        //Int to hold the number of groups to divide the Students into
        int numGroups;

        /**
         * Default constructor for a StudentALAL.
         * Used to initialize the subgroups ArrayList<ArrayList<Student>>
         */
        StudentALAL ()
            {
                subgroups = new ArrayList<ArrayList<Student>>();
            }

        /**
         * Method: setNumGroups
         * This method is used to set the number of groups that the user would like to divide the Students into. If the
         * user passes an incorrect value (< 0), an IOException is thrown.
         * @param numGroups
         * @throws IOException
         */
        public void setNumGroups (int numGroups) throws IOException
            {
                //If user tries to enter a number less than 0, throw an Exception.
                if (numGroups <= 0)
                    {
                        throw new IOException("You cannot have 0 or less groups");
                    }
                //Otherwise, set the numGroups variable to int passed in to method.
                this.numGroups = numGroups;
            }

        /**
         * Method: createGroups
         * This method is passed a StudentAL object which contains all Students, and it then compares the number
         * of Students to the number of subgroups that the user chose.  If the number of subgroups is more than the
         * number of Students, an Exception is thrown. Otherwise, the method adds the appropriate number of ArrayList<Student>
         * to the subgroups ArrayList<ArrayList<Student>>.  Next, the Students are shuffled and randomly added to the newly created
         * subgroups, using the Random class and a 'for' loop. As Students are added to the subgroups, they are removed
         * from the original ArrayList<Student> which contained all Students.
         * @param studentAL
         * @throws IOException
         */
        public void createGroups (StudentAL studentAL) throws IOException
            {
                //Instance of Random in order to select random Students each time
                Random random = new Random();
                //Int to hold the random index value created
                int randomIndex;
                //Throw an Exception if the number of groups chosen is greater than the amount of Students
                if (numGroups > studentAL.getTotalStudents())
                    {
                        throw new IOException("There are " + numGroups + " groups and only " +
                                studentAL.getTotalStudents() + " students. Please try again.");
                    }

                //For loop that creates appropriate number of subgroups (ArrayList<Student>)
                for (int i = 0; i < numGroups; i++)
                    {
                        subgroups.add(new ArrayList<Student>());
                    }
                //StudentAL is shuffled first using the Collections class
                Collections.shuffle(studentAL.getStudentAL());
                //While loop that will continue to run until all Students have been randomly added to subgroups
                while (studentAL.getTotalStudents() > 0)
                    {
                        //For loop to iterate through the subgroups
                        for (int i = 0; i < numGroups; i++)
                            {
                                //If statement that will prevent a NullPointerException
                                if (studentAL.getTotalStudents() > 0)
                                    {
                                        //Create a random number between 0 and the current total number of Students
                                        randomIndex = random.nextInt(studentAL.getTotalStudents());
                                        //Add a Student at the (random) index to the next subgroup in line, while
                                        //removing Student from the ArrayList<Student> of all Students
                                        subgroups.get(i).add(studentAL.getStudentAL().remove(randomIndex));
                                    }
                            }
                    }
            }

        /**
         * Method: toString
         * This method formats the Students that belong to each subgroup using nested "for" loops
         *
         * @return String
         */
        public String toString ()
            {
                String returnString = "";
                //First 'for' loop iterates through each of the subgroups
                for (int i = 0; i < subgroups.size(); i++)
                    {
                        //Create a Group: # identifier and add a newline character
                        returnString += "Group " + (i + 1) + ":\n";
                        //Iterate through each Student object in the subgroup, and add the Student to the String
                        for (Student student : subgroups.get(i))
                            {
                                returnString += student.toString() + "\n";
                            }
                        returnString += "\n";
                    }
                //Return the completed String
                return returnString;
            }


    }
