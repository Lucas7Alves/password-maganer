package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.PasswordGeneratorService;
import util.SceneManager;
import util.UserSession;

public class GeneratePasswordController {

    @FXML private TextField generatedPassword;
    private final PasswordGeneratorService generatorService = new PasswordGeneratorService();
    private final UserSession session = UserSession.getInstance();

    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

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

    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}