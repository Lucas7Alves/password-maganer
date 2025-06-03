package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.DB;
import util.SceneManager;

/**
 * Classe principal que inicia a aplicação JavaFX e configura a cena inicial.
 */
public class Main extends Application {

    /**
     * Método chamado ao iniciar a aplicação.
     * Configura a cena principal e realiza a conexão com o banco de dados.
     *
     * @param primaryStage Janela principal da aplicação.
     */
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

    /**
     * Exibe um alerta de erro fatal e encerra a aplicação.
     *
     * @param title   Título da mensagem.
     * @param message Mensagem de erro.
     */
    private void showFatalError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Método main que inicia a aplicação.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
