package review;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;
import java.util.Scanner;

public class DuckDuckGoose {

    public static void addToList() {

        File players = new File("./src/review/players.txt");
        LinkedList<String> circularList = new LinkedList<>();
        Scanner readFile = null;
        try {
            readFile = new Scanner(players);
            while (readFile.hasNextLine()) {
                circularList.add(readFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        System.out.println(circularList + "\n");

        Random rand = new Random();
        int num;
        System.out.println("************************* Let the game begin! *********************************");
        do{
            num = rand.nextInt(10)+1;
            for(int i = 1; i < num; i++) {

                circularList.addLast(circularList.peek());
                circularList.remove();
            }
            System.out.println("Ducked Ducked " + num + " times" );
            System.out.println("Goose! --> " + circularList.peek());
            circularList.remove();
        } while(circularList.size() > 1);
        System.out.println("************************* Game Over!!! *********************************");

        Iterator it = circularList.iterator();
        System.out.println("\n" +it.next() + " is the winner");

    }

    public static void main(String[] args) {
        DuckDuckGoose.addToList();
    }

}