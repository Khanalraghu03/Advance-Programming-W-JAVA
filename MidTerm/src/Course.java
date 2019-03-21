/**Class: midterm.Course
 * @author ITEC 3150
 * @version 1.0
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: Feb 18, 2019
 *
 *
 * This is the business class for a simple course
 *
 *
 */
public class Course {
    private String discipline;
    private String number;
    private int section;

    public Course(String discipline, String number, String section) {
        setDiscipline(discipline);
        setNumber(number);
        setSection(Integer.parseInt(section.trim()));
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "midterm.Course " + discipline + number + "-" + section;
    }
}
