import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CollegeFileReader {
    public static void main(String[] args) {
        File file = new File("./src/midterm/College.txt");
        Scanner output;
        String line = null;
//        String[] nameWColon;
        String[] couseWComma;
        ArrayList<String> studentName = new ArrayList<>();
        String courseName = null;
        String sectionNumber = null;

        String section = null;
        ArrayList<Course> arrayCourse = new ArrayList<>();
        ArrayList<ArrayList<Course>> allCourse = new ArrayList<>();
//        Course course = null;
        Student s = null;
        String name;

        try {
            output = new Scanner(file);
            while (output.hasNextLine()) {
                line = output.nextLine();
//                String name = line.replace(":","");
//                s = new Student(name);
//                System.out.println(s);
                if (line.contains(":")) {

//                    s = new Student(line.replace(":", ""));
//                    nameWColon = line.split(":");
//                    studentName.add(nameWColon[0]);
//                    studentName.add(line.replace(":", ""));
                    name = line.replace(":", "");
                    System.out.println(name);
                    s = new Student(name);
//                    System.out.println(s.toString());
//                    System.out.println(s);
                    //create a student
                } else {
                    //create a course
                    couseWComma = line.split(",");
                    courseName = couseWComma[0];
                    sectionNumber = couseWComma[1];
                    section = couseWComma[2];
                    Course course = new Course(courseName, sectionNumber, section);
//                    arrayCourse.add(course);
                    s.addCourse(course);

//                    System.out.println(s.toString());

                }



//                String s = new Student()
//                s.getCourseAL();
                System.out.println(s.toString());
//                System.out.println(s);
            }


//            System.out.println(s.toString());

//            System.out.println(s);



//            for (String k : studentName) {
//                Student stu = new Student(k);
////                stu.setCourseAL(arrayCourse);
//                System.out.println(stu.toString());
//            }

        }


         catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
