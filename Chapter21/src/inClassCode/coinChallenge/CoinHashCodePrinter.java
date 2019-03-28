package inClassCode.coinChallenge;

import java.util.HashSet;
import java.util.Set;

/**
   A program that prints hash codes of coins.
 */
public class CoinHashCodePrinter
{
	public static void main(String[] args)
	{
		Coin coin1 = new Coin(0.25, "quarter");
		Coin coin2 = new Coin(0.25, "quarter");
		Coin coin3 = new Coin(0.05, "nickel");
		Coin coin4 = new Coin(0.05, "Nickel"); 
		Coin coin5 = new Coin(0.05, "NICKEL"); 
		Coin coin6 = new Coin(0.05, "Quarter");
		Coin coin7 = new Coin(0.25, "QuarteR");
		Coin coin8 = new Coin(0.25, "Quarter");



		System.out.println("hash code of coin1=" + coin1.hashCode());
		System.out.println("hash code of coin2=" + coin2.hashCode());
		System.out.println("hash code of coin3=" + coin3.hashCode());
		System.out.println("hash code of coin4=" + coin4.hashCode());
		System.out.println("hash code of coin5=" + coin5.hashCode());
		System.out.println("hash code of coin6=" + coin6.hashCode());
		//System.out.println("hash code of coin7=" + coin7.hashCode());

		Set<Coin> coins = new HashSet<Coin>();
		coins.add(coin1);
		coins.add(coin2);
		coins.add(coin3);
		coins.add(coin4);
		coins.add(coin5);
		coins.add(coin6);
		//coins.add(coin7);

		for (Coin c : coins) {
			System.out.println(c);
			System.out.println(c.hashCode());
		}
		
		if (coin5.equals(coin4) && coin5.equals(coin3)) System.out.println("YAY");
		else System.out.println("BOO");
		if (coin6.equals(coin2)) System.out.println("BOO!!!"); else System.out.println("YAY!!!!");;
		if (coin7.equals(coin8) && coin2.equals(coin7)) System.out.println("YAY");
		else System.out.println("BOO");
	}
}
