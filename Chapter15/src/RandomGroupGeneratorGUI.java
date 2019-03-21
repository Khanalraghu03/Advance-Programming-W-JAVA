/**Class: RandomGroupGeneratorGUI
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 5, 2019
 *
 *
 * This class is a duplicate of RandomGroupGenerator.java I previously did, the only difference is
 *  this class prints everything on the Graphic User Interface instead of the command prompt.
 *
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;

public class RandomGroupGeneratorGUI extends Application {
    private int numOfGroups;
    private String studentName;
    private ArrayList<String> students;
    private Label numOfGroupsLabel;
    private TextField textField;
    private Button btnOK;
    private TextField textField2;
    private Button addMoreName;
    private Button done;
    private Label label2;
    private Label label3;
    private Label label4;
    private ArrayList<ArrayList<String>> classroom;
    private ArrayList<String> groupOfStudents;
    private int groupNumber;
    private Button showNamesInGroup;
    private int i,num,j;
    private String getGroupnums;
    private String getStudentName;

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(11,12,13,14));
        pane.setHgap(5);
        pane.setVgap(5);

        numOfGroupsLabel = new Label("Enter the number of groups: ");
        textField = new TextField();
        btnOK = new Button("OK");
        pane.add(numOfGroupsLabel, 0,0);
        pane.add(textField, 1, 0);
        pane.add(btnOK, 2 ,0);

        students = new ArrayList();

        label2 = new Label("Enter the midterm.Student Name: ");
        label3 = new Label();
        textField2 = new TextField();
        addMoreName = new Button("Add");
        done = new Button("Done");

        addMoreName.setOnAction(e -> {
            studentName = textField2.getText();
            students.add(studentName);
            pane.setHgap(5);
            label3.setText("Successfully added " + studentName + " to the list.");
            pane.setHgap(5);

            textField2.clear();
        });

        Collections.shuffle(students);

        classroom = new ArrayList<>();

        done.setOnAction(e -> {
            textField2.setVisible(false);

            for(i = 0; i< numOfGroups; i++) {
                groupOfStudents = new ArrayList<>();
                classroom.add(groupOfStudents);
            }

            //Selecting students randomly to the group
            for(i = 0; i < students.size(); i++) {
                groupNumber =  i % numOfGroups;
                //Inside the class room, get the group number: add the shuffled students in a group
                //one by one, until there are no students left
                classroom.get(groupNumber).add(students.get(i));
            }

            int row = 4;
            for(i = 0; i < numOfGroups; i++) {
                num = i + 1;
                getGroupnums = "Group #" + num + ":";
                Label groupLabel = new Label();
                groupLabel.setText(getGroupnums); //label

                pane.add(groupLabel, 0, row++);
                for(j = 0; j < classroom.get(i).size(); j++) {
                    Label studentLabel = new Label();
                    studentName = classroom.get(i).get(j);
                    studentLabel.setText(studentName);
                    pane.add(studentLabel, 0,row++);

                }
            }
        });


        pane.add(label2, 0,1);
        pane.add(textField2, 1,1);
        pane.add(addMoreName, 2,1);pane.add(done,3,1);
        pane.add(label3, 0,2);

        btnOK.setOnAction(e -> {
            String s = textField.getText();
            numOfGroups = Integer.parseInt(s);
            textField.setVisible(false);
//            primaryStage.setScene(scene2);
            label4 = new Label("\nThe number of group is: " + numOfGroups);
            pane.add(label4, 0,3);


        });

        Scene scene = new Scene(pane, 500,800);
        primaryStage.setTitle("Random Group Generator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
