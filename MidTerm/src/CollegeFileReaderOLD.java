import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CollegeFileReader {
    public static void main(String[] args) {
        File file = new File("./src/College.txt");
        Scanner output;
        String line;
        String[] nameWColon;
        String[] couseWComma;
        String studentName = null;
        String courseName = null;
        String sectionNumber = null;
        String section = null;
        //int sec = 0;

        {
            try {
                output = new Scanner(file);
                while (output.hasNextLine()) {
                    line = output.nextLine();
					
                    if (line.contains(":")) {
                        nameWColon = line.split(":");
                        studentName = line.replace(":", "");
                        System.out.println(studentName);
						Student s = new Student(studentName);

                    }
                    else if (line.contains(",")){
                        couseWComma = line.split(",");
                        courseName = couseWComma[0];
                        sectionNumber = couseWComma[1];
                        section = couseWComma[2];
                        //sec = Integer.parseInt(section);
						Course course = new Course(courseName, sectionNumber, section);
                    }

                   

                }
				studentName.addCourse(course);
                    stu.setCourseAL(new ArrayList<Course>());
                    System.out.println(stu.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
			
			
        }


    }

}
