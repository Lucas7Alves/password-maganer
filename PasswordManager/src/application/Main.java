package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.DB;
import util.SceneManager;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		SceneManager.setStage(primaryStage);

		try {

			DB.getConnection();
			DB.createSchema();

			SceneManager.switchScene("/view/Login.fxml");
		} catch (SQLException e) {
			e.printStackTrace();

			showFatalError("Erro de Banco de Dados", "Não foi possível conectar ao banco de dados.");
			Platform.exit();
		}
	}

	private void showFatalError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
