package InClassWork;//FROM https://examples.javacodegeeks.com/desktop-java/javafx/javafx-transition-example/#trans
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxTransitionExample4 extends Application
{


    @Override
    public void start(Stage stage)
    {
        // Create the Text
        /*
         * Text text = new Text("A Translate Transition Example");
         * text.setFont(Font.font(36));
         */

        // Create the Circles
        Circle circle = new Circle(100, 50, 25);

        // Create the Circles
        Circle circle1 = new Circle(50, 150, 15);



        // Create the VBox
        VBox root = new VBox(circle, circle1);
        // Set the Size of the VBox
        root.setPrefSize(500, 100);
        // Set the Style-properties of the VBox
        /*
         * root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" +
         * "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" +
         * "-fx-border-color: blue;");
         */

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("Scrolling Text using a Translate Transition");
        // Display the Stage
        stage.show();

        // Set up a Translate Transition for the Text object
        TranslateTransition trans = new TranslateTransition(Duration.seconds(2), circle);
        trans.setFromX(scene.getWidth());
        trans.setToX(-1.0 * circle.getLayoutBounds().getWidth());
        // Let the animation run forever
        trans.setCycleCount(TranslateTransition.INDEFINITE);
        // Reverse direction on alternating cycles
        trans.setAutoReverse(true);
        // Play the Animation
        trans.play();

        // Set up a Translate Transition for the Text object
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(3), circle1);
        trans1.setFromX(scene.getWidth());
        trans1.setToX(-1.0 * circle1.getLayoutBounds().getWidth());
        // Let the animation run forever
        trans1.setCycleCount(TranslateTransition.INDEFINITE);
        // Reverse direction on alternating cycles
        trans1.setAutoReverse(true);
        // Play the Animation
        trans1.play();
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }
}
