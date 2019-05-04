

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

		//If it is just one letter grade, add a comma to it
			//so that it is sorted as: "letter+", "letter," and "letter-"
		if(grade.length() == 1) {
			grade += ",";
		}

		if(compareGrade.length() == 1) {
			compareGrade += ",";
		}

		//If the grade is A+ then it should show before F+: A+ is not valid grade
		//if the grade is F+, it should show after A+
		//if the grade is F-, it should show after F+
		//Everything else is should do it's normal operation
		switch(grade) {
			case "A+":
				grade = "F-+";
				break;
			case "F+":
				grade = "F-,";
				break;
			case "F-":
				grade = "F--";
				break;
			default:
				break;
		}

		switch(compareGrade) {
			case "A+":
				compareGrade = "F-+";
				break;
			case "F+":
				compareGrade = "F-,";
				break;
			case "F-":
				compareGrade = "F--";
				break;
			default:
				break;
		}


		return grade.compareTo(compareGrade);
	}

	/******************************************************************************************************************/

}
