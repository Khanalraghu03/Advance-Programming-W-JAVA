import java.util.ArrayList;

/**
 * Class: StudentAL.java
 *
 * @author Ryan Cunico
 * @version 1.1
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: January 19, 2019
 *
 * This class is used to create an ArrayList<midterm.Student> objects.  It provides methods to add a student, obtain the
 * size of the ArrayList<midterm.Student>, return the ArrayList<midterm.Student> itself, clear the ArrayList, and iterate through
 * the ArrayList and print each midterm.Student's toString().
 */

public class StudentAL
{
    //ArrayList<midterm.Student> to hold all Students
    private ArrayList<Student> allStudents;

    /**
     * Default constructor for a StudentAL.
     * Used to initialize the ArrayList<midterm.Student>
     */
    StudentAL ()
    {
        allStudents = new ArrayList<Student>();
    }

    /**
     * Method: getStudentAL
     * This method returns the allStudents ArrayList
     *
     * @return ArrayList<midterm.Student>
     */
    public ArrayList<Student> getStudentAL ()
    {
        return allStudents;
    }

    /**
     * Method: addStudent
     * This method is used to add a midterm.Student object to the allStudents ArrayList<midterm.Student>
     * @param student
     */
    public void addStudent (Student student)
    {
        allStudents.add(student);
    }

    /**
     * Method: getTotalStudents
     * This method returns the size of the allStudents ArrayList
     *
     * @return int
     */
    public int getTotalStudents ()
    {
        return allStudents.size();
    }

    /**
     * Method: clear
     * This method clears the midterm.Student ArrayList<midterm.Student>, used in the event that the user has started entering
     * midterm.Student names and then chooses an invalid number of subgroups.
     */
    public void clear ()
    {
        allStudents.clear();
    }

    /**
     * Method: toString
     * This method returns every midterm.Student's toString(), combined and formatted nicely
     *
     * @return String
     */
    public String toString ()
    {
        String string = "";
        //Enhanced for loop to add each midterm.Student's toString() to the return String
        for (Student student : allStudents)
        {
            string += student.toString() + "\n";
        }
        return string;
    }


}

