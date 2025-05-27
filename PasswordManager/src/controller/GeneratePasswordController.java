package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.PasswordGeneratorService;
import util.SceneManager;

public class GeneratePasswordController {

    @FXML
    private TextField generatedPassword;
    private PasswordGeneratorService generatorService = new PasswordGeneratorService();

    @FXML
    private void handleGenerate() {
        String password = generatorService.generate(12);
        generatedPassword.setText(password);
    }

    @FXML
    private void handleBack() {
        SceneManager.switchScene("/view/Dashboard.fxml");
    }
}
