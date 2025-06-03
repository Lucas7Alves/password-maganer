package controller;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.impl.UserDaoImpl;
import model.entity.User;
import util.SceneManager;
import util.SecurityUtils;

/**
 * Controlador responsável pelo cadastro de novos usuários.
 */
public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;

    private final UserDaoImpl userDao = new UserDaoImpl();

    /**
     * Lida com a ação de cadastro do usuário.
     * Realiza validações e envia os dados ao banco.
     */
    @FXML
    private void handleCadastro() {
        try {
            String nome = SecurityUtils.sanitizeInput(nomeField.getText());
            String email = SecurityUtils.sanitizeInput(emailField.getText());
            String senha = senhaField.getText();

            SecurityUtils.validateRequiredFields(nome, email, senha);
            SecurityUtils.validateEmail(email);
            SecurityUtils.validateName(nome);
            SecurityUtils.validatePassword(senha);

            User user = new User(nome, senha, email);
            userDao.registerUser(user);
            showAlert("Sucesso", "Usuário cadastrado com sucesso.", Alert.AlertType.INFORMATION);
            
        } catch (IllegalArgumentException e) {
            showAlert("Erro de Validação", e.getMessage(), Alert.AlertType.ERROR);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                showAlert("Falha", "E-mail já cadastrado.", Alert.AlertType.ERROR);
            } else {
                showAlert("Erro no Banco de Dados", "Erro ao cadastrar usuário.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    /**
     * Volta para a tela de login.
     */
    @FXML
    private void handleVoltar() {
        SceneManager.switchScene("/view/Login.fxml");
    }

    /**
     * Exibe um alerta genérico na interface.
     *
     * @param titulo Título do alerta.
     * @param mensagem Mensagem a ser exibida.
     * @param tipo Tipo do alerta (INFORMATION, ERROR, etc).
     */
    private void showAlert(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}