package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.impl.UserDaoImpl;
import service.AuthenticatorService;
import service.EmailService;
import util.SceneManager;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UserDaoImpl userDao = new UserDaoImpl();
    private final AuthenticatorService authService = new AuthenticatorService();
    private final EmailService emailService = new EmailService();
    
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String senha = passwordField.getText();

        try {        	
        	//if (userDao.validateUser(email, senha)) {
        	if (true) {
        		//String token = authService.generateToken(email);
        		//emailService.sendTokenEmail(email, token);

        		 DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
                 controller.setUserEmail("lucassalvess0909@gmail.com");
        	} else {
        		System.out.println("erro");
        		showAlert("Login inválido", "E-mail ou senha incorretos.");
        	}
        } catch (Exception e) {
        	System.err.println(e.getMessage());
		}
    }

    @FXML
    private void handleCadastro() {
        SceneManager.switchScene("/view/Cadastro.fxml");
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
