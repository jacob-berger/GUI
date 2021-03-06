package jbergerHW2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
 
public class jbergerHW2 extends Application {
    
    Label mStatus;
    Canvas mCanvas;
    AnimationTimer mTimer;
    double mCurrentAngle;
    int mLength = 125;
    

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, 400, 250);
        initCanvas();
        root.setCenter(mCanvas);
        
        mTimer = new AnimationTimer() {
        	public void handle(long now) {
        		
        	}
        };

        primaryStage.setTitle("Jacob Berger HW2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void initCanvas() {
    	if (mCanvas == null) {
    		mCanvas = new Canvas();
    		mCanvas.setWidth(400);
    		mCanvas.setHeight(200);
    	}
    	
    	GraphicsContext gc = mCanvas.getGraphicsContext2D();
    	gc.setGlobalAlpha(0.6);
    	gc.drawImage(new Image("Images/Background.jpg"), 0, 0, mCanvas.getWidth(), mCanvas.getHeight());
    	
    	gc.setGlobalAlpha(1);
    	gc.setStroke(Color.BLACK);
    	gc.setLineWidth(2.5);
    	gc.strokeLine(mCanvas.getWidth()/2, 0, mCanvas.getWidth()/2, 125);
    	
    	gc.beginPath();
    	gc.arc(mCanvas.getWidth()/2, 125, 15, 15, 0, 360);
    	gc.closePath();
    	gc.clip();    	
    	
    	Image stu = new Image("Images/Stu.jpg");
    	gc.drawImage(stu, mCanvas.getWidth()/2 - 15, 110, 30, 30);
    	
    	
    	gc.strokeOval(mCanvas.getWidth()/2 - 15, 110, 30, 30);
    	
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Homework 2, Winter 2020");
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
