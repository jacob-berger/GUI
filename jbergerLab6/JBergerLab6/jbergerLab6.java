package jbergerlab6;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class jbergerLab6 extends Application {
    
    Label mStatus;
    BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        Text text = new Text("Hello world!");

        root = new BorderPane();
        root.setCenter(text);
        
        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Jacob Berger Lab 6");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Lab 6, Winter 2020");
        alert.showAndWait();
    }

    public MenuBar buildMenuBar() {
        //build a menu bar
        MenuBar menuBar = new MenuBar();

        //file menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        
        MenuItem optionsMenuItem = new MenuItem("_Options");
        optionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        optionsMenuItem.setOnAction(actionEvent -> onOptionsSelected());
        
        fileMenu.getItems().addAll(quitMenuItem, optionsMenuItem);
        
        //Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        return menuBar;
    }
    
    private Object onOptionsSelected() {
		try {
			OptionsLayoutController controller = new OptionsLayoutController();
			Optional<ButtonType> returnedButton = controller.showAndWait();
			if (returnedButton.isPresent()) {
				ButtonData buttonData = returnedButton.get().getButtonData();
				if (buttonData.compareTo(ButtonData.OK_DONE) == 0) {
					setStatus("OK_DONE");
					
					Text text = new Text();
					
					ApplicationSettings settings = ApplicationSettings.getInstance();
					if (settings.mBoldSelected) {
						if (settings.mItalicsSelected) {
							text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, settings.getFontSize()));
						} else {
							text.setFont(Font.font("Arial", FontWeight.BOLD, settings.getFontSize()));
						}
					} else if (settings.mItalicsSelected) {
						text.setFont(Font.font("Arial", FontPosture.ITALIC, settings.getFontSize()));
					} else {
						text.setFont(Font.font("Arial", settings.getFontSize()));
					}
					if (settings.mDateTimeSelected) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a");
						Date date = new Date();
						text.setText(dateFormat.format(date));
					} else {
						text.setText(settings.mUserString);
					}
					
					root.setCenter(text);
				}
			}
		} catch (Exception e) {
			System.err.append(e.getMessage());
			setStatus("Error occurred");
		}
		return null;
	}

	public void setStatus(String status) {
        mStatus.setText(status);
    }

}
