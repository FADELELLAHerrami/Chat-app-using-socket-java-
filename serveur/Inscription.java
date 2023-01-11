package serveur;

import DAO.DaoImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class Inscription extends Application {
	private static boolean started = false;

	public static void main(String[] args) {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Création de la fenêtre principale et de la scène
		primaryStage.setTitle("Inscription Page");
		GridPane grid = new GridPane();
		Scene scene1 = new Scene(grid, 800, 600);
		grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Label inscription = new Label("Page d'inscription");
		inscription.setFont(new Font(40));
		inscription.setUnderline(true);
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

		Button btnLogin = new Button("S'inscrire");
		comboBox.setValue("Cuisinier");

		Label password = new Label("Saisir votre password");
		password.setFont(new Font(20));
		PasswordField Tpassword = new PasswordField();

		Hyperlink linkCompte = new Hyperlink("Si vous avez déja un compte , cliquez ici pour se connecter");
		VBox b = new VBox();
		b.setSpacing(10);
		b.setPadding(new Insets(10));
		b.getChildren().addAll(inscription, nom, Tnom, prenom, Tprenom, fonction, comboBox, password, Tpassword,
				btnLogin, linkCompte);
		grid.getChildren().add(b);
		primaryStage.setScene(scene1);
		primaryStage.show();
		// link action
		linkCompte.setOnAction(e -> {
			primaryStage.close();
			seConnecter c = new seConnecter();
			try {
				c.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		btnLogin.setOnAction((event) -> {
			String tnom = Tnom.getText();
			String tprenom = Tprenom.getText();
			String tpassword = Tpassword.getText();
			String tfonction = comboBox.getValue().toString();
			DaoImpl daoImpl = new DaoImpl();
			System.out.println(tpassword);
			User user = new User(tnom, tprenom, tpassword, tfonction);
			daoImpl.save(user);
			primaryStage.close();
			seConnecter c = new seConnecter();
			try {
				c.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

}
