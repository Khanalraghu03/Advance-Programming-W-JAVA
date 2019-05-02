package otherSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StudentTester {

	public static void main(String[] args) {

		//create students
		Student s0 = new Student("I", "My", "900151233");
		Student s1 = new Student("j", "k", "900151234");
		Student s2 = new Student("l", "m", "900151235");
		Student s3 = new Student("n", "o", "900151237");
		Student s4 = new Student("p", "q", "900151238");
		Student s5 = new Student("Oc", "Ur", "90015129");

		//print their hashCode
		System.out.println("s0:" + s0.hashCode());
		System.out.println("s1:" + s1.hashCode());
		System.out.println("s2:" + s2.hashCode());
		System.out.println("s3:" + s3.hashCode());
		System.out.println("s4:" + s4.hashCode());
		System.out.println("s5:" + s5.hashCode());


		//Create a HashMap that takes Integer as key and HashSet of students as value
		HashMap<Integer, HashSet<Student>> studentHash = new HashMap<Integer, HashSet<Student>>();

		//we need at least 17 hashSet of students
			//so from 0 - 16, add 0 - 16 as the key
			// and create 17 empty hash set of students
		for(int i = 0; i < 17; i++)
		{
			studentHash.put(i, new HashSet<Student>());
		}

		//Create an array list of students
		HashSet<Student> list = new HashSet<>();

		//add the 5 students to the array list of students
		list.add(s0);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		list.add(s5);

		//for each student in the list:
			//get the value

		for(Student student: list) {
			studentHash.get(student.hashCode()).add(student);
		}
//		list.forEach(student ->
//		{
//
//
//		});


		for (HashMap.Entry<Integer, HashSet<Student>> studentSet : studentHash.entrySet())
		{
			System.out.println(studentSet.getKey() + ": " + studentSet.getValue().toString());
		}


//		for (HashMap.Entry<Integer, HashSet<Student>> studnet : studentHash.keySet())
//		{
//			System.out.println(studnet.getKey() + " : " + studnet.getValue().toString());
//		}
		
//		System.out.println(studentHash);
		
	}

}
