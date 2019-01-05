package app.controllers;

import app.utils.MD5Hash;
import app.utils.MsgBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static app.utils.MsgBox.Type.MB_ERROR;

public class ChangePasswordDlgController {
	private static Logger log = LogManager.getLogger(ChangePasswordDlgController.class);

	@FXML
	private TextField tfNewPassword;

	@FXML
	private void initialize() {
		tfNewPassword.requestFocus();
	}

	@FXML
	private void onBtnCancel(ActionEvent actionEvent) {
		closeWnd(actionEvent);
	}

	@FXML
	private void onBtnOk(ActionEvent actionEvent) {
		//todo cript password and transfer to textField
		String paswd = null;

		try {
			paswd = MD5Hash.get(tfNewPassword.getText());
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			MsgBox.show(e.getMessage(), MB_ERROR);
		}

		closeWnd(actionEvent);
	}

	private void closeWnd(ActionEvent actionEvent) {
		//получить источник события
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
}
