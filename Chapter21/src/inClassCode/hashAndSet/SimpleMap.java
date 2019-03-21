//package inClassCode.hashAndSet;
//
//import java.awt.*;
//import java.util.Set;
//
//public class SimpleMap {
//    public static void main(String[] args) {
//        Set<Student> studentSet = new HashSet<Student>();
//        Set<Color> favColor = new HashSet<Color>();
//
//        Student s0 = new Student();
//        Student s1 = new Student("Mickey Mouse", 98);
//        Student s2 = new Student("Minnie Mouse", 99);
//        Student s3 = new Student(); //s3 has same name and same score - should be the same as s0, s4, s5
//        Student s4 = new Student();
//        s4.setScore(33); //s4 has the same name but different score --should be same object as s0, s4, s5
//        Student s5 = new Student("general grizzly", 55);
//
//        studentSet.add(s0);
//        studentSet.add(s1);
//        studentSet.add(s2);
//        studentSet.add(s3);
//        studentSet.add(s4);
//        studentSet.add(s5);
//
//
//    }
//}
