package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.impl.PasswordDaoH2;
import model.entity.PasswordEntry;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controller responsável por cadastrar novas senhas para o usuário.
 */
public class RegisterPasswordController {

    @FXML private TextField serviceNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label feedbackLabel;

    private final PasswordDaoH2 passwordDaoH2 = new PasswordDaoH2();
    private final UserSession session = UserSession.getInstance();

    /**
     * Verifica se o usuário está logado ao inicializar a tela.
     */
    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    /**
     * Registra uma nova senha para o usuário logado.
     */
    @FXML
    private void handleRegister() {
        try {
            String serviceName = SecurityUtils.sanitizeInput(serviceNameField.getText());
            String username = SecurityUtils.sanitizeInput(usernameField.getText());
            String passwordEncrypted = passwordField.getText(); // Senha não deve ser sanitizada

            SecurityUtils.validateRequiredFields(serviceName, username, passwordEncrypted);
            SecurityUtils.validateName(serviceName); // Valida como um nome

            PasswordEntry pe = new PasswordEntry(serviceName, username, passwordEncrypted);
            passwordDaoH2.save(pe, session.getUserId());

            showFeedback("Password registered successfully.", Alert.AlertType.INFORMATION);
            clearFields();
            
        } catch (IllegalArgumentException e) {
            showFeedback(e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showFeedback("Error registering password.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Retorna para o dashboard.
     */
    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    /**
     * Exibe feedback na tela ou em alerta.
     * @param message Mensagem a ser exibida
     * @param type Tipo de mensagem
     */
    private void showFeedback(String message, Alert.AlertType type) {
        if (type == Alert.AlertType.INFORMATION) {
            feedbackLabel.setText(message);
        } else {
            Alert alert = new Alert(type);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }


    /**
     * Limpa os campos do formulário.
     */
    private void clearFields() {
        serviceNameField.clear();
        usernameField.clear();
        passwordField.clear();
    }
}