package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dao.impl.PasswordDaoH2;
import util.SceneManager;

public class DeletePasswordController {

    @FXML private TextField serviceField;
    @FXML private Label feedbackLabel;

    private final PasswordDaoH2 dao = new PasswordDaoH2();
    private int userId;

    public void setUserId(int id) {
        this.userId = id;
    }
	

    @FXML
    private void handleDelete() {
			setUserId(userId);
			
        String service = serviceField.getText().trim();
        if (service.isEmpty()) {
            feedbackLabel.setText("Service name required.");
            return;
        }

        boolean deleted = dao.deleteByServiceName(userId, service);
        if (deleted) {
            feedbackLabel.setText("Deleted successfully.");
            serviceField.clear();
        } else {
            feedbackLabel.setText("No entry found.");
        }
    }

    @FXML
    private void handleBack() {
    	DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
    	controller.setUserId(userId);
    }
}
