package serveur;

import java.util.Optional;

import DAO.DaoImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.User;

public class seConnecter extends Application {
	Scene scene;
	int nbrConnection = 0;
	DaoImpl daoImpl;
	User user;
	Alert alert;

	public static void main(String[] args) {
		System.out.println("me");
		launch(args);
		System.out.println("me");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Page de connection
		primaryStage.setTitle("Login Page");
		GridPane grid = new GridPane();
		scene = new Scene(grid, 800, 600);

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Label login = new Label("Page Login");
		login.setFont(new Font(40));
		login.setUnderline(true);
		Label nom = new Label("Saisir votre nom");
		nom.setFont(new Font(20));
		TextField Tnom = new TextField();
		Label prenom = new Label("Saisir votre prenom");
		prenom.setFont(new Font(20));
		TextField Tprenom = new TextField();

		Label fonction = new Label("Saisir votre fonction");
		fonction.setFont(new Font(20));

		ObservableList<String> options = FXCollections.observableArrayList("Cuisinier", "Caissier", "Serveuse");

		ComboBox<String> comboBox = new ComboBox<>(options);

		comboBox.setValue("Cuisinier");
		Button btnLogin = new Button("Se connecter");

		Label password = new Label("Saisir votre password");
		password.setFont(new Font(20));
		PasswordField Tpassword = new PasswordField();

		Hyperlink linkCompte = new Hyperlink("Cliquez ici pour créer votre compte");
		VBox b = new VBox();
		b.setSpacing(10);
		b.setPadding(new Insets(10));
		b.getChildren().addAll(login, nom, Tnom, prenom, Tprenom, fonction, comboBox, password, Tpassword, btnLogin,
				linkCompte);
		grid.getChildren().addAll(b);
		primaryStage.setScene(scene);
		primaryStage.show();

//		page d'indcription

		btnLogin.setOnAction((event) -> {
			nbrConnection++;
			String tnom = Tnom.getText();
			String tprenom = Tprenom.getText();
			String tpassword = Tpassword.getText();
			String tfonction = comboBox.getValue().toString();
			DaoImpl daoImpl = new DaoImpl();
			System.out.println(tpassword);
			User user = daoImpl.findUser(tnom, tprenom, tpassword, tfonction);
			if (user == null) {
				Alert alertInformation1 = new Alert(Alert.AlertType.INFORMATION);
				alertInformation1.setTitle("Message Dialog");
				alertInformation1.setHeaderText("Look, a Message Dialog");
				alertInformation1.setContentText("Vos information sont incorectes");

				alertInformation1.showAndWait();

				// condition de nbr de connection

				if (nbrConnection == 3) {
					Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
					alertInformation.setTitle("Message Dialog");
					alertInformation.setHeaderText("Look, a Message Dialog");
					alertInformation.setContentText("I have a great message for you!");
					alertInformation.setOnCloseRequest(event1 -> {
						Platform.exit();
					});
					alertInformation.showAndWait();
				}
			} else {
				primaryStage.close();
				ClientChat clientChat = new ClientChat();
				try {
					clientChat.start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		linkCompte.setOnAction(e -> {
			primaryStage.close();
			Inscription c = new Inscription();
			try {
				c.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
	// se connecter

}
