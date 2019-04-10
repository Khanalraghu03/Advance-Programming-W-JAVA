import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;


/**Class: GenerateDataForPets
 * @author Raghu Khanal
 * @version 1.0
 * Course : ITEC 3150 Spring 2019
 * Written: Mar 19, 2019
 *
 * This class Quickly gens 50 cats and 50 dogs (Change NUM_OF_PETS to modify)
 * Purpose:  To very quickly generate test data for Pet, place them in an array list,
 * hash set and tree set.
 *
 */
public class GenerateDataForPets
{
	private String nameCat = "Snoot";
	private String nameDog = "Rover";
	private String[] catBreedArray = {"calico", "tabby", "turkishvan"};
	private String[] dogBreedArray = {"lab", "doberman", "poodle"};
	private String[] colArray = {"black", "brown", "grey"};
	private Litter[] litterValues = Litter.values();
	private TailType[] tailValues = TailType.values();
	private String[] sizes = {"small", "medium", "large"};
	private Random ran = new Random();
	private ArrayList<Pet> pal = new ArrayList<Pet>();
	private HashSet<Pet> phs;
	private TreeSet<Pet> pts;
	private boolean b = true;
	private static PrintWriter pw;
	private static int NUM_OF_PETS = 100;


	/**
	 * @param i - which pet is this?
	 * @return a pet
	 */
	public Pet createPet(int i)
	{
		Pet p;
		if (i % 2 == 0)
		{
			p = new Cat(nameCat + (i/2), catBreedArray[ran.nextInt(3)], colArray[ran.nextInt(3)], litterValues[ran.nextInt(4)] , ran.nextInt(31));
		}
		else
		{
			if (b) b=false; else b=true;
			p = new Dog(nameDog + (i/2), dogBreedArray[ran.nextInt(3)], colArray[ran.nextInt(3)], b, sizes[ran.nextInt(3)], ran.nextInt(46), tailValues[ran.nextInt(6)]);
		}
		return p;
	}


	/**
	 * main driver
	 * @param args
	 */
	public static void main(String[] args)
	{
		GenerateDataForPets gdfp = new GenerateDataForPets();
		for (int i = 0; i < NUM_OF_PETS; i++)
		{
			Pet p = gdfp.createPet(i);
			gdfp.pal.add(p);
		}
		gdfp.phs = new HashSet<Pet>(gdfp.pal);
		gdfp.pts = new TreeSet<Pet>(gdfp.phs);
		System.out.println("ArrayList is: ");
		System.out.println(gdfp.pal);
		System.out.println("HashSet is: ");
		System.out.println(gdfp.phs);
		System.out.println("TreeSet is: ");
		System.out.println(gdfp.pts);
		try
		{
			pw = new PrintWriter("./Chapter21/src/Pets.txt");
			for (Pet p:gdfp.pal)
			{
				if (p instanceof Cat)
					pw.print("Cat ");
				else if (p instanceof Dog)
					pw.print("Dog ");

				pw.print(p.getName() + " ");
				pw.print(p.getBreed() + " ");
				pw.print(p.getColor() + " ");
				pw.print(p.getLegs() + " ");
				pw.print(p.getWeight() + " ");
				pw.print(p.getNumberOfToys() + " ");
				if (p instanceof Cat)
					pw.print(((Cat) p).getLitterType().toString() + " ");
				else if (p instanceof Dog)
					pw.print(((Dog) p).getTail().toString() + " ");
				pw.println();
			}
		}
		catch (IOException ioe) {ioe.printStackTrace();}
		finally {pw.close();}
	}

}
