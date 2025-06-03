package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.PasswordLeakService;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controlador responsável por verificar se uma senha foi vazada.
 */
public class LeakCheckController {

    @FXML private TextField passwordInput;
    @FXML private Label resultLabel;

    private final PasswordLeakService leakService = new PasswordLeakService();
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
     * Verifica se a senha informada foi vazada.
     */
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

    /**
     * Retorna à tela de dashboard.
     */
    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    /**
     * Exibe um alerta genérico com a mensagem de erro.
     *
     * @param title   Título do alerta.
     * @param message Mensagem a ser exibida.
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
