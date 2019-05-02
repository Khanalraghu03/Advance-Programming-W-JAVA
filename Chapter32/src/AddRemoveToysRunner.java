import java.io.File;

/**
 * @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * @version 1.0
 * This class is the main for the AddToysRunnable and RemoveToysRunnable files
 */


public class AddRemoveToysRunner
{
   private static int MAX_TOYS = 5;
   private static int ITERATIONS = 100;

   public static void main(String args[])
   {
//      File f = new File("./Chapter21/src");
      Cat c = new Cat("","","",Litter.SCOOPABLE,0,0,0);
      Dog d = new Dog("","","",true,"",5,TailType.BOBBED);

      //Adds the toy
      AddToysRunnable catToyProducer = new AddToysRunnable(c,ITERATIONS);
      AddToysRunnable dogToyProducer = new AddToysRunnable(d, ITERATIONS);

      //Removes the toy
      RemoveToysRunnable catToyConsumer = new RemoveToysRunnable(c,ITERATIONS);
      RemoveToysRunnable dogToyConsumer = new RemoveToysRunnable(d,ITERATIONS);

      Thread t1 = new Thread(catToyProducer);
      Thread t2 = new Thread(catToyConsumer);
      Thread t3 = new Thread(dogToyProducer);
      Thread t4 = new Thread(dogToyConsumer);

      t1.start();
      t2.start();
      t3.start();
      t4.start();
   }
}
