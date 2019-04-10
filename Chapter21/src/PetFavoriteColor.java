/**
 * @author: Raghu Khanal
 * @assign #: 6
 * @date: 04/08/2019
 * @course number: ITEC 3150
 * @version 1.0
 * This class is adds all the mapped pets into a table and provides
 *  an interface for user to select various colors that divides the
 *  group of pets. Whatever color the user selects, the table text will
 *  be colored by it.
 */

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PetFavoriteColor extends Application{
    private TableView table = new TableView();
    private ObservableList<Pet> data;
    private Color[] colors = { Color.BLACK, Color.BLUE,
            Color.CYAN, Color.DARK_GRAY, Color.GRAY,
            Color.GREEN,Color.LIGHT_GRAY, Color.MAGENTA,
            Color.ORANGE, Color.PINK, Color.RED,
            Color.WHITE, Color.YELLOW };
    private ArrayList<Pet> pets = getPets();
    private Map<Color, HashSet<Pet>> m = getMap(colors, pets);
    private String textColor = "";

    public static void main(String[] args) {
            launch(args);
    }


    public Map<Color, HashSet<Pet>> getMap(Color[] colors, ArrayList<Pet> pets) {
        Map<Color, HashSet<Pet>> m = new HashMap<Color, HashSet<Pet>>();

        //for a key in keys: put a pet(value) into the set
        for(Color color: colors) {
            m.put(color, new HashSet<Pet>());
        }

        for(Pet p: pets) {
            Color favorite = colors[p.hashCode()]; //fav color
            m.get(favorite).add(p);
        }
        return m;
    }


    public ArrayList<Pet> getPets() {
        File file = new File("./Chapter21/src/Pets.txt");

        Scanner readFile;
        String lineVal[] = null;
        ArrayList<Pet> pets = new ArrayList<>();
        try {
            readFile = new Scanner(file);
            while (readFile.hasNextLine()) {
                lineVal = (readFile.nextLine()).split(" ");
                String catORdog = lineVal[0];
                String name = lineVal[1];
                String breed = lineVal[2];
                String color = lineVal[3];
                int legs = Integer.parseInt(lineVal[4]);
                int weight = Integer.parseInt(lineVal[5]);
                int numOfToys = Integer.parseInt(lineVal[6]);
                String type = lineVal[7];

                if(catORdog.equalsIgnoreCase("Cat")) {
                    if(type.equalsIgnoreCase("SCOOPABLE")){
                        Cat cat = new Cat(name, breed,color, Litter.SCOOPABLE, legs, weight,numOfToys);
                        pets.add(cat);
                    } else if(type.equalsIgnoreCase("CRYSTALS")){
                        Cat cat = new Cat(name, breed,color, Litter.CRYSTALS, legs, weight,numOfToys);
                        pets.add(cat);
                    }else if(type.equalsIgnoreCase("REGULAR")){
                        Cat cat = new Cat(name, breed,color, Litter.REGULAR, legs, weight,numOfToys);
                        pets.add(cat);
                    } else {
                        Cat cat = new Cat(name, breed,color, Litter.NONE, legs, weight,numOfToys);
                        pets.add(cat);
                    }

                }
                if(catORdog.equalsIgnoreCase("Dog")) {
                    if(type.equalsIgnoreCase("BOBBED")){
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.BOBBED);
                        pets.add(dog);
                    }else if(type.equalsIgnoreCase("RING")){
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.RING);
                        pets.add(dog);
                    }else if(type.equalsIgnoreCase("DOCKED")){
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.DOCKED);
                        pets.add(dog);
                    }else if(type.equalsIgnoreCase("OTTER")){
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.OTTER);
                        pets.add(dog);
                    }else if(type.equalsIgnoreCase("WHIP")){
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.WHIP);
                        pets.add(dog);
                    } else {
                        Dog dog = new Dog(name, breed,color, legs, weight,numOfToys,true,"", TailType.SICKLE);
                        pets.add(dog);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return pets;
    }

    public void setUpTableColumn(TableView table) {
        TableColumn<Pet, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Pet, String> breedCol = new TableColumn<>("Breed");
        breedCol.setCellValueFactory(
                new PropertyValueFactory<>("breed"));

        TableColumn<Pet, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(
                new PropertyValueFactory<>("color"));

        TableColumn<Pet, Integer> legsCol = new TableColumn<>("Legs");
        legsCol.setCellValueFactory(
                new PropertyValueFactory<>("legs"));

        TableColumn<Pet, String> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(
                new PropertyValueFactory<>("weight"));
        TableColumn<Pet, String> notCol = new TableColumn<>("# of Toys");
        notCol.setCellValueFactory(
                new PropertyValueFactory<>("numberOfToys"));

        TableColumn<Pet, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(param -> {
            String val = "";
            Pet p = param.getValue();
            if(p instanceof Cat) {
                val =  ((Cat) p).getLitterType().toString();
            } else {
                val =  ((Dog) p).getTail().toString();
            }

            return new ReadOnlyObjectWrapper<>(val);
        });

        table.getColumns().clear();

        table.getColumns().addAll(nameCol, breedCol, colorCol,
                legsCol, weightCol, notCol, typeCol);

        for(int i = 0; i < table.getColumns().size(); i++) {
            ((TableColumn) table.getColumns().get(i)).setStyle("-fx-text-fill: " + textColor);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create buttons and color the buttons
        Button blackBtn = new Button();
        blackBtn.setStyle("-fx-font: 30 arial; -fx-base: #000000;");

        Button blueBtn = new Button();
        blueBtn.setStyle("-fx-font: 30 arial; -fx-base: #0e2bee;");

        Button cyanButton = new Button();
        cyanButton.setStyle("-fx-font: 30 arial; -fx-base: #0ac8ee;");

        Button darkGreyBtn = new Button();
        darkGreyBtn.setStyle("-fx-font: 30 arial; -fx-base: #999999;");

        Button greyBtn = new Button();
        greyBtn.setStyle("-fx-font: 30 arial; -fx-base: #808080;");

        Button greenBtn = new Button();
        greenBtn.setStyle("-fx-font: 30 arial; -fx-base: #259905;");

        Button lightGreyBtn = new Button();
        lightGreyBtn.setStyle("-fx-font: 30 arial; -fx-base: #77807e;");

        Button magentaBtn = new Button();
        magentaBtn.setStyle("-fx-font: 30 arial; -fx-base: #ff00ff;");

        Button orangeBtn = new Button();
        orangeBtn.setStyle("-fx-font: 30 arial; -fx-base: #ffa500;");

        Button pinkBtn = new Button();
        pinkBtn.setStyle("-fx-font: 30 arial; -fx-base: #ff69b4;");

        Button redBtn = new Button();
        redBtn.setStyle("-fx-font: 30 arial; -fx-base: #ee2211;");

        Button whiteBtn = new Button();
        whiteBtn.setStyle("-fx-font: 30 arial; -fx-base: #e9eeec;");

        Button yellowBtn = new Button();
        yellowBtn.setStyle("-fx-font: 30 arial; -fx-base: #ffff00;");


        //Create a pane to add the buttons
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setVgap(10);
        pane.setHgap(10);

        //Add the buttons to the pane
        pane.add(blackBtn,0,0);
        pane.add(blueBtn,1,0);
        pane.add(cyanButton,2,0);
        pane.add(darkGreyBtn,0,1);
        pane.add(greyBtn,1,1);
        pane.add(greenBtn,2,1);
        pane.add(lightGreyBtn,0,2);
        pane.add(magentaBtn,1,2);
        pane.add(orangeBtn,2,2);
        pane.add(pinkBtn,0,3);
        pane.add(redBtn,1,3);
        pane.add(whiteBtn,2,3);
        pane.add(yellowBtn,1,4);

        //Show the window w/ buttons
        Scene scene1 = new Scene(pane);
        Stage stage1 = new Stage();
        stage1.setTitle("Buttons");
        stage1.setScene(scene1);
        stage1.show();


        //Initial Table
        data = FXCollections.observableArrayList(m.get(Color.BLACK));
        textColor = "#000000";
        setUpTableColumn(table);
        table.setItems(data);


        //Provide functionality for the buttons:
        blackBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.BLACK));
            textColor = "#000000";
            setUpTableColumn(table);
            table.setItems(data);
        });

        blueBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.BLUE));
            textColor = "#0e2bee";
            setUpTableColumn(table);
            table.setItems(data);
        });

        cyanButton.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.CYAN));
            textColor = "#0ac8ee";
            setUpTableColumn(table);
            table.setItems(data);
        });

        darkGreyBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.DARK_GRAY));
            textColor = "#999999";
            setUpTableColumn(table);
            table.setItems(data);
        });

        greyBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.GRAY));
            textColor = "#808080";
            table.setItems(data);
        });

        greenBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.GREEN));
            textColor = "#259905";
            setUpTableColumn(table);
            table.setItems(data);
        });

        lightGreyBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.LIGHT_GRAY));
            textColor = "#77807e";
            table.setItems(data);
        });

        magentaBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.MAGENTA));
            textColor = "#ff00ff";
            setUpTableColumn(table);
            table.setItems(data);
        });

        orangeBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.ORANGE));
            textColor = "#ffa500";
            setUpTableColumn(table);
            table.setItems(data);
        });

        pinkBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.PINK));
            textColor = "#ff69b4";
            setUpTableColumn(table);
            table.setItems(data);
        });

        redBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.RED));
            textColor = "#ee2211";
            setUpTableColumn(table);
            table.setItems(data);
        });

        whiteBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.WHITE));
            textColor = "#000000"; //e9eeec
            setUpTableColumn(table);
            table.setItems(data);
        });

        yellowBtn.setOnAction(event -> {
            data = FXCollections.observableArrayList(m.get(Color.YELLOW));
            textColor = "#ffff00";
            setUpTableColumn(table);
            table.setItems(data);
        });

        //Add table to primary window and show it
        ScrollPane sp = new ScrollPane();
        sp.setContent(table);
        Scene scene = new Scene(sp, 580, 415);
        primaryStage.setTitle("Pet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
