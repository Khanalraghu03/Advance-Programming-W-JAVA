import java.time.LocalDate;
import java.time.Period;


/**Class: TVCharacter
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class contains the attributes of the character and calculates its age.
 *
 */
public class TVCharacter {
    private String firstname;
    private String lastname;
    private String dob;
    private String tvshow;

    public TVCharacter(String firstname, String lastname, String dob, String tvshow) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.tvshow = tvshow;
    }

    //Calculates the Age of the character
    public Integer getAges() {
        String[] dateString = getDob().split("/");
        int month = Integer.parseInt(dateString[0]);
        int day = Integer.parseInt(dateString[1]);
        int year = Integer.parseInt(dateString[2]);


        LocalDate birthday = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        Period p = Period.between(birthday, today);

        return (p.getYears());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTvshow() {
        return tvshow;
    }

    public void setTvshow(String tvshow) {
        this.tvshow = tvshow;
    }


    @Override
    public String toString() {
        return "TVCharacter{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob='" + dob + '\'' +
                ", tvshow='" + tvshow + '\'' +
                '}';
    }
}

