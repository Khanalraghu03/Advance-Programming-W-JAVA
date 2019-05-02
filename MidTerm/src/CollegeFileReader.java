import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CollegeFileReader {

    public static void main(String[] args) {
        File file = new File("MidTerm/src/College.txt");
        Scanner output;
        try {
            output = new Scanner(file);
            String line = output.nextLine();
            ArrayList<Course> courses = new ArrayList<>();
            Student s = null;
            line = line.replace(":", "");
            s = new Student(line);

            while (output.hasNextLine()) {
                line = output.nextLine();
                if(line.contains(":")) {
                    System.out.println(s.toString());
                    courses.clear();
                    line = line.replace(":", "");
                    s = new Student(line);
                }
                else if(line.contains(",")){
                    String[] lineContent = line.split(", ");
                    Course c = new Course(lineContent[0], lineContent[1], lineContent[2]);
                    courses.add(c);

                }
                s.setCourseAL(courses);
            }

            System.out.println(s.toString());

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
