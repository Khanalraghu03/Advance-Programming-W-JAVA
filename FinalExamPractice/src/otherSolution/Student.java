package otherSolution;

public class Student {

	private String fName;
	private String lName;
	private String bID;
	
	public Student(String fName, String lName, String bID) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.bID = bID;
	}

	//setters and getters
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getbID() {
		return bID;
	}
	public void setbID(String bID) {
		this.bID = bID;
	}
	public int getbIDInt() {
		return Integer.parseInt(bID);
	}


	//hashCode
	@Override
	public int hashCode() {
		int results = (Math.abs(this.fName.hashCode() + this.lName.hashCode() + this.bID.hashCode()) % 17);
		return results;
	}

	//equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (bID == null) {
			if (other.bID != null)
				return false;
		} else if (!bID.equals(other.bID))
			return false;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		if (lName == null) {
			if (other.lName != null)
				return false;
		} else if (!lName.equals(other.lName))
			return false;
		return true;
	}

	//toString
	@Override
	public String toString() {
		return "Student [fName=" + fName + ", lName=" + lName + ", bID=" + bID + "]";
	}
	
	
	
}
