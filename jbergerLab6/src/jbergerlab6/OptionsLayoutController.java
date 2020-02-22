package jbergerlab6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;

public class OptionsLayoutController extends Dialog<ButtonType> implements Initializable {
	@FXML
	private CheckBox Italics;
	@FXML
	private CheckBox Bold;
	@FXML
	private RadioButton ShowDate;
	@FXML
	private ToggleGroup DateOrString;
	@FXML
	private RadioButton ShowString;
	@FXML
	private TextField InputString;
	@FXML
	private TextField Height;
	
	private ButtonType mOk;

	// Event Listener on RadioButton[#ShowDate].onAction
	@FXML
	public void OnDateTime(ActionEvent event) {
		if (ShowDate.isSelected()) {
			InputString.setDisable(true);
		} else {
			InputString.setDisable(false);
		}
	}
	
	public OptionsLayoutController() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("OptionsLayout.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		getDialogPane().setContent(root);

		mOk = new ButtonType("Ok", ButtonData.OK_DONE);
		getDialogPane().addEventFilter(ActionEvent.ACTION, actionEvent -> onOk(actionEvent));
		ButtonType Cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(mOk, Cancel);
		InputString.setDisable(true);
	}

	private Object onOk(ActionEvent actionEvent) {
		try {
			Integer height = Integer.parseInt(Height.getText());
			if (height < 8 || height > 40) {
				actionEvent.consume();
				Alert alert = new Alert(AlertType.ERROR, "Enter a valid font size from 8 to 40.", ButtonType.OK);
				alert.showAndWait();
				Height.requestFocus();
				Height.selectAll();
			} else {
				ApplicationSettings settings = ApplicationSettings.getInstance();
				settings.setFontSize(height);
				settings.mBoldSelected = Bold.isSelected();
				settings.mItalicsSelected = Italics.isSelected();
				settings.mDateTimeSelected = ShowDate.isSelected();
				settings.mUserString = InputString.getText();
			}
		} catch (Exception e) {
			System.err.append(e.getMessage());
		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ApplicationSettings settings = ApplicationSettings.getInstance();
		Italics.setSelected(settings.mItalicsSelected);
		Bold.setSelected(settings.mBoldSelected);
		ShowDate.setSelected(settings.mDateTimeSelected);
		InputString.setText(settings.mUserString);
		Height.setText(String.valueOf(settings.getFontSize()));		
		
	}
	
	
}
