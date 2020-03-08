package jbergerAshman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.paint.*;

public class jbergerAshman extends Application {
    
    Label mStatus;
    int level;
    int cakesEaten;
    int getRemaining;
    double ghostSpeed;
    GameBoard gameBoard;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        gameBoard = new GameBoard();
        Paint wall = Color.BLACK;
        Paint blank = Color.WHITE;
        root.setCenter(gameBoard);

        for (int ix = 0; ix < 20; ix++) {
            for (int iy = 0; iy < 20; iy++) {
//                if (board[ix][iy] == Tile.EMPTY) {
//                    Rectangle rectangle = new Rectangle(width / 20, width / 20);
//                    rectangle.setFill(blank);
//                    tilePane.getChildren().add(rectangle);
//                } else {
//                    Rectangle rectangle = new Rectangle(width / 20, width / 20);
//                    rectangle.setFill(wall);
//                    tilePane.getChildren().add(rectangle);
//                }
            }
        }

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
