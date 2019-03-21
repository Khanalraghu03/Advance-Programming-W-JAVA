package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


public class HelloWorld extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label label1 = new Label("Name:");
		TextField textField = new TextField ();
		HBox hb = new HBox();
		hb.getChildren().addAll(label1, textField);
		hb.setSpacing(10);
		Button btn = new Button("Say Hello World");
		btn.setOnAction((e) -> System.out.println("Hello World !"));

		StackPane root = new StackPane();
		root.getChildren().add(btn);
		root.getChildren().add(hb);

		Scene scene = new Scene(root, 300, 300);
		primaryStage.setTitle("My First Java FX App");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}