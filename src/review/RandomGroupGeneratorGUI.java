package review;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;


public class RandomGroupGeneratorGUI extends Application {
    int numOfGroups;
    String studentName;

    @Override
    public void start(Stage primaryStage) {
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(11,12,13,14));
        pane.setHgap(5);
        pane.setVgap(5);

        Label label1 = new Label("Enter the number of groups: ");
        TextField textField = new TextField();
        Button button = new Button("OK");
        pane.getChildren().addAll(label1,textField, button);


        FlowPane pane2 = new FlowPane();
        pane2.setPadding(new Insets(11,12,13,14));
        pane2.setHgap(5);
        pane2.setVgap(5);
        TextField textField2;
        Button addMoreName;
        Button close;
        Scene scene2 = new Scene(pane2);


        FlowPane pane3 = new FlowPane();
        pane3.setPadding(new Insets(11,12,13,14));
        pane3.setHgap(5);
        pane3.setVgap(5);
        Scene scene3 = new Scene(pane3);


        FlowPane pane4 = new FlowPane();
        pane4.setPadding(new Insets(11,12,13,14));
        pane4.setHgap(5);
        pane4.setVgap(5);
        Scene scene4 = new Scene(pane4);

        ArrayList<String> students = new ArrayList();

        Label label2 = new Label("Enter the Student Name: ");
        Label label7 = new Label();
        textField2 = new TextField();
        addMoreName = new Button("Add");
        close = new Button("Done");


        addMoreName.setOnAction(e -> {
            studentName = textField2.getText();
            students.add(studentName);
            System.out.println(students);
            label7.setText("Successfully added " + studentName + " to the list.");
        });



        close.setOnAction(e -> {
            primaryStage.setScene(scene3);
        });


        pane2.getChildren().addAll(label2,textField2,addMoreName,close,label7);

//
        button.setOnAction(e -> {
            String s = textField.getText();
            numOfGroups = Integer.parseInt(s);
            primaryStage.setScene(scene2);

        });

        Collections.shuffle(students);
        Label label3 = new Label("\nThe number of group is: " + numOfGroups + "\n");
//        Label label4 = new Label("Students shuffled: \n" + students);
//
        ArrayList<ArrayList<String>> classroom = new ArrayList<>();
        Button showNamesInGroup = new Button("Show Groups");

        pane3.getChildren().addAll(label3, showNamesInGroup);

        showNamesInGroup.setOnAction(e -> {
            primaryStage.setScene(scene4);
        });



        for(int i = 0; i< numOfGroups; i++) {
            ArrayList<String> groupOfStudents = new ArrayList<>();
            classroom.add(groupOfStudents);
        }


        int groupNumber;
        //Selecting students randomly to the group
        for(int i = 0; i < students.size(); i++) {
            groupNumber =  i % numOfGroups;
            //Inside the class room, get the group number: add the shuffled students in a group
            //one by one, until there are no students left
            classroom.get(groupNumber).add(students.get(i));
        }


        Label label5 = new Label();
        Label label6 = new Label();
        for(int i = 0; i < numOfGroups; i++) {
            int num = i + 1;
            label5.setText("Group #" + num + ":"); //label
            pane4.getChildren().add(label5);
            for(int j = 0; j < classroom.get(i).size(); j++) {
                label6.setText(classroom.get(i).get(j));
                pane4.getChildren().add(label6);
            }
        }

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Random Group Generator");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
