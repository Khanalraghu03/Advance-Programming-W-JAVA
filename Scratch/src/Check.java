import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Date;

public class Check {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();                          //Today's date
        LocalDate birthday = LocalDate.of(1997, Month.DECEMBER, 12);  //Birth date

        Period p = Period.between(birthday, today);

//Now access the values as below
        System.out.println(p.getDays());
        System.out.println(p.getMonths());
        System.out.println(p.getYears());
    }
}
