import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Class: currentWork.DayOfWeek
 *
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 3, 2019
 * <p>
 * <p>
 * This class asks user to select Month and day and type in the year
 * Once the user have pressed enter:
 *      it determines the Day of the Week
 */
public class DayOfWeek extends Application {
    Label mLabel = new Label("Month: ");
    ComboBox month = new ComboBox();
    Label dLabel = new Label("Day: ");
    ComboBox day = new ComboBox();
    Label yLabel = new Label("Year: ");
    TextField year = new TextField();
    GridPane pane = new GridPane();
    Label showDay = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {

        month.getItems().addAll("January", "February",
                "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December");
        day.getItems().addAll("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30", "31");
        final String[] DAY_OF_WEEK_STRING = {"Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"};

        pane.add(mLabel, 0, 0);
        pane.add(month, 1, 0);
        pane.add(dLabel, 2, 0);
        pane.add(day, 3, 0);
        pane.add(yLabel, 4, 0);
        pane.add(year, 5, 0);
        pane.add(showDay, 0, 1);

        year.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                int indexOfMonth = month.getSelectionModel().getSelectedIndex();
                int getDay = day.getSelectionModel().getSelectedIndex() + 1;
                try {
                    int getYear = Integer.parseInt(year.getText());
                    Calendar userInput = new GregorianCalendar(getYear, indexOfMonth, getDay);
                    userInput.setLenient(false);

                    String dayOfWeek = DAY_OF_WEEK_STRING[userInput.get(Calendar.DAY_OF_WEEK) - 1];
                    showDay.setText("The day of the week is " + dayOfWeek);

                } catch (NumberFormatException e) {
                    if(year.getText().isEmpty()) {
                        showDay.setText("Please enter an year.");
                    }
                    else {
                        showDay.setText("Please enter year in number format");
                    }

                } catch (IllegalArgumentException e) {
                    showDay.setText("The day doesn't exist");
                }

            }
        });

        Scene scene = new Scene(pane, 700, 70);
        primaryStage.setTitle("Day of Week");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
