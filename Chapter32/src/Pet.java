
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * @version 1.0
 * This class is where all the attributes of pets(Cat, Dog) located
 */

/**Program: Pet
 * @author : ITEC 2150 
 * @version 2.0
 * Course: ITEC 2150-01
 * Written: Feb 10, 2013 
 **/
//Note: This class does not have complete correct javadoc--


public class Pet implements Comparable<Pet>
{
	protected String name;
	protected String breed;
	protected String color;
	protected int numberOfLegs;
	protected int weight;
	protected int numberOfToys;
	private Lock petLock = new ReentrantLock();
	private Condition toyFull = petLock.newCondition();
	private Condition toyEmpty = petLock.newCondition();

	/**
	 * Zero argument Constructor
	 * 
	 */
	public Pet()
	{
		this.name = "default";
		this.breed = "mutt";
		this.color = "black";
		this.numberOfLegs = 4;
		this.weight = 0;	
		this.numberOfToys = 0;
	}

	/**
	 * Constructor with four fields
	 * 
	 */
	public Pet(String name, String breed, String color, int weight)
	{
		this.name = name;
		this.breed = breed;
		this.color = color;			
		this.weight = weight;
		this.numberOfLegs = 4;
		this.numberOfToys = 0;
	}

	/**
	 * Constructor with all fields
	 * 
	 */
	public Pet(String name, String breed, String color, int legs, int weight, int not)
	{
		this.name = name;
		this.breed = breed;
		this.color = color;
		this.numberOfLegs = legs;
		this.weight = weight;
		this.numberOfToys = not;
	}

	/**
	 * getter for name
	 * @return  the name
	 */
	public String getName() 
	{
		return name;
	}
	/**
	 * @param name  the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * @return  the breed
	 */
	public String getBreed() 
	{
		return breed;
	}
	/**
	 * @param breed  the breed to set
	 */
	public void setBreed(String breed) 
	{
		this.breed = breed;
	}
	/**
	 * @return  the color
	 */
	public String getColor() 
	{
		return color;
	}
	/**
	 * @param color  the color to set
	 */
	public void setColor(String color) 
	{
		this.color = color;
	}
	/**
	 * @return  the legs
	 */
	public int getLegs() 
	{
		return numberOfLegs;
	}
	/**
	 * @param legs  the legs to set
	 */
	public void setLegs(int legs) 
	{
		this.numberOfLegs = legs;
	}
	/**
	 * @return  the weight
	 */
	public int getWeight() 
	{
		return weight;
	}
	/**
	 * @param weight  the weight to set
	 */
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}	

	/**
	 * @return the numberOfToys
	 */
	public int getNumberOfToys()
	{
		return numberOfToys;
	}

	/**
	 * @param numberOfToys the numberOfToys to set
	 */
	public void setNumberOfToys(int numberOfToys)
	{
		this.numberOfToys = numberOfToys;
	}

	/**
	 * Add another toy for the Pet
	 */

	public void addToy()
	{
		petLock.lock();

		try {

			while(numberOfToys == 5) {
				toyFull.await();
			}

			while(numberOfToys < 5) {
				System.out.println("Adding: Toy from pet " + this + "\n");
				this.numberOfToys++;

			}
			toyEmpty.signalAll();
			System.out.println();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			petLock.unlock();
		}

	}

	/**
	 * Remove a toy from the Pet - meanie
	 */
	public void removeToy()
	{
		petLock.lock();

		try {
			while (numberOfToys <= 0) {
				toyEmpty.await();
			}
			while(numberOfToys > 0) {
				System.out.println("Removing: Toy from pet " + this + "\n");
				this.numberOfToys--;

			}

			toyFull.signalAll();
			System.out.println();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			petLock.unlock();
		}

	}

	@Override
	public String toString() 
	{
		return "Pet [name=" + name + ", breed=" + breed + ", color=" + color
				+ ", numOfLegs=" + numberOfLegs + ", weight=" + weight + ", numberOfToys=" + numberOfToys + "]";
	}

	@Override
	public int compareTo(Pet comparePet) 
	{
		int compareWeight = ((Pet) comparePet).getWeight();

		//ascending order
		return this.weight - compareWeight;
		//subtract the weight of the
		//explicit parameter from this.weight
		//will be negative if this weight < comparePet weight 
		//will be 0 if weights are the same
		//will be positive if this weight > comparePet weight
	}

	// Create static method to count total weight of all pets
	//static methods go last
	public static int getTotalWeight(ArrayList<Pet>p1)
	{
		int total =0;
		for (Pet p : p1)
		{
			total += p.weight;
		}
		return total;
	}

	public static int getTotalnumOfLegs(ArrayList<Pet>p1)
	{
		int total = 0;
		for (Pet p : p1)
		{
			total += p.numberOfLegs;
		}
		return total;
	}
}
