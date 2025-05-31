package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.dao.UserDao;
import model.dao.impl.UserDaoImpl;
import service.AuthenticatorService;
import util.SceneManager;

public class TokenController {

	@FXML
	private TextField tokenField;

	private final AuthenticatorService authService = new AuthenticatorService();
	private final UserDao userDao = new UserDaoImpl();
	private String userEmail;

	public void setUserEmail(String email) {
		this.userEmail = email;
	}

	@FXML
	private void handleVerificar() {
		String token = tokenField.getText();
		try {
			if (authService.validateToken(userDao.getUserIdByEmail(userEmail), token)) {
				DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
				controller.setUserEmail(userEmail);
			} else {
				showAlert("Token inválido", "O token inserido é inválido ou expirou.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String titulo, String mensagem) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
