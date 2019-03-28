package inClassCode.fabColorsTONamesMap;

import java.awt.Color;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
   This program demonstrates a map that maps names to colors.
   Find out whose favorite color is blue
*/
public class MapDemo1
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

      // Print all keys and values in the map

      Set<String> keySet = favoriteColors.keySet();
      for (String key : keySet)
      {
         Color value = favoriteColors.get(key);
         System.out.println(key + " : " + value);
      }
      

         Color adamsColor = favoriteColors.get("Adam");
         System.out.println("Adam's favorite color: " + adamsColor);
         
         for (String name : keySet) {
			if (favoriteColors.get(name)== Color.BLUE)
				System.out.println("Blue is the favorite color of " + name);
		}
     
   }
}
