import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class StudentTester
    {

        public static void main (String[] args)
            {
                StudentTester studentTester = new StudentTester();
                System.out.println("Binary Search Tree Problem≠");
                studentTester.binarySearchTreeProblem();
                System.out.println("\n\nHash Map Problem:");
                studentTester.hashMapProblem();
                System.out.println("\n\nBrute Force Problem:");
                studentTester.bruteForceTester();





            }

        public void binarySearchTreeProblem() {
            Student student1 = new Student("Ryan", "Cunico", "6873");
            Student student2 = new Student("Hasan", "Mohammed", "6878");
            Student student3 = new Student("Richard", "Smith", "6869");
            Student student4 = new Student("Shadow", "Cunico", "1222");
            Student student5 = new Student("Nancy", "Sardar", "6842");
            Student student6 = new Student("Philip", "Williams", "6882");


            BinarySearchTree binarySearchTree = new BinarySearchTree();
            binarySearchTree.add(student1);
            binarySearchTree.add(student2);
            binarySearchTree.add(student3);
            binarySearchTree.add(student4);
            binarySearchTree.add(student5);
            binarySearchTree.add(student6);

            System.out.println("Binary tree: Post Order:");
            binarySearchTree.printPostOrder();
            binarySearchTree.print();


            //print their hashCode
            System.out.println("Hash Code: ");
            System.out.println("student 1 hashcode: " + student1.hashCode());
            System.out.println("student 2 hashcode: " + student2.hashCode());
            System.out.println("student 3 hashcode: " + student3.hashCode());
            System.out.println("student 4 hashcode: " + student4.hashCode());
            System.out.println("student 5 hashcode: " + student5.hashCode());
            System.out.println("student 6 hashcode: " + student6.hashCode());

            Comparator<Student> com = (o1, o2) -> Integer.parseInt(o1.bannerID) >
                    Integer.parseInt(o2.bannerID) ? 1:-1;

            System.out.println("student1 compare student1 " + com.compare(student1, student2));

        }

        public void hashMapProblem() {
            Student student1 = new Student("Ryan", "Cunico", "900186873");
            Student student2 = new Student("Hasan", "Mohammad", "900186878");
            Student student3 = new Student("Richard", "Smith", "900186869");
            Student student4 = new Student("Shadow", "Cunico", "900111222");
            Student student5 = new Student("Nancy", "Sadar", "900186842");
            Student student6 = new Student("Philip", "Smith", "900186882");

            HashSet<Student> studentHashSet = new HashSet<Student>();
            studentHashSet.add(student1);
            studentHashSet.add(student2);
            studentHashSet.add(student3);
            studentHashSet.add(student4);
            studentHashSet.add(student5);
            studentHashSet.add(student6);

            HashMap<Integer, HashSet<Student>> studentHashMap = new HashMap<Integer, HashSet<Student>>();

            for (int i = 0; i < 17; i++)
                {
                    studentHashMap.put(i, new HashSet<Student>());
                }

            for (Student student : studentHashSet)
                {
                    studentHashMap.get(student.hashCode()).add(student);
                }

            for (HashMap.Entry<Integer, HashSet<Student>> studentSet : studentHashMap.entrySet())
                {
                    System.out.println(studentSet.getKey() + ": " + studentSet.getValue().toString());
                }
        }

        public void bruteForceTester()
            {
                Character[] providedChars = {'a', 'b', 'c'};
                ArrayList<String> allCombos = new ArrayList<String>();
                for (int i = 0; i < providedChars.length; i++)
                    {
                        for (int j = 0; j < providedChars.length; j++)
                            {
                                for (int k = 0; k < providedChars.length; k++)
                                    {
                                        String newChars = providedChars[i] + "" + providedChars[j] + "" + providedChars[k];
                                        allCombos.add(newChars);
                                     }
                            }
                    }
                System.out.println(allCombos.toString() + ": [" + allCombos.size() + "]");
        }

    }
