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

public class ListPasswordsController {

    @FXML private TableView<PasswordEntry> passwordTable;
    @FXML private TableColumn<PasswordEntry, String> serviceCol;
    @FXML private TableColumn<PasswordEntry, String> usernameCol;
    @FXML private TableColumn<PasswordEntry, String> passwordCol;

    private final PasswordDaoH2 dao = new PasswordDaoH2();
    private final UserSession session = UserSession.getInstance();

    @FXML
    private void initialize() {
        validateSession();
        loadPasswords();
    }

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

    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    private void validateSession() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
            throw new IllegalStateException("Sessão inválida");
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}