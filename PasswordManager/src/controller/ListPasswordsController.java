package controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.impl.PasswordDaoH2;
import model.entity.PasswordEntry;
import util.SceneManager;

public class ListPasswordsController {

	@FXML
	private TableView<PasswordEntry> passwordTable;
	@FXML
	private TableColumn<PasswordEntry, String> serviceCol;
	@FXML
	private TableColumn<PasswordEntry, String> usernameCol;
	@FXML
	private TableColumn<PasswordEntry, String> passwordCol;

	private final PasswordDaoH2 dao = new PasswordDaoH2();

	private String userId;

	public void setUserId(String id) {
		this.userId = id;
		loadPasswords();
	}

	private void loadPasswords() {
		List<PasswordEntry> entries;
		entries = dao.findAllByUserId(userId);
		ObservableList<PasswordEntry> data = FXCollections.observableArrayList(entries);

		serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("passwordEncrypted"));

		passwordTable.setItems(data);
	}

	@FXML
	private void handleBack() {
		DashboardController controller = SceneManager.switchSceneWithController("/view/Dashboard.fxml");
	    controller.setUserId(userId);
	}
}
