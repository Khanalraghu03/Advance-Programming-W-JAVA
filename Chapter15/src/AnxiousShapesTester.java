/**Class: AnxiousShapesTester
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class is a tester class for AnxiousShapes and generates bunch of shapes with the help
 *  of the AnxiousShapes.java class. It add them to the GUI.
 *
 */

import java.util.Random;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AnxiousShapesTester extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random rand = new Random();
        int type;
        int screenSize = rand.nextInt(750 - 500) + 500;
        int x;
        int y;
        int maxSize = rand.nextInt(100 - 50) + 50;
        int minSize = rand.nextInt(25 - 10) + 10;
        int size;
        double delay;
        Color colorOfShape;
        int hftmX = 3;
        int hftmY = 2;
        int numOfShapes = rand.nextInt(200 - 100) + 100;

        AnxiousShapes as;
        Group group = new Group();
        for (type = 0; type < numOfShapes; type++) {
            x = rand.nextInt(screenSize);
            y = rand.nextInt(screenSize);
            size = rand.nextInt(maxSize - minSize) + minSize;
            delay = (rand.nextInt(25 - 10) + 10)/1000.0;
            colorOfShape = new Color(Math.random(), Math.random(), Math.random(),
                    Math.random());
            as = new AnxiousShapes(type, x, y, size, delay, colorOfShape, hftmX, hftmY);
            as.calculateTt().play();
            group.getChildren().add(as.getShape());
        }

        Scene scene = new Scene(group, screenSize, screenSize);
        primaryStage.setTitle("Nervous Shapes");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
