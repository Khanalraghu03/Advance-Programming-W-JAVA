/**Class: AnxiousShapes
 * @author Raghu Khanal
 * @version 1.0
 * Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class is a very simple shape with movement associated with it if you
 * utilize the calculateTt method
 *
 */

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.util.Random;

public class AnxiousShapes {

    private Shape shape;
    private double delay;
    private int howFarToMoveX;
    private int howFarToMoveY;
    private Random rand = new Random();
    private int x;
    private int y;
    private Color color;
    private int size;

    public AnxiousShapes(int type, int x, int y, int size, double delay,
                        Color color, int hftmX, int hftmY) {
        this.delay = delay;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;

        // Make shapes:
        if (type % 4 == 0) {
            shape = new Circle(getX(), getX(), getSize(), getColor());
        } else if (type % 4 == 1) {
            // how to create triangle
            shape = new Polygon();
            ObservableList<Double> list = ((Polygon) shape).getPoints();
            for (int i = 0; i < 3; i++) {
                list.add(getX() + getSize() * Math.cos(2 * i * Math.PI / 3));
                list.add(getY() - getSize() * Math.sin(2 * i * Math.PI / 3));
            }
            shape.setFill(getColor());

        } else if (type % 4 == 2) {
            // //how to create rectangle
            shape = new Rectangle(getX(), getY(), getWidth(), getHeight());
            shape.setFill(getColor());
        } else if (type % 4 == 3) {
            // //how to create pentagon
            shape = new Polygon();
            ObservableList<Double> list = ((Polygon) shape).getPoints();
            for (int i = 0; i < 6; i++) {
                list.add(getX() + getSize() * Math.cos(2 * i * Math.PI / 6));
                list.add(getY() - getSize() * Math.sin(2 * i * Math.PI / 6));
            }
            shape.setFill(getColor());

        } else {
            System.out.println("Something went wrong!");
        }

        this.delay = delay;
        this.howFarToMoveX = hftmX;
        this.howFarToMoveY = hftmY;
    }
    // ThreadLocalRandom.current().nextInt();

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public int getHowFarToMoveX() {
        return howFarToMoveX;
    }

    public void setHowFarToMoveX(int howFarToMoveX) {
        this.howFarToMoveX = howFarToMoveX;
    }

    public int getHowFarToMoveY() {
        return howFarToMoveY;
    }

    public void setHowFarToMoveY(int howFarToMoveY) {
        this.howFarToMoveY = howFarToMoveY;
    }

    public int getWidth() {
        // will generate the value of x btwn: 10-50

        return rand.nextInt(50 - 10) + 10;
    }
    public int getHeight() {
        // will generate the value of y btwn: 25-100
        return rand.nextInt(100 - 25) + 25;
    }

    public TranslateTransition calculateTt() {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(delay), shape);
        tt.setToX(shape.getLayoutX() + howFarToMoveX);
        tt.setToY(shape.getLayoutY() + howFarToMoveY);
        // Let the animation run forever -- if the shape
        // tries to move "off-screen" it will return to the beginning
        tt.setCycleCount(TranslateTransition.INDEFINITE);
        return tt;
    }

    // another getter and setter

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AnxiousShape [shape=" + shape + ", delay=" + delay
                + ", howFarToMoveX=" + howFarToMoveX + ", howFarToMoveY="
                + howFarToMoveY + "]";
    }

}
