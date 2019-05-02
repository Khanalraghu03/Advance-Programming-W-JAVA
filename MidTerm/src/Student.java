import java.util.ArrayList;

/**Class: Student
 * @author ITEC 3150
 * @version 1.0
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: Feb 18, 2019
 *
 *
 * This is the business class for a student
 * A student consists of:
 *  a name
 *  an array list of courses for the student
 *
 */
public class Student {

    private String name;
    private ArrayList<Course> courseAL;

    public Student(String name) {
        setName(name);
        this.courseAL = new ArrayList<Course>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Course> getCourseAL() {
        return courseAL;
    }
    public void setCourseAL(ArrayList<Course> courseAL) {
        this.courseAL = courseAL;
    }

    public void addCourse(Course course) {
        this.courseAL.add(course);
    }

    @Override
    public String toString() {
        return "Student " + name + ", is taking " + courseAL;
    }
}
