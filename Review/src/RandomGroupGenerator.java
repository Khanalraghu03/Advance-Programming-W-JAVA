import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RandomGroupGenerator {


        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of groups: ");
            int numOfGroups = input.nextInt();

            input = new Scanner(System.in);
            String studentName;

            ArrayList<String> students = new ArrayList();

            do {
                System.out.print("Enter the student name (or x to exit): ");
                studentName = input.nextLine();
                if (!studentName.equalsIgnoreCase("x")) {
                    students.add(studentName);
                }
            } while (!(studentName.equalsIgnoreCase("x")));

            System.out.println("\nThe number of group is: " + numOfGroups + "\n");
            Collections.shuffle(students);

            System.out.println("Students shuffled: \n" + students);

            ArrayList<ArrayList<String>> classroom = new ArrayList<>();

            //Create groups that students will belong to using the
                //number of groups user specified and assign them in a classroom
            for(int i = 0; i< numOfGroups; i++) {
                ArrayList<String> groupOfStudents = new ArrayList<>();
                classroom.add(groupOfStudents);
            }

            //We currently have a classroom with assigned number of groups.
                //Now select the students and put them in a group.

            int groupNumber;
            //Selecting students randomly to the group
            for(int i = 0; i < students.size(); i++) {
                groupNumber =  i%numOfGroups;
                //Inside the class room, get the group number: add the shuffled students in a group
                    //one by one, until there are no students left
                classroom.get(groupNumber).add(students.get(i));
            }

            for(int i = 0; i < numOfGroups; i++) {
                int num = i + 1;
                System.out.println("Group #" + num + ":");
                for(int j = 0; j < classroom.get(i).size(); j++) {
                    System.out.println(classroom.get(i).get(j));
                }
            }


//            System.out.println("\nHere is Students in group of " + numOfGroups + ": \n" + classroom);

        }
    }
