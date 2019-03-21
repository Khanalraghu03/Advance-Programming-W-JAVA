import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**Class: midterm.RadioButtonColor
 * @author ITEC 3150
 * @version 1.0
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: Feb 18, 2019
 *
 *
 * Write a simple change listener to toggle the colors according to the radio button selected/chosen
 *
 *
 */

public class RadioButtonColor extends Application {

    @Override
    public void start(Stage stage) {


        // Group
        ToggleGroup group = new ToggleGroup();



        // Radio 1: Red
        RadioButton rbRed = new RadioButton("Red");
        rbRed.setToggleGroup(group);


        // Radio 2: Green
        RadioButton rbGreen = new RadioButton("Green");
        rbGreen.setToggleGroup(group);

        // Radio 3: Blue
        RadioButton rbBlue = new RadioButton("Blue");
        rbBlue.setToggleGroup(group);

        ////////////////////////////////////////////////////////////////////////////
        //My code:
        rbRed.setOnAction(event -> {
            rbRed.setTextFill(Color.RED);
            rbGreen.setTextFill(Color.BLACK);
            rbBlue.setTextFill(Color.BLACK);
        });

        rbBlue.setOnAction(event -> {
            rbBlue.setTextFill(Color.BLUE);
            rbRed.setTextFill(Color.BLACK);
            rbGreen.setTextFill(Color.BLACK);

        });

        rbGreen.setOnAction(event -> {
            rbGreen.setTextFill(Color.GREEN);
            rbRed.setTextFill(Color.BLACK);
            rbBlue.setTextFill(Color.BLACK);
        });
        //////////////////////////////////////////////////////////////////////////

        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        root.getChildren().addAll(rbRed, rbGreen, rbBlue);


        Scene scene = new Scene(root, 175, 100);
        stage.setScene(scene);
        stage.setTitle("JavaFX Radio Button Colors");
        scene.setRoot(root);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
