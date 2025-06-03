package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.PasswordGeneratorService;
import util.SceneManager;
import util.UserSession;

/**
 * Controlador responsável pela geração de senhas seguras.
 */
public class GeneratePasswordController {

    @FXML private TextField generatedPassword;
    private final PasswordGeneratorService generatorService = new PasswordGeneratorService();
    private final UserSession session = UserSession.getInstance();

    /**
     * Inicializa o controlador e valida a sessão do usuário.
     */
    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    /**
     * Gera uma senha segura e exibe no campo de texto.
     */
    @FXML
    private void handleGenerate() {
        try {
            String password = generatorService.generate(12);
            generatedPassword.setText(password);
        } catch (Exception e) {
            showAlert("Erro", "Falha ao gerar senha: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Retorna à tela de dashboard.
     */
    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    /**
     * Exibe um alerta genérico na interface.
     *
     * @param title   Título do alerta.
     * @param message Mensagem de erro.
     * @param type    Tipo do alerta.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
