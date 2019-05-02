package otherSolution;

import java.util.HashMap;
import java.util.HashSet;
/**
 * Class: Driver.java
 * @version: 1.0
 * Author: Hasan Mohammed
 * Date: Apr 23, 2019
 * Course: ITEC3150
 * Purpose:  
 */

public class Driver
{
	public static void main(String[] args)
	{
		
		Driver d = new Driver();
		
		d.activateHashMap();
		
		d.activateBinarySearchTree();
		
		d.activateBruteForceAlgorithm();
	}
	
	public void activateHashMap()
	{	
		HashMap<Integer, HashSet<Student>> studentHash = new HashMap<Integer, HashSet<Student>>();
		
		HashSet<Student> list = new HashSet<Student>();
		
		Student s1 = new Student("Daniel", "Sales", ""+6343);
		list.add(s1);
		Student s2 = new Student("Nancy", "Sardar", ""+4673);
		list.add(s2);
		Student s3 = new Student("Ryan", "Cunico", ""+5324);
		list.add(s3);
		Student s4 = new Student("Eric", "Jenkins", ""+7533);
		list.add(s4);
		Student s5 = new Student("Fergie", "Giron", ""+5424);
		list.add(s5);
		Student s6 = new Student("Josh", "Sales", ""+4245);
		list.add(s6);		
		
		
		for(int i = 0 ;i<17;i++)
		{
			studentHash.put(i, new HashSet<Student>());
		}
		
		list.forEach(student->
		{
			studentHash.get(student.hashCode()).add(student);
		});
		
		System.out.println(studentHash);
	}
	
	public void activateBinarySearchTree()
	{		
		BinarySearchTree tree = new BinarySearchTree();
		
		Student s1 = new Student("Daniel", "Sales", ""+6343);
//		tree.add(s1);
		Student s2 = new Student("Nancy", "Sardar", ""+4673);
//		tree.add(s2);
		Student s3 = new Student("Ryan", "Cunico", ""+5324);
//		tree.add(s3);
		Student s4 = new Student("Eric", "Jenkins", ""+7533);
//		tree.add(s4);
		Student s5 = new Student("Fergie", "Giron", ""+5424);
//		tree.add(s5);
		Student s6 = new Student("Josh", "Sales", ""+4245);
//		tree.add(s6);
		
		System.out.println("Preorder:");
		tree.printPreOrder();
		System.out.println("Inorder:");
		tree.print();
		System.out.println("Postorder");
		tree.printPostOrder();
		
	}
	
	public void activateBruteForceAlgorithm()
	{
		String[] array = {"a", "b", "c"};
		
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array.length; j++)
			{
				for (int h = 0; h < array.length; h++)
				{
					System.out.println(array[i] + array[j] + array[h]);
				}
			}
		}
	}

}
