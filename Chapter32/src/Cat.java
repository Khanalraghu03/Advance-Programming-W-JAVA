/**  @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * This class will create Cat
 * @version 1.0
 **/

/**Kajal Vaghani
 /** Class:Cat
 *@ author Dr. B
 * version 1.0
 * Course: ITEC 2250 Spring 2013
 * Written: February 24, 2013
 *This class - This class subclass of Pet class and create the Cat method and constructor.
 *Purpose:- The purpose of the class is create the cat's method and constructor to call in PetTester class.
 */
/**Program: Cat
 * @author: ITEC 2150
 * @version 1.0
 * Course: ITEC 2150-01
 * Written: Feb 10, 2013
 * This class - This class subclass of Pet class and models Cats
 *Purpose:- The purpose of the class is to create and modify cats.
 **/


//Cat.java implements the Cat subclass describing the unique characteristics of a cat

public class Cat extends Pet
{
	/**
	 */
	private Litter litterType; //scoopable, crystals, regular, none

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
		return "Cat [super = "+super.toString() + "litterType=" + litterType + "]";
	}

}
