package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.dao.UserDao;
import model.dao.impl.UserDaoImpl;
import service.AuthenticatorService;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controller responsável por validar o token de autenticação enviado ao e-mail
 * do usuário.
 */
public class TokenController {

	@FXML
	private TextField tokenField;

	private final AuthenticatorService authService = new AuthenticatorService();
	private final UserDao userDao = new UserDaoImpl();
	private final UserSession session = UserSession.getInstance();

	/**
	 * Define o e-mail do usuário na sessão antes da verificação do token.
	 * 
	 * @param email E-mail do usuário
	 */
	public void setUserEmail(String email) {
		SecurityUtils.validateEmail(email);
		session.initSession(null, email); // Armazena email temporariamente
	}

	/**
	 * Valida o token inserido pelo usuário e inicia a sessão completa.
	 */
	@FXML
	private void handleVerificar() {
		try {
			String token = SecurityUtils.sanitizeToken(tokenField.getText());
			SecurityUtils.validateToken(token);

			String userEmail = session.getUserEmail();
			String userId = userDao.getUserIdByEmail(userEmail);

			if (userId == null || Integer.valueOf(userId) <= 0) {
				throw new IllegalStateException("Usuário não encontrado");
			}

			if (authService.validateToken(userId, token)) {
				// Inicia sessão completa com userId e email
				session.initSession(userId, userEmail);
				SceneManager.switchScene("/view/Dashboard.fxml");
			} else {
				showAlert("Token inválido", "O token inserido é inválido ou expirou.", Alert.AlertType.ERROR);
			}
		} catch (IllegalArgumentException | IllegalStateException e) {
			showAlert("Erro de Validação", e.getMessage(), Alert.AlertType.ERROR);
		} catch (Exception e) {
			showAlert("Erro", "Ocorreu um erro inesperado: " + e.getMessage(), Alert.AlertType.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Exibe um alerta ao usuário.
	 * 
	 * @param titulo   Título do alerta
	 * @param mensagem Mensagem a ser exibida
	 * @param tipo     Tipo do alerta
	 */
	private void showAlert(String titulo, String mensagem, Alert.AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}