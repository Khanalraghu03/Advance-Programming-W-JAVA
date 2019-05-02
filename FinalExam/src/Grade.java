

/**
   This class models a grade.
 */
public class Grade implements Comparable<Grade>
{
	private String letterGrade;

	/**
      Constructs a Grade from a letter grade
      @param grade the letter grade
	 */
	public Grade(String grade)
	{
		letterGrade = grade;
	}

	/**
	 * Sets the letter grade.
	 * @param  grade the letter grade.
	 */
	public void setLetterGrade(String grade)
	{
		this.letterGrade = grade;
	}

	/**
         Gets the letter grade.
         @return the letter grade
	 */
	public String getLetterGrade()
	{
		return letterGrade;
	}

	public String toString()
	{
		return "Letter grade is: " + this.letterGrade;
	}


	/*************************************** My Part ******************************************************************/

	@Override
	public int compareTo(Grade g) {
		String grade = getLetterGrade();
		String compareGrade = g.getLetterGrade();

		//I am sure there is a better way to do this, but this is what I could come up with---

		//A comma is less than a plus but greater than -
		if(grade.length() == 1) {
			grade += ",";
		}
		if(compareGrade.length() == 1) {
			compareGrade += ",";
		}

		//Print A+ after F
		if(grade.equals("A+")) {
			grade = "F--";
		}
		if(compareGrade.equals("A+")) {
			compareGrade = "F--";
		}

		//Print F+ after A+
		if(grade.equals("F+")) {
			grade = "F---";
		}
		if(compareGrade.equals("F+")) {
			compareGrade = "F---";
		}


		//Print F- after F+
		if(grade.equals("F-")) {
			grade = "F----";
		}
		if(compareGrade.equals("F-")) {
			compareGrade = "F----";
		}


		int num = grade.compareTo(compareGrade);
		return grade.compareTo(compareGrade);
	}

	/******************************************************************************************************************/

}
