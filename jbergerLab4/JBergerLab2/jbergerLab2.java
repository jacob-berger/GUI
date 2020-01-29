package jbergerlab2;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
 
public class jbergerLab2 extends Application {
    
    Label mStatus;
    Canvas mCanvas, mPermCanvas;
    double mLastX, mLastY;
    Point2D mFrom, mTo;
    Paint mColor;
    int mBrushSize;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        StackPane stackPane = new StackPane();
        mCanvas = initializeCanvas(Color.WHITE);
        mPermCanvas = initializeCanvas(Color.TRANSPARENT);
        
        stackPane.getChildren().add(mCanvas);
        stackPane.getChildren().add(mPermCanvas);
        
        root.setCenter(scrollPane);
        scrollPane.setContent(stackPane);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root);
        mPermCanvas.setOnMousePressed(mouseEvent -> onMousePressed(mouseEvent));
        mPermCanvas.setOnMouseDragged(mouseEvent -> onMouseDragged(mouseEvent));
        mPermCanvas.setOnMouseReleased(mouseEvent -> onMouseReleased(mouseEvent));

        primaryStage.setTitle("Jacob Berger Lab 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Canvas initializeCanvas(Color color) {
		Canvas canvas = new Canvas(400, 400);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    	graphicsContext.setFill(color);
    	graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	graphicsContext.setLineWidth(2);
		
		return canvas;
	}
    
    private void onMousePressed(MouseEvent e) {
    	mLastX = e.getX();
    	mLastY = e.getY();
    	mFrom = new Point2D(mLastX, mLastY);
    	setStatus("Mouse pressed");
    }
    
    private void onMouseDragged(MouseEvent e) {
    	mLastX = e.getX();
    	mLastY = e.getY();
    	mTo = new Point2D(mLastX, mLastY);
    	if (mCanvas.contains(mTo)) {
			drawLine(mCanvas, mColor, true);
		} else {
			GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
			graphicsContext.setFill(Color.WHITE);
			graphicsContext.fillRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
		}
		setStatus("Mouse dragged");
    }
    
    private void onMouseReleased(MouseEvent e) {
    	mTo = new Point2D(mLastX, mLastY);
    	if (mPermCanvas.contains(mTo)) {
    		drawLine(mPermCanvas, mColor, false);
    	}
    	setStatus(String.format("From (%.2f, %.2f) to (%.2f, %.2f)", mFrom.getX(), mFrom.getY(), mTo.getX(), mTo.getY()));
    }
    
    private void drawLine(Canvas canvas, Paint paint, boolean eraseTempLines) {
    	GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    	graphicsContext.setStroke(paint);
    	if (eraseTempLines) {
    		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	}
    	graphicsContext.strokeLine(mFrom.getX(), mFrom.getY(), mTo.getX(), mTo.getY());
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Lab 2, Winter 2020");
        alert.showAndWait();
    }

    public MenuBar buildMenuBar() {
        //build a menu bar
        MenuBar menuBar = new MenuBar();

        //file menu
        Menu fileMenu = new Menu("_File");
        MenuItem newMenuItem = new MenuItem("_New");
        MenuItem openMenuItem = new MenuItem("_Open");
        MenuItem saveMenuItem = new MenuItem("_Save");
        MenuItem saveAsMenuItem = new MenuItem("Save _As");
        MenuItem quitMenuItem = new MenuItem("E_xit");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent -> onFileNew());
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(actionEvent -> onFileOpen());
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setOnAction(actionEvent -> onFileSave(false));
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        saveAsMenuItem.setOnAction(actionEvent -> onFileSave(true));
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, new SeparatorMenuItem(), quitMenuItem);
        
        //Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);
        
        //Width menu
        Menu widthMenu = new Menu("_Width");
        RadioMenuItem onePixelMenuItem = new RadioMenuItem("_1 Pixel");
        RadioMenuItem fourPixelsMenuItem = new RadioMenuItem("_4 Pixels");
        RadioMenuItem eightPixelsMenuItem = new RadioMenuItem("_8 Pixels");
        onePixelMenuItem.setOnAction(actionEvent -> onSelectWidth(1));
        fourPixelsMenuItem.setOnAction(actionEvent -> onSelectWidth(4));
        eightPixelsMenuItem.setOnAction(actionEvent -> onSelectWidth(8));
        widthMenu.getItems().addAll(onePixelMenuItem, fourPixelsMenuItem, eightPixelsMenuItem);
        
        //Color menu
        Menu colorMenu = new Menu("_Color");
        RadioMenuItem blackMenuItem = new RadioMenuItem("_Black");
        RadioMenuItem redMenuItem = new RadioMenuItem("_Red");
        RadioMenuItem greenMenuItem = new RadioMenuItem("_Green");
        RadioMenuItem blueMenuItem = new RadioMenuItem("_Blue");
        blackMenuItem.setOnAction(actionEvent -> onSelectColor(Color.BLACK));
        redMenuItem.setOnAction(actionEvent -> onSelectColor(Color.RED));
        greenMenuItem.setOnAction(actionEvent -> onSelectColor(Color.GREEN));
        blueMenuItem.setOnAction(actionEvent -> onSelectColor(Color.BLUE));
        colorMenu.getItems().addAll(blackMenuItem, redMenuItem, greenMenuItem, blueMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);
        
        return menuBar;
    }

	public void setStatus(String status) {
        mStatus.setText(status);
    }
    
    private void onFileNew() {
    	setStatus("New menu option selected.");
    	GraphicsContext graphicsContext = mPermCanvas.getGraphicsContext2D();
    	graphicsContext.clearRect(0, 0, mPermCanvas.getWidth(), mPermCanvas.getHeight());
    	graphicsContext = mCanvas.getGraphicsContext2D();
    	graphicsContext.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
    }

    private void onFileOpen() {
    	setStatus("Open menu option selected.");
	}
    
    private void onFileSave(boolean saveAs) {
    	if (!saveAs) {
    		setStatus("Save menu option selected.");
    	} else {
    		setStatus("Save As menu option selected.");
    	}
    }
    
    private void onSelectWidth(int width) {
    	mBrushSize = width;
    	setStatus("Width changed to " + width + " pixels.");
    }
    
    private void onSelectColor(Paint paint) {
    	mColor = paint;
    	setStatus("Color changed to " + paint.toString()+ " .");
    }
}
