/**
 * @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * @version 1.0
 * This class will keep removing things from the queue
 */

/**
 * @author Horstmann
 * @version
 * This class will keep removing things from the queue
 */

public class RemoveToysRunnable implements Runnable
{
	private Pet pet;
	private int count;
	private static final int DELAY = 1000;

	/**
      Constructs the RemoveToysRunnable 
      Sets the pet with a no toys and count.
      @param aPet the pet that the remove toys is going to consume from
      @param //count the number of times that remive toys is going to consume
	 */
	public RemoveToysRunnable(Pet aPet, int aCount)
	{
		count = aCount;
		pet = aPet;
	}
	public void run()
	{
//		try
//		{
//			for (...)
//			{
//				pet.removeToy();
//				Thread.sleep(DELAY);
//			}
//		}
//		catch (InterruptedException exception) {}
	}
}
