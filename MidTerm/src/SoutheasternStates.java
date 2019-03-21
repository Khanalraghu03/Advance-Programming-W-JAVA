import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

/**Class: midterm.SoutheasternStates
 * @author ITEC 3150
 * @version 1.0
 * midterm.Course : ITEC 3150 Spring 2019
 * Written: Feb 18, 2019
 *
 *
 * Given a state in the southeast, display the capital
 *
 *
 */

public class SoutheasternStates extends Application {
    // Declare an array of Strings for flag titles
    private String[] states = {"Alabama", "Florida", "Georgia", "Kentucky", "Mississippi", "North Carolina",
            "South Carolina", "Tennessee", "Virginia", "West Virginia"};

    // Declare an array of strings for state capitals
    private String[] stateCapitals = {"Montgomery", "Tallahassee", "Atlanta", "Frankfort", "Jackson", "Raleigh",
            "Columbia", "Nashville", "Richmond", "Charleston"};

    // Launch the application
    public void start(Stage stage)
    {
        // Set title for the stage
        stage.setTitle("States in a Combo Box");

        // Create a label
        Label labelCapital =
                new Label(stateCapitals[0]);

        // Create a combo box
        ComboBox<String> comboBoxStates =
                new ComboBox<String>(FXCollections
                        .observableArrayList(states));

        comboBoxStates.setValue("Alabama");
        labelCapital.setText("The capital is Montgomery");


        // Create action event
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        //fill in the code which will find the state selected
//                        int num = comboBoxStates.getSelectionModel().getSelectedIndex();
//                        labelCapital.setText("The capital is " + stateCapitals[num]);

                        for(int i = 0; i<states.length;i++) {
                            if(states[i].equals(comboBoxStates.getValue())) {
                                labelCapital.setText(stateCapitals[i]);
                            }
                        }
                        //and according to the state chosen will display the correct capital
                        //from the stateCapitals array
//                        comboBoxStates.setOnAction(e -> setDisplay(items.indexOf(cbo.getValue())));
                    }
                };

        // Set on action
        comboBoxStates.setOnAction(event);


        // Create a tile pane
        TilePane tilePane = new TilePane(comboBoxStates, labelCapital);

        // Create a scene
        Scene scene = new Scene(tilePane, 200, 200);

        // Set the scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[])
    {
        // Launch the application
        launch(args);
    }
} 