package serveur;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application {

	PrintWriter pr;
	ObservableList<String> listObservable;
	public static void main(String[] args) {
		
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Client Chat");

		BorderPane borderpane = new BorderPane();
		Scene scene = new Scene(borderpane, 800, 600);
		VBox box2 = new VBox();
		box2.setSpacing(10);
		box2.setPadding(new Insets(10));

		// listView au centre de la page
		listObservable = FXCollections.observableArrayList();
		ListView<String> listView = new ListView<String>(listObservable);
		borderpane.setCenter(box2);
		box2.getChildren().add(listView);
		// Pou envoyer un message
		Label labelMessage = new Label("Message : ");
		TextField textMessage = new TextField();
		textMessage.setPrefSize(400, 35);
		Button btnMsg = new Button("Envoyer");
		// hbox 3
		HBox box3 = new HBox();
		box3.setSpacing(10);
		box3.setPadding(new Insets(10));
		box3.getChildren().addAll(labelMessage, textMessage, btnMsg);
		//
		borderpane.setBottom(box3);

		//
		primaryStage.setScene(scene);
		primaryStage.show();
		
		btnMsg.setOnAction((event) -> {
			seConnecter();

			String message = textMessage.getText();
			pr.println(message);
		});
	}
	public  void seConnecter() {
		try {
			Socket s = new Socket("localhost", 2547);

			// data out
			pr = new PrintWriter(s.getOutputStream(), true);
			// data in
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
				while (true) {
					try {
						String response = br.readLine();
						Platform.runLater(() -> {
							listObservable.add(response);
						});
					}

					catch (IOException e) {
						e.printStackTrace();
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
