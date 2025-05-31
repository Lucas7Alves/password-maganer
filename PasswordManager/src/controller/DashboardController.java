package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import model.dao.impl.UserDaoImpl;
import util.SceneManager;

public class DashboardController {

    private UserDaoImpl userDao = new UserDaoImpl();

    private String userEmail;
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        try {
			userId = userDao.getUserIdByEmail(userEmail);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	private String userId;
	public void setUserId(String id) {
		this.userId = id;
	}

    @FXML
    private void handleCadastroSenha() {
        RegisterPasswordController controller = SceneManager.switchSceneWithController("/view/RegisterPassword.fxml");
        controller.setUserId(userId);
    }

    @FXML
    private void handleListarSenhas() {
        ListPasswordsController controller = SceneManager.switchSceneWithController("/view/ListarPasswordsView.fxml");
        controller.setUserId(userId);
    }

    @FXML
    private void handleExcluirSenha() {
        DeletePasswordController controller = SceneManager.switchSceneWithController("/view/DeletePasswordView.fxml");
        controller.setUserId(userId);
    }

    @FXML
    private void handleGerarSenhaSegura() {
        SceneManager.switchScene("/view/GeneratePasswordView.fxml");
        
    }

    @FXML
    private void handleVerificarSenha() {
    	SceneManager.switchScene("/view/LeakCheckView.fxml");
    }

    @FXML
    private void handleLogout() {
        SceneManager.switchScene("/view/Login.fxml");
    }
}
