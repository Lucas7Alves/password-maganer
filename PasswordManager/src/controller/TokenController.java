package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AuthenticatorService;
import util.SceneManager;

public class TokenController {

	@FXML
	private TextField tokenField;

	private final AuthenticatorService authService = new AuthenticatorService();
	private String userEmail;

	public void setUserEmail(String email) {
		this.userEmail = email;
	}

	@FXML
	private void handleVerificar() {
		String token = tokenField.getText();
		if (authService.validateToken(userEmail, token)) {
			DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
			controller.setUserEmail(userEmail);
		} else {
			showAlert("Token inválido", "O token inserido é inválido ou expirou.");
		}
	}

	private void showAlert(String titulo, String mensagem) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
