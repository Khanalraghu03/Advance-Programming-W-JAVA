import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Class: DayOfTheWeek.java
 * @version: 1.0
 * Author: Hasan Mohammed
 * Date: Feb 7, 2019
 * midterm.Course: ITEC3150
 * Purpose:  This class creates an application that accepts 3 inputs, 2 from comboBoxes (month and date) and 1 from a textfield (year)
 * then displays the day of the week on that date.
 */

public class DayOfWeek extends Application
{
    private ObservableList<String> choicesMonths;
    private ComboBox<String> comboBoxMonths;

    private ObservableList<Integer> choicesDays;
    private ComboBox<Integer> comboBoxDays;

    private TextField yearTextField;

    private final String[] months = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    private final String[] dayList = {"Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday" };

    private int monthInt;
    private int dayOfTheMonthInt;

    private Label resultMessage;
    private Label errorMessage;

    public void start(Stage stage) throws Exception
    {
        //create grid
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);

        //create month box
        grid.add(new Label("Month:"), 0, 0);
        choicesMonths = FXCollections.observableArrayList(months);
        comboBoxMonths = new ComboBox<String>(choicesMonths);
        grid.add(comboBoxMonths, 1, 0);

        //create and disable day box
        grid.add(new Label("Day: "), 2, 0);
        choicesDays = FXCollections.observableArrayList(
                1,2,3,4,5,6,7,8,9,10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26,27,28,29,30,31);
        comboBoxDays = new ComboBox<Integer>();
        grid.add(comboBoxDays, 3, 0);
        comboBoxDays.setDisable(true);

        // create and disable year textfield
        grid.add(new Label("Year: "), 4, 0);
        yearTextField = new TextField();
        grid.add(yearTextField, 5, 0);
        yearTextField.setDisable(true);

        //create blank result label
        resultMessage = new Label();
        resultMessage.setFont(new Font(20));
        grid.add(resultMessage, 0, 6, 6, 1);

        //create blank error label
        errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        grid.add(errorMessage, 5, 1);

        //events
        comboBoxMonthsAction();
        comboBoxDaysAction();
        textFieldYearAction();

        Scene scene = new Scene(grid, 500, 100);

        stage.setScene(scene);
        stage.setTitle("Day Of The Week");
        stage.show();
    }

    /**
     *
     * Method: comboBoxMonthsAction
     * Params:
     * Return: void
     * This methods assigns monthInt and list of options for comboBoxDays based on the value of the comboBoxMonth
     * also enables comboBoxDays
     */
    public void comboBoxMonthsAction()
    {
        comboBoxMonths.setOnAction(event ->
        {
            monthInt = comboBoxMonths.getSelectionModel().getSelectedIndex();
            GregorianCalendar gc = new GregorianCalendar(2016,monthInt, 1); //any year that is a leap year to get 29 days for feb
            gc.setLenient(false);
            int maxNoDays = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            comboBoxDays.getItems().setAll(choicesDays.subList(0, maxNoDays));
            //enable combobox
            comboBoxDays.setDisable(false);
            comboBoxDays.setValue(1);

        });
    }

    /**
     *
     * Method: comboBoxDaysAction
     * Params:
     * Return: void
     * This method assigns the value for dayOfTheMonth and enables the yearTextField
     */
    public void comboBoxDaysAction()
    {
        comboBoxDays.setOnAction(event ->
        {
            dayOfTheMonthInt = comboBoxDays.getValue();
            //System.out.println(dayOfTheMonthInt);
            yearTextField.setDisable(false);
        });
    }

    /**
     *
     * Method: textFieldYearAction
     * Params:
     * Return: void
     * This method clears an error message if previously triggered.
     * then creates a Calendar based on the values from yearTextField, monthInt and dayOfTheMonthInt
     * if a NUmberFormatException is thrown from the yearTextField, then it displays an error message
     */
    public void textFieldYearAction()
    {
        yearTextField.setOnAction(event ->
        {
            //System.out.println(yearTextField.getText());
            //clear error message
            errorMessage.setText("");

            //attempt to create date
            try
            {
                Calendar date = new GregorianCalendar(Integer.parseInt(yearTextField.getText()), monthInt, dayOfTheMonthInt);
                int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                resultMessage.setText("The day of the week is " + dayList[dayOfWeekInt - 1] );
            }
            catch (NumberFormatException nfe)
            {
                resultMessage.setText("");
                errorMessage.setText("Please enter valid year!");
                System.out.println("NumberFormatException has been thrown.");
            }
        });
    }

    /**
     * Method: main
     * Params:
     * Return: void
     */
    public static void main(String[] args)
    {
        Application.launch(args);

    }

}

