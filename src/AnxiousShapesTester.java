import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;
import static javafx.application.Application.launch;
//import javafx.scene.paint.Color;

public class AnxiousShapesTester {
    public static void main(String[] args) {

        Random rand = new Random();
        int type = 0;
        int screenSize = rand.nextInt(750)+500;
        int x = rand.nextInt(screenSize);
        int y = rand.nextInt(screenSize);
        int size = 67;
        int delay = rand.nextInt(25) + 10;
      Color color = new Color(Math.random(), Math.random(), Math.random(), Math.random());
        int hftmX = 3;
        int hftmY = 2;
//    int numOfShapes = rand.nextInt(200) + 100;
        //randomly generate these



//        int maxSize = rand.nextInt(100) + 50;
//        int minSize = rand.nextInt(25) + 10;
//        int windowSize = rand.nextInt(750) + 500;
        AnxiousShapes aShapes = null;
        Pane pane = new Pane();
//        ArrayList<AnxiousShapes> list = new ArrayList<>();
//        while(type < numOfShapes) {
             aShapes = new AnxiousShapes(type, x, y, size, delay,
                    color, hftmX, hftmY);
////            list.add(aShapes);
//
//            type++;
//        }


        // will have different anxious shapes every time because I am using a Random
        // class.

        // int type, int x, int y, int size, double delay, Color color, int hftmX, int
        // hftmY
        pane.getChildren().add(aShapes.getShape());
        Scene scene = new Scene(pane, 400, 400);

        Stage stage = new Stage();
        stage.setScene();
        aShapes.start(stage);



    }


}
