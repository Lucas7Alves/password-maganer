package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.impl.UserDaoImpl;
import model.entity.User;

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;

    private final UserDaoImpl userDao = new UserDaoImpl();

    @FXML
    private void handleCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            showAlert("Erro", "Todos os campos são obrigatórios.");
            return;
        }

        User user = new User(nome, email, senha);
        try {
			userDao.registerUser(user);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        showAlert("Sucesso", "Usuário cadastrado com sucesso.");
    }

    @FXML
    private void handleVoltar() {
        // Voltar para tela de login
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
