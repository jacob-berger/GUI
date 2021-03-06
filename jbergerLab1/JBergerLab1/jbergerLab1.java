package jbergerlab1;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class jbergerLab1 extends Application implements ChangeListener<String>{
    
    Label mStatus;
    ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        imageView.setImage(new Image("B52s/logo.png"));

        BorderPane root = new BorderPane();
        root.setCenter(imageView);
        root.setPrefWidth(imageView.getFitWidth());
        
        ListView<String> listView = new ListView<>();
        root.setLeft(listView);

        ObservableList<String> observableList = observableArrayList();
        observableList.add("First Album");
        observableList.add("Cindy");
        observableList.add("Fred");
        observableList.add("Kate");
        observableList.add("Keith");
        observableList.add("Matt");
        observableList.add("Rickey");
        listView.setItems(observableList);
        
        listView.getSelectionModel().selectedItemProperty().addListener(this);
        
        listView.setPrefWidth(computeStringWidth("First Album") * 1.3);
        
     
        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Jacob Berger Lab 1");
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
        alert.setHeaderText("Jacob Berger, CSCD370 Lab 1, Winter 2020");
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
        fileMenu.getItems().add(quitMenuItem);
        
        //Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        return menuBar;
    }
    
    public void setStatus(String status) {
        mStatus.setText(status);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        switch (newValue) {
            case "First Album":
                imageView.setImage(new Image("B52s/logo.png"));
                setStatus("First Album, 1979");
                break;
            case "Cindy":
                imageView.setImage(new Image("B52s/cindy.png"));
                setStatus("Cindy Wilson (Percussion since 1976)");
                break;
            case "Fred":
                imageView.setImage(new Image("B52s/fred.png"));
                setStatus("Fred Schneider (Vocals, Cowbell, since 1976)");
                break;
            case "Kate":
                imageView.setImage(new Image("B52s/kate.png"));
                setStatus("Kate Pierson (Organ since 1976)");
                break;
            case "Keith":
                imageView.setImage(new Image("B52s/keith.png"));
                setStatus("Keith Strickland (Drums, Guitar, since 1976)");
                break;
            case "Matt":
                imageView.setImage(new Image("B52s/matt.png"));
                setStatus("Matt Flynn (Touring, Drums, prior to 2004)");
                break;
            case "Rickey":
                imageView.setImage(new Image("B52s/rickey.png"));
                setStatus("Rickey Wilson, deceased (Bass, 1976-1985)");
                break;
        }
    }
    
    private double computeStringWidth(String s) {
    	//put it in a Text object
    	final Text text = new Text(s);
    	//now get the width
    	return text.getLayoutBounds().getWidth();
    }
}
