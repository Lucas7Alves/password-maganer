package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utilitário responsável por gerenciar a troca de cenas (telas) na aplicação JavaFX.
 */
public class SceneManager {

    private static Stage mainStage;

    /**
     * Define o palco principal da aplicação.
     *
     * @param stage O palco principal
     */
    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Altera a cena atual para a definida pelo arquivo FXML informado.
     *
     * @param fxmlPath Caminho do arquivo FXML
     */
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Altera a cena atual e retorna o controller associado ao FXML.
     *
     * @param fxmlPath Caminho do arquivo FXML
     * @param <T> Tipo do controller
     * @return Controller do FXML carregado
     */
    public static <T> T switchSceneWithController(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root));
            mainStage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
