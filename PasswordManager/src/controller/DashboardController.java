package controller;

import javafx.fxml.FXML;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

public class DashboardController {

    private final UserSession session = UserSession.getInstance();

    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    @FXML
    private void handleCadastroSenha() {
        validateSession();
        SceneManager.switchScene("/view/RegisterPassword.fxml");
    }

    @FXML
    private void handleListarSenhas() {
        validateSession();
        SceneManager.switchScene("/view/ListarPasswordsView.fxml");
    }

    @FXML
    private void handleExcluirSenha() {
        validateSession();
        SceneManager.switchScene("/view/DeletePasswordView.fxml");
    }

    @FXML
    private void handleGerarSenhaSegura() {
        validateSession();
        SceneManager.switchScene("/view/GeneratePasswordView.fxml");
    }

    @FXML
    private void handleVerificarSenha() {
        validateSession();
        SceneManager.switchScene("/view/LeakCheckView.fxml");
    }

    @FXML
    private void handleLogout() {
        session.clearSession();
        SceneManager.switchScene("/view/Login.fxml");
    }

    private void validateSession() {
        try {
            SecurityUtils.validateRequiredFields(session.getUserId(), session.getUserEmail());
            SecurityUtils.validateEmail(session.getUserEmail());
        } catch (IllegalArgumentException e) {
            handleLogout();
            throw new IllegalStateException("Sessão inválida: " + e.getMessage());
        }
    }
}