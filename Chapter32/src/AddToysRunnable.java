
import  java.util.Date;

/*** @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * This class adds toys into the queue.
 * */

/**
 * @author Horstmann
 * @version
 * This class will keep putting things into the queue.
 */

public class AddToysRunnable implements Runnable
{
   private Pet pet;
   private int count;
   private static final int DELAY = 1000;

   /**
      Constructs the AddToysRunnable 
      Sets the pet with a no toys and count.
      @param aPet the pet that the add toys is going to produce from
      @param //count the number of time that add toys is going to produce
   */
   public AddToysRunnable(Pet aPet, int aCount)
   {
      count = aCount;
      pet = aPet;
   }

	public void run()
	{
		try
		{
			for (int i = 0; i < count; i++)
			{
				pet.addToy();
				Thread.sleep(DELAY);
			}
		}
		catch (InterruptedException exception)
		{

		}
	}
}
