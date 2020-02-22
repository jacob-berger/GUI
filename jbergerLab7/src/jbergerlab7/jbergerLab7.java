package jbergerlab7;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class jbergerLab7 extends Application {
    
    Label mStatus;
    Image X = new Image("Images/x.png");
    Image O = new Image("Images/o.png");

    @Override
    public void start(Stage primaryStage) {

        System.out.println("STILL NEED TO IMPLEMENT TASK 1 PART 7 FOR BLANK CELLS");

        BorderPane root = new BorderPane();

        ImageView xImageView = new ImageView("Images/x.png");
        xImageView.setUserData("X");
        xImageView.setOnDragDetected(event -> onDrag(event));

        ImageView oImageView = new ImageView("Images/o.png");
        oImageView.setUserData("O");
        oImageView.setOnDragDetected(event -> onDrag(event));

        HBox hbox = new HBox();
        hbox.getChildren().addAll(xImageView, oImageView);
        hbox.setAlignment(Pos.CENTER);
        
        GridPane gridPane = createGridPane();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox, gridPane);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, 290, 450);

        primaryStage.setTitle("Jacob Berger Lab 7");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void onDrag(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.COPY);
        dragboard.setDragView(imageView.getImage(), imageView.getImage().getWidth() / 2, imageView.getImage().getHeight() / 2);

        ClipboardContent clipboardContent = new ClipboardContent();
        if (imageView.getUserData().toString().equalsIgnoreCase("X")) {
            clipboardContent.putString("X");
            dragboard.setContent()
        }
        System.out.println(imageView.getUserData().toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private GridPane createGridPane() {
    	
    	GridPane gridPane = new GridPane();
    	
    	//Row 1
        gridPane.add(new ImageView("Images/blank.png"), 0, 0);
        gridPane.add(new ImageView("Images/vert.png"), 1, 0);
        gridPane.add(new ImageView("Images/blank.png"), 2, 0);
        gridPane.add(new ImageView("Images/vert.png"), 3, 0);
        gridPane.add(new ImageView("Images/blank.png"), 4, 0);
        
        //Row 2
        gridPane.add(new ImageView("Images/horiz.png"), 0, 1);
        gridPane.add(new ImageView("Images/horiz.png"), 2, 1);
        gridPane.add(new ImageView("Images/horiz.png"), 4, 1);
        
        //Row 3
        gridPane.add(new ImageView("Images/blank.png"), 0, 2);
        gridPane.add(new ImageView("Images/vert.png"), 1, 2);
        gridPane.add(new ImageView("Images/blank.png"), 2, 2);
        gridPane.add(new ImageView("Images/vert.png"), 3, 2);
        gridPane.add(new ImageView("Images/blank.png"), 4, 2);
        
        //Row 4
        gridPane.add(new ImageView("Images/horiz.png"), 0, 3);
        gridPane.add(new ImageView("Images/horiz.png"), 2, 3);
        gridPane.add(new ImageView("Images/horiz.png"), 4, 3);
        
        //Row 5
        gridPane.add(new ImageView("Images/blank.png"), 0, 4);
        gridPane.add(new ImageView("Images/vert.png"), 1, 4);
        gridPane.add(new ImageView("Images/blank.png"), 2, 4);
        gridPane.add(new ImageView("Images/vert.png"), 3, 4);
        gridPane.add(new ImageView("Images/blank.png"), 4, 4);     
        
        ObservableList<Node> children = gridPane.getChildren();
        children.get(0).setUserData("BLANK");
        children.get(2).setUserData("BLANK");
        children.get(4).setUserData("BLANK");
        children.get(8).setUserData("BLANK");
        children.get(10).setUserData("BLANK");
        children.get(12).setUserData("BLANK");
        children.get(16).setUserData("BLANK");
        children.get(18).setUserData("BLANK");
        children.get(20).setUserData("BLANK");
        //0 2 4 10 12 14 20 22 24
        
        return gridPane;
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Lab 7, Winter 2020");
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
