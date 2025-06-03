package controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.impl.PasswordDaoH2;
import model.entity.PasswordEntry;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

/**
 * Controller responsável por listar as senhas salvas pelo usuário logado.
 */
public class ListPasswordsController {

    @FXML private TableView<PasswordEntry> passwordTable;
    @FXML private TableColumn<PasswordEntry, String> serviceCol;
    @FXML private TableColumn<PasswordEntry, String> usernameCol;
    @FXML private TableColumn<PasswordEntry, String> passwordCol;

    private final PasswordDaoH2 dao = new PasswordDaoH2();
    private final UserSession session = UserSession.getInstance();

    /**
     * Inicializa a tela e carrega as senhas.
     */
    @FXML
    private void initialize() {
        validateSession();
        loadPasswords();
    }

    /**
     * Carrega todas as senhas do usuário logado na tabela.
     */
    private void loadPasswords() {
        try {
            String userId = session.getUserId();
            SecurityUtils.validateRequiredFields(userId);
            
            List<PasswordEntry> entries = dao.findAllByUserId(userId);
            ObservableList<PasswordEntry> data = FXCollections.observableArrayList(entries);

            serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("passwordEncrypted"));

            passwordTable.setItems(data);
        } catch (IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
            SceneManager.switchScene("/view/Dashboard.fxml");
        }
    }

    /**
     * Retorna à tela do Dashboard.
     */
    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    /**
     * Valida se a sessão do usuário está ativa.
     */
    private void validateSession() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
            throw new IllegalStateException("Sessão inválida");
        }
    }

    /**
     * Exibe um alerta na tela.
     * @param title Título do alerta
     * @param message Mensagem exibida
     * @param type Tipo de alerta
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}