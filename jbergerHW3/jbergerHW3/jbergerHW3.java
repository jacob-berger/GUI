package jbergerHW3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Observable;

public class jbergerHW3 extends Application {

    Label mStatus;
    VBox mMainDisplay;
    Subpanel mTop, mBottom;
    BorderPane root;

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        createDisplay();
        root.setCenter(mMainDisplay);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label();
        mStatus.textProperty().bind(mTop.pasteProperty);
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Jacob Berger Homework 3");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("Images/Stu.jpg"));
        primaryStage.show();
    }

    private VBox createDisplay() {
        mTop = new Subpanel("Edit me!");
        mBottom = new Subpanel("Me too!");
        mMainDisplay = new VBox(mTop, mBottom);
        VBox.setVgrow(mTop, Priority.ALWAYS);
        VBox.setVgrow(mBottom, Priority.ALWAYS);

        return mMainDisplay;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Homework 3, Winter 2020");
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

        Menu viewMenu = new Menu("_View");
        MenuItem defaultThemeItem = new MenuItem("_Default");
        defaultThemeItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        defaultThemeItem.setOnAction(actionEvent -> onThemeChange(true));
        MenuItem styledThemeItem = new MenuItem("_Garbage");
        styledThemeItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        styledThemeItem.setOnAction(actionEvent -> onThemeChange(false));
        viewMenu.getItems().addAll(defaultThemeItem, styledThemeItem);
        
        //Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        
        return menuBar;
    }

    private void onThemeChange(boolean defaultTheme) {
        if (!defaultTheme) {
            if (root.getStylesheets().size() == 0) {
                root.getStylesheets().add(getClass().getResource("CustomStyle.css").toExternalForm());
            }
        } else {
            if (root.getStylesheets().size() != 0) {
                root.getStylesheets().remove(0);
                boolean removed = (root.getStylesheets().size() == 0);
            }
        }
    }

    public void setStatus(String status) {
        mStatus.setText(status);
    }

}
