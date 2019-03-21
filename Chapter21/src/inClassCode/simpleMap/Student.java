
public class Student implements Comparable<Student> {
	private String name;
	private int score;
	

	public Student() {		
		this.name = "M Simpson";
		this.score = 50;
	}
	
	public Student(String name, int score) {		
		this.setName(name);
		this.setScore(score);		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//hash code is generated only according to name field
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode()); //to ignore case	
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	     if (obj == null) return false;
		Student otherStudent = (Student) obj;
		return name.equalsIgnoreCase(otherStudent.name); 
		//it is equal if the name of the objects is the same
	}

	@Override
	public String toString() {
		return "Student " + name + " score:" + score + "\n";
	}

	@Override
	public int compareTo(Student stu) {		
		return this.score - stu.score;
	}
	
	
	
	

}
