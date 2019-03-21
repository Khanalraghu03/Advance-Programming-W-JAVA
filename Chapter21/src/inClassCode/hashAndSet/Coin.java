package inClassCode.hashAndSet;

/**
   A coin with a monetary value.
*/
public class Coin implements Comparable
{
   private double value;
   private String name;

   /**
      Constructs a coin.
      @param aValue the monetary value of the coin.
      @param aName the name of the coin
   */
   public Coin(double aValue, String aName) 
   { 
      value = aValue; 
      name = aName;
   }

   /**
      Gets the coin value.
      @return the value
   */
   public double getValue() 
   {
      return value;
   }

   /**
      Gets the coin name.
      @return the name
   */
   public String getName() 
   {
      return name;
   }

   public boolean equals(Object otherObject)
   {
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      Coin other = (Coin) otherObject;

      //return value == other.value && name.equalsIgnoreCase(other.name);
      return value == other.value;
   }

   public int hashCode()
   {
      //int h1 = name.toLowerCase().hashCode();
      int h2 = new Double(value).hashCode();
      final int HASH_MULTIPLIER = 29; //we always use prime number
//      int h = HASH_MULTIPLIER * h1 + h2;
      int h = HASH_MULTIPLIER * h2;
      return h;
   }


   public String toString()
   {
      return "Coin[value=" + value + ",name=" + name + "]";
   }

   @Override
   public int compareTo(Object o)
   {
      Coin otherCoin = (Coin)o;
//      return (int)(value - otherCoin.value);
      //I should really check within a small delta for equality
      if(this.value > otherCoin.value) return 1;
      else if(this.value < otherCoin.value) return -1;
      return 0;
   }

}
