package inClassCode.hashAndSet;

import java.util.*;
import java.util.stream.Stream;

public class SetSampleTest {

    public static void main(String[] args) {

        Set<Student> studentSet = new TreeSet<Student>();
        LinkedList<Student> studentLL = new LinkedList<Student> ();

        Student s0 = new Student();
        Student s1 = new Student("Mickey Mouse", 98);
        Student s2 = new Student("Minnie Mouse", 99);
        Student s3 = new Student(); //s3 has same name and same score - should be the same as s0, s4, s5
        Student s4 = new Student();
        s4.setScore(33); //s4 has the same name but different score --should be same object as s0, s4, s5
        Student s5 = new Student("general grizzly", 55); //should be the same as s0, s4, s3 (I ignore case)

        //System.out.println("" + s0 + s1 + s2 + s3 + "");
        System.out.println(s0.hashCode());
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        System.out.println(s3.hashCode());
        System.out.println(s4.hashCode());
        System.out.println(s5.hashCode());

        studentSet.add(s0);studentSet.add(s1);studentSet.add(s2);studentSet.add(s3); studentSet.add(s4);studentSet.add(s5);
        studentLL.add(s0);studentLL.add(s1);studentLL.add(s3);studentLL.add(s2); studentLL.add(s4);studentLL.add(s5);
        System.out.println("set: " + studentSet);
        System.out.println("ll: " + studentLL);


        //dry iteration
        //using iterator
        for(
        Iterator<Student> it = studentSet.iterator();
        it.hasNext();
        )
        {
            Student s = (Student) it.next();
            System.out.println(s);
        }

        //using for each
        for(Student s: studentSet) {
            System.out.println("Using for each\n");
            System.out.println(s);
        }

        //using stream
        Stream<Student> stream = studentSet.stream();
//        stream.forEach((stu) -> System.out.println(stu));
        stream.forEach(System.out::println);
        //size()
        System.out.println(studentSet.size());
        //contains
        System.out.println("Contains " + studentSet.contains(s1));
        //addAll - union
        studentSet.addAll(studentLL);
        System.out.println(studentSet);
        //retain all - interaction
        studentSet.retainAll(studentLL);
        System.out.println(studentSet.retainAll(studentLL));
        //remove all - difference
        studentSet.removeAll(studentLL);
        System.out.println(studentSet.isEmpty());
    }

}