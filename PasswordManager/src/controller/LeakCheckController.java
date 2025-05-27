package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.PasswordLeakService;
import util.SceneManager;

public class LeakCheckController {

	@FXML
	private TextField passwordInput;

	@FXML
	private Label resultLabel;

	private PasswordLeakService leakService = new PasswordLeakService();


	@FXML
	private void handleCheck() {
		String password = passwordInput.getText();
		boolean vazou = leakService.checkLeakPassword(password);
		resultLabel.setText(vazou ? "⚠️ Senha vazada!" : "✅ Senha segura.");
	}

	@FXML
	private void handleBack() {
		SceneManager.switchScene("/view/Dashboard.fxml");
	}
}
