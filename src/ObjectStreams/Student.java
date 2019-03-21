import java.util.Date;
import java.io.Serializable;


public class Student implements Serializable {
	private String name;
	private double score;
	private Date date;
	
	
	public Student(String name, double score, Date date) {
		this.name = name;
		this.score = score;
		this.date = date;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Student [name=" + name + ", score=" + score + ", date=" + date + "]";
	}
}
