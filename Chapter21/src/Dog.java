 /** Class:Dog
 *@ author Raghu Khanal
 * version 1.0
 * Course: ITEC 3150 Spring 2019
 * Written: February 24, 2019
 *This class - This class subclass of Pet class and create the Dogs data, methods and constructor.
 *Purpose:- The purpose of the class is create Dogs.
 */


public class Dog extends Pet
{

	private boolean trained;
	private String size; //large, medium, small
	private TailType tail;

	public Dog (String name, String breed, String coatColor, boolean trained, String size, int weight, TailType tt)
	{
		super(name, breed, coatColor, weight);
		this.trained = trained;
		this.size = size;
		this.tail = tt;
	}

	public Dog (String name, String breed, String coatColor, int legs, int weight, int not, boolean trained, String size, TailType t)
	{
		super(name, breed, coatColor, legs, weight, not);
		this.trained = trained;
		this.size = size;
		this.tail = t;
	}

	public boolean getTrained()
	{
		return trained;
	}

	/**
	 * @param trained the trained to set
	 */
	public void setTrained(boolean trained)
	{
		this.trained = trained;
	}

	/**
	 * @return
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size)
	{
		this.size = size;
	}



	/**
	 * @return the tail
	 */
	public TailType getTail()
	{
		return tail;
	}

	/**
	 * @param tail the tail to set
	 */
	public void setTail(TailType tail)
	{
		this.tail = tail;
	}

	/* For SOPing to the console
	 * @see Assignment5.Pet#toString()
	 */
	@Override
	public String toString()
	{
		return "Dog =" + super.toString() + "trained=" + trained + ", size=" + size + "]"+ "\n";
	}

}

