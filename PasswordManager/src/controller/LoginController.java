package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.TokenDao;
import model.dao.UserDao;
import model.dao.impl.TokenDaoImpl;
import model.dao.impl.UserDaoImpl;
import service.AuthenticatorService;
import service.EmailService;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controller responsável pela autenticação de usuários.
 */
public class LoginController {

	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;

	private final UserDao userDao = new UserDaoImpl();
	private final AuthenticatorService authService = new AuthenticatorService();
	private final EmailService emailService = new EmailService();
	private final TokenDao tokenDao = new TokenDaoImpl();
	private final UserSession session = UserSession.getInstance();

    /**
     * Manipula o evento de login, realizando validações e envio de token.
     */
	@FXML
	private void handleLogin() {
		try {
			String email = SecurityUtils.sanitizeInput(emailField.getText());
			String senha = passwordField.getText(); // Senha não é sanitizada para permitir caracteres especiais

			// Validações de segurança
			SecurityUtils.validateRequiredFields(email, senha);
			SecurityUtils.validateEmail(email);

			if (userDao.validateUser(email, senha)) {
				String token = authService.generateToken(email);
				emailService.sendTokenEmail(email, token);

				String userId = String.valueOf(userDao.getUserIdByEmail(email));
				if (userId == null || userId.equals("0")) {
					throw new IllegalStateException("ID de usuário inválido");
				}

				tokenDao.saveToken(userId, token);
				session.initSession(userId, email);

				SceneManager.switchScene("/view/Token.fxml");
			} else {
				showAlert("Erro", "Credenciais inválidas", Alert.AlertType.ERROR);
			}
		} catch (IllegalArgumentException e) {
			showAlert("Erro de Validação", e.getMessage(), Alert.AlertType.ERROR);
		} catch (Exception e) {
			showAlert("Erro", "Falha no login: " + e.getMessage(), Alert.AlertType.ERROR);
			e.printStackTrace();
		}
	}


    /**
     * Redireciona para a tela de cadastro.
     */
	@FXML
	private void handleCadastro() {
		SceneManager.switchScene("/view/Cadastro.fxml");
	}

    /**
     * Mostra um alerta para o usuário.
     * @param title Título do alerta
     * @param message Mensagem do alerta
     * @param type Tipo do alerta
     */
	private void showAlert(String title, String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}