package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.PasswordLeakService;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

public class LeakCheckController {

    @FXML private TextField passwordInput;
    @FXML private Label resultLabel;

    private final PasswordLeakService leakService = new PasswordLeakService();
    private final UserSession session = UserSession.getInstance();

    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    @FXML
    private void handleCheck() {
        try {
            String password = passwordInput.getText();
            SecurityUtils.validateRequiredFields(password);
            
            boolean vazou = leakService.checkLeakPassword(password);
            resultLabel.setText(vazou ? "⚠️ Senha vazada!" : "✅ Senha segura.");
        } catch (IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erro", "Falha ao verificar senha: " + e.getMessage(), Alert.AlertType.ERROR);
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