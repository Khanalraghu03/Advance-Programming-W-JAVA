/**Program: Cat
 * @author: ITEC 3150
 * @version 1.0
 * Course: ITEC 3150-01
 * Written: Feb 10, 2019
 * This class - This class subclass of Pet class and models Cats
 *Purpose:- The purpose of the class is to create and modify cats.
 **/

// Cat.java implements the Cat subclass describing the unique characteristics of a cat

public class Cat extends Pet
{
	/**
	 */
	private Litter litterType; //scoop-able, crystals, regular, none

	/**
	 * Set number of legs to default to 4
	 */
	public Cat (String name, String breed, String coatColor, Litter litterType, int weight)
	{
		super(name, breed, coatColor, weight); // set for all pet
		this.litterType = litterType; // set for only cat
		this.numberOfLegs = 4;  // set for only cat
	}

	public Cat (String name, String breed, String coatColor, Litter litterType, int weight, int legs, int not)
	{
		super(name, breed, coatColor, legs, weight, not);
		this.litterType = litterType;
	}

	/**
	 * @return
	 */
	public Litter getLitterType()
	{
		return litterType;
	}

	/**
	 * @param litterType the litterType to set
	 */
	public void setLitterType(Litter litterType)
	{
		this.litterType = litterType;
	}

	@Override
	public String toString()
	{
		return "Cat =" + super.toString() + "litterType=" + litterType + "]"+ "\n";
	}

}
