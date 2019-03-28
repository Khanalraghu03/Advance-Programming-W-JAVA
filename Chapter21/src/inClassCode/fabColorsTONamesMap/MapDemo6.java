package inClassCode.fabColorsTONamesMap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
   This program demonstrates a map that maps names to colors.
 */
public class MapDemo6
{
	public static void main(String[] args)
	{      
		//Let's make this better -- let's not hard-code that the only favorite colors are red, green or blue!
		Set<Color> allFavoriteColors = new HashSet<Color>();
		Map<String, Color> favoriteColors = new TreeMap<String, Color>();
		favoriteColors.put("Juliet", Color.BLUE);
		favoriteColors.put("Romeo", Color.GREEN);
		favoriteColors.put("Adam", Color.RED);
		favoriteColors.put("Eve", Color.BLUE);
		favoriteColors.put("Tybalt", Color.BLUE);
		favoriteColors.put("Mercutio", Color.BLUE);
		favoriteColors.put("Lady Montague", Color.RED);
		favoriteColors.put("Friar John", Color.RED);
		favoriteColors.put("Nurse", Color.GREEN);
		favoriteColors.put("Prince Escalus", Color.MAGENTA);
		favoriteColors.put("Potpan", Color.WHITE);
		favoriteColors.put("Homer", Color.CYAN);
		favoriteColors.put("Marge", Color.CYAN);

		// Print all keys and values in the map

		Set<String> allNames = favoriteColors.keySet();
		for (String name : allNames) 	{
			Color c = favoriteColors.get(name);
			//System.out.println(key + " : " + value);
			allFavoriteColors.add(c);
		}

		System.out.println(allFavoriteColors);

		//for each of the colors, build an array list of the names that go with it.
		//now let's put the array list in a hashmap

		Map<Color, ArrayList<String>> colorToListOfNames = new HashMap<Color, ArrayList<String>>();
		//Using Lambdas!
		allFavoriteColors.forEach(color->{
			ArrayList<String> namesAL = new ArrayList<String>(); 
			allNames.forEach(name ->{ if (favoriteColors.get(name)== color) namesAL.add(name); });	
			System.out.println(color + " " + namesAL);
			colorToListOfNames.put(color, namesAL);
		});
		System.out.println("BEFORE BART AND LISA " + colorToListOfNames);
		
		
		//now add a new name (Lisa Simpson) whose favorite color is BLUE		
		//how do I put this in the colorToListOFNames HashMap?

		if (colorToListOfNames.containsKey(Color.BLUE)) {
			ArrayList<String> temp = colorToListOfNames.get(Color.BLUE);			
			temp.add("Lisa Simpson");
			colorToListOfNames.put(Color.BLUE, temp); }
		System.out.println("AFTER LISA " + colorToListOfNames);

		//now add a new name (Bart Simpson) whose favorite color is YELLOW		
		//how do I put this in the colorToListOFNames HashMap?

		if (!colorToListOfNames.containsKey(Color.YELLOW)) {
			ArrayList<String> temp = new ArrayList<String>();		
			temp.add("Bart Simpson");
			colorToListOfNames.put(Color.YELLOW, temp); 
		}
		System.out.println("AFTER BART " + colorToListOfNames);
	}
}

