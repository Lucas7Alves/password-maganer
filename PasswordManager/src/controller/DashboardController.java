package controller;

import javafx.fxml.FXML;

public class DashboardController {
	
	private String userEmail;

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

    @FXML
    private void handleCadastroSenha() {
        // Redirecionar para tela de cadastro de senhas
    }

    @FXML
    private void handleVerificarSenha() {
        // Redirecionar para tela de verificação de vazamento
    }

    @FXML
    private void handleLogout() {
        // Voltar para tela de login
    }
}
