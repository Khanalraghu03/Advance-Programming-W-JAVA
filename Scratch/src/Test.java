import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class Test extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Test");

//		primaryStage.setScene(activateButtonExample());
		primaryStage.setScene(activateRadioButtonExample());
//		primaryStage.setScene(activateCheckBoxExample());

        primaryStage.show();
    }

    public Scene activateButtonExample()
    {

        GridPane grid = new GridPane();

        Button btn1 = new Button("btn1");
        grid.add(btn1, 0, 0);

        btn1.setOnAction(event ->
        {
            System.out.println("btn1");
        });

        Scene scene = new Scene(grid, 200, 250);

        return scene;
    }

    public Scene activateRadioButtonExample()
    {
        GridPane grid = new GridPane();

        grid.add(new Label("Radio Button Example"), 0, 0);

        // create a toggle group
        ToggleGroup tg1 = new ToggleGroup();
        ToggleGroup tg2 = new ToggleGroup();

        // create RadioButton
        RadioButton r1 = new RadioButton("one");
        RadioButton r2 = new RadioButton("two");
        RadioButton r3 = new RadioButton("three");
        RadioButton r4 = new RadioButton("four");

        // assign radiobuttons to toggle group
        r1.setToggleGroup(tg1);
        r2.setToggleGroup(tg1);
        r3.setToggleGroup(tg2);
        r4.setToggleGroup(tg2);

        // add to grid
        grid.add(r1, 0, 1);
        grid.add(r2, 0, 2);
        grid.add(r3, 0, 3);
        grid.add(r4, 0, 4);

        // add event handler for r1
        r1.setOnAction(event -> {
            System.out.println(r1.getText());
        });

        Scene scene = new Scene(grid, 200, 250);

        return scene;
    }

    public Scene activateCheckBoxExample()
    {
        GridPane grid = new GridPane();

        grid.add(new Label("Checkbox example"), 0, 0);

        // create checkboxes
        CheckBox cb1 = new CheckBox("one");
        CheckBox cb2 = new CheckBox("two");
        CheckBox cb3 = new CheckBox("three");
        CheckBox cb4 = new CheckBox("four");

        // add to grid
        grid.add(cb1, 0, 1);
        grid.add(cb2, 0, 2);
        grid.add(cb3, 0, 3);
        grid.add(cb4, 0, 4);

        // add event handler for cb1
        cb1.setOnAction(Event -> {
            if (cb1.isSelected())
            {
                System.out.println("cb1 got selected");
            } else
            {
                System.out.println("cb1 got unselected");
            }

        });

        Scene scene = new Scene(grid, 200, 250);

        return scene;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
