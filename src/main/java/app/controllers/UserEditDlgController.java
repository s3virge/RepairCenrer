package app.controllers;

import app.dao.UserDao;
import app.models.User;
import app.models.UserGroup;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserEditDlgController {
	private static Logger log = LogManager.getLogger(UserEditDlgController.class);

	@FXML TextField tfSurname;
	@FXML TextField tfName;
	@FXML TextField tfPatronymic;
	@FXML TextField tfLogin;
	@FXML TextField tfPassword;
	@FXML TextField tfPhoneNumber;
	@FXML TextField tfEmail;

	@FXML
	private ComboBox cbUserGroup;

	@FXML
	private void OnClickBtnEdit(ActionEvent event) {
		log.trace("");

		//todo change data about user in database

		String userGroup = (String) cbUserGroup.getSelectionModel().getSelectedItem();

		User newUser = new User(tfLogin.getText(), tfPassword.getText(), userGroup,
				tfSurname.getText(), tfName.getText(), tfPatronymic.getText(),
				tfEmail.getText(), tfPhoneNumber.getText());

//        log.debug("{}", newUser.toString());

//        UserDao.insert(newUser);

		closeWnd(event);
	}

	@FXML
	private void OnClickBtnCancel(ActionEvent actionEvent) {
		closeWnd(actionEvent);
	}

	private void closeWnd(ActionEvent actionEvent) {
		//получить источник события
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void initialize() {
		log.trace("");

//        tfSurname.requestFocus(); //did not working
		//this trick is working
		Platform.runLater(() -> tfSurname.requestFocus());

		cbUserGroup.getItems().addAll(UserGroup.ADMIN, UserGroup.ACCEPTOR, UserGroup.MANAGER, UserGroup.MASTER);
	}

	public void fillTextfields(User selectedUser) {
		tfSurname.setText(selectedUser.getSurname());
		tfName.setText(selectedUser.getName());
		tfPatronymic.setText(selectedUser.getPatronymic());
		tfLogin.setText(selectedUser.getLogin());
		tfPassword.setText(selectedUser.getPassword());
		tfPhoneNumber.setText(selectedUser.getPhoneNumber());
		tfEmail.setText(selectedUser.getEmail());

		cbUserGroup.getSelectionModel().select(selectedUser.getGroup());
	}
}
