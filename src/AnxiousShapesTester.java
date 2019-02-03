/**Class: AnxiousShapesTester
 * @author Raghu Khanal
 * @version 1.0
 * Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class is a tester class for AnxiousShapes and generates bunch of shapes with the help
 *  of the AnxiousShapes.java class. It add them to the GUI.
 *
 */
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AnxiousShapesTester extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random rand = new Random();
        // int type = 0;
        int windowSize = rand.nextInt(750 - 500) + 500;
        int x;
        int y;
        int size;
        int delay;
        Color color;
        int hftmX = 3;
        int hftmY = 2;
        int numOfShapes = rand.nextInt(200 - 100) + 100;

        // private int maxSize;
        // private int minSize;
        // private int numOfShape;
        // private int windowSize;

        // int type, int x, int y, int size, double delay, Color color, int
        // hftmX, int
        // hftmY
        // int num = 5;
        ArrayList asList = new ArrayList();
        for (int type = 0; type < numOfShapes; type++) {

            x = rand.nextInt(windowSize);
            y = rand.nextInt(windowSize);
            size = rand.nextInt(100 - 10) + 10;
            delay = rand.nextInt(25 - 10) + 10;
            color = new Color(Math.random(), Math.random(), Math.random(),
                    Math.random());

            AnxiousShapes as = new AnxiousShapes(type, x, y, size, delay, color,
                    hftmX, hftmY);
            as.calculateTt();
            asList.add(as.getShape());
        }
//        System.out.println(numOfShapes + " " + windowSize);


        Pane pane = new Pane();

        for (int i = 0; i < asList.size(); i++) {
            pane.getChildren().add((Node) asList.get(i));
        }

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Nervous Shapes");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
