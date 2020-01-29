/*
 * Scribble Example with Basic Menus
 * Paul Schimpf, Sept 2016
 */
package jbergerHW1;

import java.util.Optional;

import javax.swing.undo.UndoManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class jbergerHW1 extends Application
{
    private static final double DIMX=400, DIMY=400 ;  // Canvas dimensions
    private Canvas mCanvas ;                          // Canvas to draw on
    private double mLastX, mLastY;                    // last location of mouse
    private Color  mColor = Color.BLACK;              // initial color
    private UndoManager mUndoManager = new UndoManager();
    
    @Override
    public void start(Stage primaryStage) {

        mCanvas = new Canvas(DIMX, DIMY);
        initCanvas();
        
        // showing two different ways to attach mouse event listeners
        mCanvas.setOnMousePressed(mouseEvent -> onMousePressed(mouseEvent));
        mCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> onMouseDragged(mouseEvent));

        // root container
        BorderPane root = new BorderPane();
        // setting the background of a container with CSS
        root.setStyle("-fx-background-color: lightgray;");
        root.setCenter(mCanvas);

        // build a menu
        MenuBar menuBar = buildMenus();
        root.setTop(menuBar);

        // put a scene on the stage
        primaryStage.setTitle("HW1");
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar buildMenus() {
        MenuBar menuBar = new MenuBar();

        // File menu with just new and exit for now
        Menu fileMenu = new Menu("_File");
        MenuItem item = new MenuItem("_New");
        item.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        item.setOnAction(actionEvent -> initCanvas());
        fileMenu.getItems().add(item);
        item = new MenuItem("_Quit");
        item.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        item.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(item);
        
        // Color menu
        // These are more appropriately done with RadioMenuItem and
        // ToggleGroup, which will be added later
        String[] colorItems = new String[]{"_Red", "_Green", "_Blue", "Blac_k"};
        Menu colorMenu = new Menu("_Color");
        for (String colorItem : colorItems) {
            item = new MenuItem(colorItem);
            item.setOnAction(actionEvent -> onColor(colorItem));
            colorMenu.getItems().add(item);
        }
        
        // Edit menu
        Menu editMenu = new Menu("_Edit");
        MenuItem undoItem = new MenuItem("_Undo");
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undoItem.setDisable(true);
        
        MenuItem redoItem = new MenuItem("_Redo");
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redoItem.setDisable(true);
        
        editMenu.getItems().addAll(undoItem, redoItem);
        
        // Help menu with an about item
        Menu helpMenu = new Menu("_Help");
        item = new MenuItem("_About");
        item.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(item);

        menuBar.getMenus().addAll(fileMenu, editMenu, colorMenu, helpMenu);

        return menuBar;
    }

    // TODO: modify this
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HW1");
        alert.setHeaderText("Jacob Berger, Winter 2020");
        Optional<ButtonType> result = alert.showAndWait();
    }

    // Color change handler
    private void onColor(String colorItem) {
        switch (colorItem) {
            case "_Red":
                mColor = Color.RED;
                break;
            case "_Green":
                mColor = Color.GREEN;
                break;
            case "_Blue":
                mColor = Color.BLUE;
                break;
            case "Blac_k":
                mColor = Color.BLACK;
                break;
        }
    }

    // initialize the canvas
    private void initCanvas() {
        GraphicsContext g = mCanvas.getGraphicsContext2D();
        UndoableNew undoableNew = new UndoableNew(mCanvas);
        undoableNew.addEdit(mUndoManager);
        refreshUndoRedo();
        // defaults to transparent, showing the pane color
        // fill it so we can distinguish the boundaries
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        g.setLineWidth(3);
    }

    private void refreshUndoRedo() {
		if (mUndoManager.canUndo()) {
			System.out.println("Can undo");
			MenuItem undoItem = buildMenus().getMenus().get(1).getItems().get(0);
			undoItem.setText(mUndoManager.getPresentationName());
			undoItem.setDisable(false);
		} else {
			buildMenus().getMenus().get(1).getItems().get(0).setDisable(true);
			System.out.println("Can't undo");
		}
		
		if (mUndoManager.canRedo()) {
			System.out.println("Can redo");
			MenuItem redoItem = buildMenus().getMenus().get(1).getItems().get(1);
			redoItem.setText(mUndoManager.getPresentationName());
			redoItem.setDisable(false);
		} else {
			buildMenus().getMenus().get(1).getItems().get(1).setDisable(true);
			System.out.println("Can't redo");
		}
		
	}

	// when the mouse is pressed, save the coordinates
    private void onMousePressed(MouseEvent e) {
        mLastX = e.getX();
        mLastY = e.getY();
    }
    
    // when the mouse is dragged, draw a line
    private void onMouseDragged(MouseEvent e) {
        GraphicsContext g = mCanvas.getGraphicsContext2D();
        g.setStroke(mColor);
        g.strokeLine(mLastX, mLastY, e.getX(), e.getY());
        mLastX = e.getX();
        mLastY = e.getY();
    }

    // this is where we start execution
    public static void main(String[] args) {
        launch(args);
    }

}
