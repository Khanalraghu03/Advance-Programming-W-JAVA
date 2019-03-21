package inClassCode;

import java.util.Scanner;

/**
 A program for demonstrating printing of the BinarySearchTree class.
 */
public class TreePrintDemo
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        BinarySearchTree t = new BinarySearchTree();

        boolean done = false;
        while (!done)
        {
            System.out.println("Enter a word or a blank line to quit:");
            String input = in.nextLine();
            if (input.length() == 0)
                done = true;
            else
                t.add(input);
        }

        done = false;
        while (!done)
        {
            System.out.println("P)reorder, I)norder, pO)storder, Q)uit:");
            String input = in.nextLine().toUpperCase();
            if (input.equals("Q"))
                done = true;
            else if (input.equals("P"))
                t.printPreOrder();
            else if (input.equals("I"))
                t.print();
            else if (input.equals("O"))
                t.printPostOrder();
        }
    }
}

