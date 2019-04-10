import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**Class: TVCharacterTable
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class grabs names, date, tv shows from a text file and displays it in GUI table.
 *
 */
public class TVCharacterTable extends Application {
    private TableView table = new TableView();
    private ObservableList<TVCharacter> data;

    @Override
    public void start(Stage primaryStage) throws Exception {
        data = FXCollections.observableArrayList(
                getTVCharacters()
        );

        //Adds all the columns to the table
        setUpTableColumn(table);

        //Adds all the contents to the table
        table.setItems(data);

        ScrollPane sp = new ScrollPane();
        sp.setContent(table);

        Scene scene = new Scene(sp, 400, 300);
        primaryStage.setTitle("TV Characters");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Get every character's attributes and store it as ArrayList of TV Characters and return it
    public ArrayList<TVCharacter> getTVCharacters() {
        File file = new File("./Chapter16/src/TVCharacter.txt");
        Scanner output = null;
        String line = null;
        String[] lineContents;
        String firstName = null;
        String lastName = null;
        String date = null;
        String tvShow = null;

        ArrayList<TVCharacter> tvChrs= new ArrayList<TVCharacter>();
        try {
            output = new Scanner(file);
            while(output.hasNextLine()) {
                line = output.nextLine();
                if(line.contains(", ")) {
                    lineContents = line.split(", ");
                    firstName = lineContents[0];
                    lastName = lineContents[1];
                    date = lineContents[2];
                    tvShow = lineContents[3];
                    TVCharacter tvCharacter = new TVCharacter(firstName, lastName, date, tvShow);
                    int ages = tvCharacter.getAges();
                    tvChrs.add(tvCharacter);

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tvChrs;
    }


    //Adds the Columns of the table
    public void setUpTableColumn(TableView table) {
        TableColumn<TVCharacter, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory("firstname"));

        TableColumn<TVCharacter, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastname"));

        TableColumn<TVCharacter, String> dOBCol = new TableColumn<>("Date of Birth");
        dOBCol.setCellValueFactory(
                new PropertyValueFactory<>("dob"));

        TableColumn<TVCharacter, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(
                new PropertyValueFactory<>("ages"));

        TableColumn<TVCharacter, String> tVShowCol = new TableColumn<>("TV Show");
        tVShowCol.setCellValueFactory(
                new PropertyValueFactory<>("tvshow"));

        table.getColumns().addAll(firstNameCol, lastNameCol, dOBCol,
                ageCol, tVShowCol);

    }

    public static void main(String[] args) {launch(args);}
}
