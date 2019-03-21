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
public class MapDemo4
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

		/*ArrayList<String> favoriteColorBlue = new ArrayList<String>();
		ArrayList<String> favoriteColorGreen = new ArrayList<String>();
		ArrayList<String> favoriteColorRed = new ArrayList<String>();
		for (String name : keySet) {


			System.out.println("Blue is the favorite color of " + favoriteColorBlue);
			System.out.println("Red is the favorite color of " + favoriteColorRed);
			System.out.println("Green is the favorite color of " + favoriteColorGreen); 
		} */

		//for each of the colors, build an array list of the names that go with it.
	

		Map<Color, ArrayList<String>> colorToListOfNames = new HashMap<Color, ArrayList<String>>(); 	//******HashMnow let's put the array list in a hashmap
		for (Color color : allFavoriteColors) {
			ArrayList<String> namesAL = new ArrayList<String>(); //how many array lists will we have?
			//how many colors do we have?
			for (String name : allNames) {
				if (favoriteColors.get(name)== color) {
					//System.out.println(color + " is the favorite color of " + name);
					namesAL.add(name);
				} 		
			}
			System.out.println(color + " " + namesAL);
			colorToListOfNames.put(color, namesAL);  	//now let's put the array list in a hashmap
		}
		System.out.println(colorToListOfNames); 
}
}

