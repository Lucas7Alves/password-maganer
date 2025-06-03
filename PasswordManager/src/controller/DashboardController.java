package controller;

import javafx.fxml.FXML;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controlador da tela de dashboard, que lida com as ações do usuário logado.
 */
public class DashboardController {

    private final UserSession session = UserSession.getInstance();

    /**
     * Inicializa o dashboard e redireciona para o login se a sessão for inválida.
     */
    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    /**
     * Ações de navegação para as telas do sistema.
     * Cada método valida a sessão antes de redirecionar.
     */
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

    /**
     * Finaliza a sessão do usuário e redireciona para o login.
     */
    @FXML
    private void handleLogout() {
        session.clearSession();
        SceneManager.switchScene("/view/Login.fxml");
    }

    /**
     * Valida a sessão atual. Se inválida, encerra a sessão.
     *
     * @throws IllegalStateException se os dados da sessão forem inválidos.
     */
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