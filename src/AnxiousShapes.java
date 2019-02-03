import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class AnxiousShapes extends Application {
    private Shape shape;
    private Random rand = new Random();
    private int size;
    private double delay;
    private int howFarToMoveX;
    private int howFarToMoveY;
    private int x;
    private int y;


    public AnxiousShapes(int type, int x, int y, int size, double delay,
                        Color color, int hftmX, int hftmY) {
        this.delay = delay;
        this.x = x;
        this.y = y;

        //Make shapes:
        if (type % 4 == 0) {
            shape = new Circle(x, y, size, color);
        } else if (type % 4 == 1) {
            //how to create triangle
            shape = new Polygon();
            ObservableList<Double> list = ((Polygon) shape).getPoints();
            for (int i = 0; i < 3; i++) {
                list.add(x + size * Math.cos(2 * i * Math.PI / 3));
                list.add(y - size * Math.sin(2 * i * Math.PI / 3));
            }
            shape.setFill(color);

        } else if (type % 4 == 2) {
//              //how to create rectangle
//               shape = new Rectangle(x, y, width, height);
        } else if (type % 4 == 3) {
//              //how to create pentagon
                 shape = new Polygon();
            ObservableList<Double> list = ((Polygon) shape).getPoints();
            for (int i = 0; i < 6; i++) {
               list.add(x + size * Math.cos(2 * i * Math.PI / 6));
               list.add(y - size * Math.sin(2 * i * Math.PI / 6));
            }
            shape.setFill(color);

        } else {
            System.out.println("Something went wrong!");
        }

    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Shape rectangle = drawRectangle();
        Shape circle = drawCircle();

        pane.getChildren().add(rectangle);
        pane.getChildren().add(circle);

        Scene scene = new Scene(pane, getScreenSize(), getScreenSize());

        primaryStage.setTitle("Anxious Shape");


        primaryStage.setScene(scene);
        primaryStage.show();
    }

//     ThreadLocalRandom.current().nextInt();

//    public Shape getShape() {
//        return shape;
//    }
//
//    public void setShape(Shape shape) {
//        this.shape = shape;
//    }
//
//    public double getDelay() {
//        return delay;
//    }
//
//    public void setDelay(double delay) {
//        this.delay = delay;
//    }
//
//    public int getHowFarToMoveX() {
//        return howFarToMoveX;
//    }
//
//    public void setHowFarToMoveX(int howFarToMoveX) {
//        this.howFarToMoveX = howFarToMoveX;
//    }
//
//    public int getHowFarToMoveY() {
//        return howFarToMoveY;
//    }
//
//    public void setHowFarToMoveY(int howFarToMoveY) {
//        this.howFarToMoveY = howFarToMoveY;
//    }
//
//    public TranslateTransition calculateTt() {
//        TranslateTransition tt = new TranslateTransition(
//                Duration.seconds(delay), shape);
//        tt.setToX(shape.getLayoutX() + howFarToMoveX);
//        tt.setToY(shape.getLayoutY() + howFarToMoveY);
//        // Let the animation run forever -- if the shape
//        // tries to move "off-screen" it will return to the beginning
//        tt.setCycleCount(TranslateTransition.INDEFINITE);
//        return tt;
//    }
//
    public int generateWidth() {
        //We will generate the value of x btwn: 10-50

        return rand.nextInt(50-10) +10;
    }
    public int generateHeight() {
        //We will generate the value of y btwn: 25-100
        return rand.nextInt(100-25) +25;
    }

    public int getScreenSize() {
        return rand.nextInt(750-500)+500;
    }
//
//    public int getNumOfShapes() {
//        return rand.nextInt(200) + 100;
//    }
//    public Color getColor() {
//        return new Color(Math.random(), Math.random(), Math.random(), Math.random());
//    }
    public int generatePositionX(){
        return rand.nextInt(getScreenSize());
    }
    public int generatePositionY(){
        return rand.nextInt(getScreenSize());
    }

    public Rectangle drawRectangle() {
        return new Rectangle(generatePositionX(), generatePositionY(),generateWidth() , generateHeight());
    }
    public Circle drawCircle() {
        return new Circle(generatePositionX(), generatePositionY(), 25 );
    }
    public Polygon drawPentagon() {
        return new Polygon();
    }
    public Polygon drawTriangle() {
        return new Polygon();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
