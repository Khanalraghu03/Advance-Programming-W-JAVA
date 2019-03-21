import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.*;

import javafx.scene.paint.Color;

public class SimpleMaps {

	public static void main(String[] args) {
		Set<Student> studentSet = new HashSet<Student> ();
		Set<Color> favoriteColors = new HashSet<Color>();
		
		//System.out.println("hashset");
		
		Student s0 = new Student();
		Student s1 = new Student("Mickey Mouse", 98);
		Student s2 = new Student("Minnie Mouse", 22);
		Student s3 = new Student(); //s3 has same name and same score - should be the same as s0, s4, s5
		Student s4 = new Student();
		s4.setScore(33); //s4 has the same name but different score --should be same object as s0, s4, s5
		Student s5 = new Student("general grizzly", 55); //should be the same as s0, s4, s3 (I ignore case)
		Student s6 = new Student("H. Simpson", 99); 
		
		studentSet.add(s0);studentSet.add(s1);studentSet.add(s2);studentSet.add(s3); studentSet.add(s4);studentSet.add(s5);
		
		favoriteColors.add(Color.RED);
		favoriteColors.add(Color.GREEN);
		favoriteColors.add(Color.BLUE);
		
		Map<Student, Color> colorMap = new HashMap<Student, Color>();
		
		colorMap.put(s0, Color.RED);
		colorMap.put(s1, Color.RED);
		colorMap.put(s2, Color.BLUE);
		colorMap.put(s3, Color.BLUE);
		colorMap.put(s4, Color.BLUE);
		colorMap.put(s5, Color.BLUE);
		colorMap.put(s6, Color.GREEN);
		
		System.out.println(colorMap);

		

	}

}
