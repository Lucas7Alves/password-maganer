package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.impl.PasswordDaoH2;
import util.SceneManager;
import util.SecurityUtils;
import util.UserSession;

public class DeletePasswordController {

    @FXML private TextField serviceField;
    @FXML private Label feedbackLabel;

    private final PasswordDaoH2 dao = new PasswordDaoH2();
    private final UserSession session = UserSession.getInstance();

    @FXML
    private void initialize() {
        if (!session.isValid()) {
            SceneManager.switchScene("/view/Login.fxml");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            String service = SecurityUtils.sanitizeInput(serviceField.getText());
            SecurityUtils.validateRequiredFields(service);
            
            boolean deleted = dao.deleteByServiceName(session.getUserId(), service);
            if (deleted) {
                showFeedback("Deleted successfully.", Alert.AlertType.INFORMATION);
                serviceField.clear();
            } else {
                showFeedback("No entry found.", Alert.AlertType.WARNING);
            }
        } catch (IllegalArgumentException e) {
            showFeedback(e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showFeedback("Error deleting entry.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }

    private void showFeedback(String message, Alert.AlertType type) {
        if (type == Alert.AlertType.INFORMATION || type == Alert.AlertType.WARNING) {
            feedbackLabel.setText(message);
        } else {
            Alert alert = new Alert(type);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}