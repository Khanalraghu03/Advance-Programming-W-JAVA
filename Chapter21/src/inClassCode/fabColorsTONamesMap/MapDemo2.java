package inClassCode.fabColorsTONamesMap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
   This program demonstrates a map that maps names to colors.
 */
public class MapDemo2
{
	public static void main(String[] args)
	{      

		Map<String, Color> favoriteColors = new HashMap<String, Color>();
		favoriteColors.put("Juliet", Color.BLUE);
		favoriteColors.put("Romeo", Color.GREEN);
		favoriteColors.put("Adam", Color.RED);
		favoriteColors.put("Eve", Color.BLUE);
		favoriteColors.put("Tybalt", Color.BLUE);
		favoriteColors.put("Mercutio", Color.BLUE);
		favoriteColors.put("Lady Montague", Color.RED);
		favoriteColors.put("Friar John", Color.RED);
		favoriteColors.put("Nurse", Color.GREEN);


		// Print all keys and values in the map

		Set<String> allNames = favoriteColors.keySet();
		for (String key : allNames) {
			Color value = favoriteColors.get(key);
			System.out.println(key + " : " + value);

		}

		Color adamsColor = favoriteColors.get("Adam");
		System.out.println("Adam's favorite color: " + adamsColor);

		ArrayList<String> favoriteColorBlue = new ArrayList<String>();
		ArrayList<String> favoriteColorGreen = new ArrayList<String>();
		ArrayList<String> favoriteColorRed = new ArrayList<String>();

		for (String name : allNames) {
			if (favoriteColors.get(name)== Color.BLUE) {
				System.out.println("Blue is the favorite color of " + name);
				favoriteColorBlue.add(name);
			}
			else if (favoriteColors.get(name)== Color.RED) {
				System.out.println("Red is the favorite color of " + name);
				favoriteColorRed.add(name);
			}
			else if (favoriteColors.get(name)== Color.GREEN) {
				System.out.println("Green is the favorite color of " + name);
				favoriteColorGreen.add(name);
			}		
		}
		System.out.println("Blue is the favorite color of " + favoriteColorBlue);
		System.out.println("Red is the favorite color of " + favoriteColorRed);
		System.out.println("Green is the favorite color of " + favoriteColorGreen);

	}
}
