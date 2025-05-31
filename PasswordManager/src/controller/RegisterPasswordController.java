package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.impl.PasswordDaoH2;
import model.dao.impl.UserDaoImpl;
import model.entity.PasswordEntry;
import util.SceneManager;

public class RegisterPasswordController {

	@FXML
	private TextField serviceNameField;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label feedbackLabel;

	private final PasswordDaoH2 passwordDaoH2 = new PasswordDaoH2();

	private String userId;

	public void setUserId(String id) {
		this.userId = id;
	}

	@FXML
	private void handleRegister() {
		String serviceName = serviceNameField.getText().trim();
		String username = usernameField.getText().trim();
		String passwordEncrypted = passwordField.getText();

		if (serviceName.isEmpty() || username.isEmpty() || passwordEncrypted.isEmpty()) {
			feedbackLabel.setText("Please fill in all fields.");
			return;
		}

		PasswordEntry pe = new PasswordEntry(serviceName, username, passwordEncrypted);
		passwordDaoH2.save(pe, String.valueOf(userId));

		if (true) {
			feedbackLabel.setText("PasswordEntry registered successfully.");
			serviceNameField.clear();
			usernameField.clear();
			passwordField.clear();
		}
	}

	@FXML
	private void handleBack() {
		DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
		controller.setUserId(userId);
	}
}
