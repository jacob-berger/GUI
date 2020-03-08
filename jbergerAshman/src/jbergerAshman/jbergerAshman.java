package jbergerAshman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class jbergerAshman extends Application {

    Label mStatus;
    int level;
    int cakesEaten;
    int remaining;
    double ghostSpeed;
    GameBoard gameBoard;

    public static void main(String[] args) {
        launch(args);
    }

    public Label getStatus() {
        return mStatus;
    }

    public void setStatus(Label mStatus) {
        this.mStatus = mStatus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCakesEaten() {
        return cakesEaten;
    }

    public void setCakesEaten(int cakesEaten) {
        this.cakesEaten = cakesEaten;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public double getGhostSpeed() {
        return ghostSpeed;
    }

    public void setGhostSpeed(double ghostSpeed) {
        this.ghostSpeed = ghostSpeed;
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        gameBoard = new GameBoard();
        //224 cakes total including where player starts
        setRemaining(224);
        setCakesEaten(0);
        root.setCenter(gameBoard);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, 400, 500);



        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Lab Template, Winter 2020");
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

}
