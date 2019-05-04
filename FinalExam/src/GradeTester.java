/**
 * /**Class: GradeTester
 *  * @author Raghu Khanal
 *  * @version 1.0
 *  * Course : ITEC 3150
 *  * Written: May 2, 2019
 *
 *  This class implements a GradeTester by
 *  creating 10 random grades, then adding them to a BinarySearchTree.
 *  It prints valid grades in a sorted order first except for A+, F-, F+ because they considered invalid.
 *      Then it prints the invalid grades in sorted format.
 */

public class GradeTester {
    public static void main(String[] args) {

        Grade g1 = new Grade("A");
        Grade g2 = new Grade("F");
        Grade g3 = new Grade("C");
        Grade g4 = new Grade("A-");
        Grade g5 = new Grade("F+");
        Grade g6 = new Grade("J");
        Grade g7 = new Grade("F");
        Grade g8 = new Grade("F-");
        Grade g9 = new Grade("A+");
        Grade g10 = new Grade("K");

        BinarySearchTree b = new BinarySearchTree();
        b.add(g1);
        b.add(g2);
        b.add(g3);
        b.add(g4);
        b.add(g5);
        b.add(g6);
        b.add(g7);
        b.add(g8);
        b.add(g9);
        b.add(g10);

        b.print();

    }
}