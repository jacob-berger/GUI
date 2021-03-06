package jbergerLab9;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.tools.Tool;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class jbergerLab9 extends Application {

    Label mStatus;
    Canvas mCanvas, mPermCanvas;
    double mLastX, mLastY;
    Point2D mFrom, mTo;
    Color mColor = Color.BLACK;
    int mBrushSize = 1;
    ToggleGroup mWidthToggle, mColorToggle;
    ToolbarPosition mToolbarPosition;
    BorderPane root;
    boolean mSave;
    ArrayList<Line> lines;
    File mFilename;
    private Stage mStage;
    private File mFilePath;

    public static void main(String[] args) {
        launch(args);
    }

    public MenuBar buildMenuBar() {
        // build a menu bar
        MenuBar menuBar = new MenuBar();

        // file menu
        Menu fileMenu = new Menu("_File");
        MenuItem newMenuItem = new MenuItem("_New");
        MenuItem openMenuItem = new MenuItem("_Open");
        MenuItem saveMenuItem = new MenuItem("_Save");
        MenuItem saveAsMenuItem = new MenuItem("Save _As");
        MenuItem printMenuItem = new MenuItem("_Print");
        MenuItem printPreviewMenuItem = new MenuItem("Print Pre_view");
        MenuItem quitMenuItem = new MenuItem("E_xit");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent -> onFileNew());
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(actionEvent -> onFileOpen());
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setOnAction(actionEvent -> onFileSave(false));
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        saveAsMenuItem.setOnAction(actionEvent -> onFileSave(true));
        printMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        printMenuItem.setOnAction(actionEvent -> onPrint());
        printPreviewMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        printPreviewMenuItem.setOnAction(actionEvent -> onPrintPrevew());
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, new SeparatorMenuItem(), printMenuItem, printPreviewMenuItem, new SeparatorMenuItem(),
                quitMenuItem);

        // Help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        // Width menu
        Menu widthMenu = new Menu("_Width");
        mWidthToggle = new ToggleGroup();
        RadioMenuItem onePixelMenuItem = new RadioMenuItem("_1 Pixel");
        RadioMenuItem fourPixelsMenuItem = new RadioMenuItem("_4 Pixels");
        RadioMenuItem eightPixelsMenuItem = new RadioMenuItem("_8 Pixels");
        onePixelMenuItem.setOnAction(actionEvent -> onSelectWidth(1, false));
        onePixelMenuItem.setToggleGroup(mWidthToggle);
        onePixelMenuItem.setSelected(true);
        fourPixelsMenuItem.setOnAction(actionEvent -> onSelectWidth(4, false));
        fourPixelsMenuItem.setToggleGroup(mWidthToggle);
        eightPixelsMenuItem.setOnAction(actionEvent -> onSelectWidth(8, false));
        eightPixelsMenuItem.setToggleGroup(mWidthToggle);
        widthMenu.getItems().addAll(onePixelMenuItem, fourPixelsMenuItem, eightPixelsMenuItem);
        widthMenu.setOnShowing(event -> onWidthShowing(true));

        // Color menu
        Menu colorMenu = new Menu("_Color");
        mColorToggle = new ToggleGroup();
        RadioMenuItem blackMenuItem = new RadioMenuItem("_Black");
        RadioMenuItem redMenuItem = new RadioMenuItem("_Red");
        RadioMenuItem greenMenuItem = new RadioMenuItem("_Green");
        RadioMenuItem blueMenuItem = new RadioMenuItem("_Blue");
        blackMenuItem.setOnAction(actionEvent -> onSelectColor(Color.BLACK));
        blackMenuItem.setToggleGroup(mColorToggle);
        blackMenuItem.setSelected(true);
        redMenuItem.setOnAction(actionEvent -> onSelectColor(Color.RED));
        redMenuItem.setToggleGroup(mColorToggle);
        greenMenuItem.setOnAction(actionEvent -> onSelectColor(Color.GREEN));
        greenMenuItem.setToggleGroup(mColorToggle);
        blueMenuItem.setOnAction(actionEvent -> onSelectColor(Color.BLUE));
        blueMenuItem.setToggleGroup(mColorToggle);
        colorMenu.getItems().addAll(blackMenuItem, redMenuItem, greenMenuItem, blueMenuItem);
        colorMenu.setOnShowing(event -> onColorShowing(true));

        menuBar.getMenus().addAll(fileMenu, widthMenu, colorMenu, helpMenu);

        return menuBar;
    }

	private void onPrintPrevew() {
	}

	private void onPrint() {
	}

	public ToolBar buildToolBar() {
        ToolBar toolbar = new ToolBar();
        toolbar.setOrientation(Orientation.VERTICAL);

        Button newButton = new Button();
        newButton.setGraphic(new ImageView(new Image("New.png")));
        newButton.setOnAction(actionEvent -> onNewButton());
        newButton.setTooltip(new Tooltip("New"));

        Button openButton = new Button();
        openButton.setGraphic(new ImageView(new Image("Open.png")));
        openButton.setOnAction(actionEvent -> onOpenButton());
        openButton.setTooltip(new Tooltip("Open"));

        Button saveButton = new Button();
        saveButton.setGraphic(new ImageView(new Image("Save.png")));
        saveButton.setOnAction(actionEvent -> onSaveButton());
        saveButton.setTooltip(new Tooltip("Save"));

        Button printPreviewButton = new Button();
        printPreviewButton.setGraphic(new ImageView(new Image("Print.png")));
        printPreviewButton.setOnAction(actionEvent -> onPrintPrevew());
        printPreviewButton.setTooltip(new Tooltip("Print Preview"));

        Button moveButton = new Button();
        moveButton.setGraphic(new ImageView(new Image("Move.png")));
        moveButton.setOnAction(actionEvent -> onMoveButton());
        moveButton.setTooltip(new Tooltip("Move"));

        Button colorButton = new Button();
        colorButton.setGraphic(new ImageView(new Image("Color.png")));
        colorButton.setOnAction(actionEvent -> onColorButton());
        colorButton.setTooltip(new Tooltip("Color"));

        Button widthButton = new Button();
        widthButton.setGraphic(new ImageView(new Image("Width.png")));
        widthButton.setOnAction(actionEvent -> onWidthButton());
        widthButton.setTooltip(new Tooltip("Width"));

        toolbar.getItems().addAll(newButton, new Separator(), openButton, new Separator(), saveButton, new Separator(), printPreviewButton, new Separator(),
                moveButton, new Separator(), colorButton, new Separator(), widthButton);

        return toolbar;
    }

    private void drawLine(Canvas canvas, Paint paint, boolean eraseTempLines) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(paint);
        if (eraseTempLines) {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        graphicsContext.strokeLine(mFrom.getX(), mFrom.getY(), mTo.getX(), mTo.getY());

    }

    private Canvas initializeCanvas(Color color) {
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setLineWidth(2);

        return canvas;
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Jacob Berger, CSCD370 Lab 9, Winter 2020");
        alert.showAndWait();
    }

    private void onColorButton() {
        onSelectColor(null);
        setStatus("Color toolbar option selected.");
    }

    private void onColorShowing(boolean b) {
        ObservableList<Toggle> toggles = mColorToggle.getToggles();
        Toggle item;
        if (mColor == Color.BLACK) {
            item = toggles.get(0);
        } else if (mColor == Color.RED) {
            item = toggles.get(1);
        } else if (mColor == Color.GREEN) {
            item = toggles.get(2);
        } else {
            item = toggles.get(3);
        }
        mColorToggle.selectToggle(item);
    }

    private void onFileNew() {
        setStatus("New menu option selected.");
        GraphicsContext graphicsContext = mPermCanvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, mPermCanvas.getWidth(), mPermCanvas.getHeight());
        graphicsContext = mCanvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        mStage.setTitle("(untitled)");
        mSave = false;
    }

    private void onFileOpen() {
        setStatus("Open menu option selected.");
    }

    private void onFileSave(boolean saveAs) {
        File selectedFile = mFilename;
        setStatus("Save menu option selected.");
        if (mFilename == null || saveAs) {
            setStatus("Save As menu option selected.");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Line File");
            fileChooser.setInitialDirectory(mFilePath);
            ExtensionFilter lineFilter = new ExtensionFilter("Line File", "*.line");
            ExtensionFilter allFilter = new ExtensionFilter("All Files", "*.*");
            fileChooser.getExtensionFilters().addAll(lineFilter, allFilter);
            if (mFilename != null) {
                fileChooser.setInitialFileName(mFilename.getName());
            }
            selectedFile = fileChooser.showSaveDialog(mStage);
        }

        if (selectedFile != null) {
            try {
                FileOutputStream fout = new FileOutputStream(selectedFile);
                ObjectOutputStream objectOut = new ObjectOutputStream(fout);
//				objectOut.writeObject(line.lines);
                objectOut.writeObject(Line.class.getField("lines"));
                objectOut.close();
                fout.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            mFilename = selectedFile;
            mStage.setTitle(mFilename.getName());
            mSave = false;
        }
        //		mSave = false;
    }

    private void onMouseDragged(MouseEvent e) {
        mLastX = e.getX();
        mLastY = e.getY();
        mTo = new Point2D(mLastX, mLastY);
        GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(mBrushSize);
        if (mCanvas.contains(mTo)) {
            drawLine(mCanvas, mColor, true);
        } else {
            graphicsContext = mCanvas.getGraphicsContext2D();
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        }
        setStatus("Mouse dragged");
    }

    private void onMousePressed(MouseEvent e) {
        if (e.isPrimaryButtonDown()){
            mLastX = e.getX();
            mLastY = e.getY();
            mFrom = new Point2D(mLastX, mLastY);
            setStatus("Mouse pressed");
        } else {
            Point2D currentPosition = new Point2D(e.getX(), e.getY());
            Point2D bestCandidate = new Point2D(lines.get(0).getStart().getX(), lines.get(0).getStart().getY());
            for (Line line : lines) {
                double x = Math.pow(e.getX() - line.getStart().getX(), 2);
                double y = Math.pow(e.getY() - line.getStart().getY(), 2);
                double distance = Math.sqrt(x + y);
            }
        }
    }

    private void onMouseReleased(MouseEvent e) {
        mTo = new Point2D(mLastX, mLastY);
        GraphicsContext graphicsContext = mPermCanvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(mBrushSize);
        mSave = true;
        if (mPermCanvas.contains(mTo)) {
            // drawLine(mPermCanvas, mColor, false);
            Line line = new Line(mFrom, mTo, mBrushSize, mColor);
            line.draw(mPermCanvas);
            line.AddLine(line);

        }
        setStatus(
                String.format("From (%.2f, %.2f) to (%.2f, %.2f)", mFrom.getX(), mFrom.getY(), mTo.getX(), mTo.getY()));
    }

    private void onMoveButton() {
        switch (mToolbarPosition) {
            case LEFT:
                root.setLeft(null);
                VBox box = (VBox) root.getChildren().get(1);
                ToolBar toolbar = buildToolBar();
                toolbar.setOrientation(Orientation.HORIZONTAL);
                box.getChildren().add(toolbar);
                mToolbarPosition = ToolbarPosition.TOP;
                break;
            case TOP:
                box = (VBox) root.getChildren().get(1);
                box.getChildren().remove(1);
                root.setRight(buildToolBar());
                mToolbarPosition = ToolbarPosition.RIGHT;
                break;
            case RIGHT:
                root.setRight(null);
                root.setLeft(buildToolBar());
                mToolbarPosition = ToolbarPosition.LEFT;
                break;
        }
        setStatus("Move toolbar option selected.");
    }

    private void onNewButton() {
        onFileNew();
        setStatus("New toolbar option selected.");
    }

    private void onOpenButton() {
        onFileOpen();
        setStatus("Open toolbar option selected.");
    }

    private void onSaveButton() {
        onFileSave(false);
        setStatus("Save toolbar option selected.");
    }

    private void onSelectColor(Color paint) {
        if (paint != null) {
            mColor = paint;
        } else {
            if (mColor == Color.BLACK) {
                mColor = Color.RED;
            } else if (mColor == Color.RED) {
                mColor = Color.GREEN;
            } else if (mColor == Color.GREEN) {
                mColor = Color.BLUE;
            } else if (mColor == Color.BLUE) {
                mColor = Color.BLACK;
            }
        }
        setStatus("Color changed to " + paint.toString() + " .");
    }

    private void onSelectWidth(int width, boolean cycle) {
        if (width != 0) {
            mBrushSize = width;
        } else {
            if (this.mBrushSize == 1) {
                mBrushSize = 4;
            } else if (mBrushSize == 4) {
                mBrushSize = 8;
            } else if (mBrushSize == 8) {
                mBrushSize = 1;
            }
        }
        setStatus("Width changed to " + width + " pixels.");
    }

    private void onWidthButton() {
        onSelectWidth(0, true);
        setStatus("Width toolbar option selected.");
    }

    private void onWidthShowing(boolean b) {
        ObservableList<Toggle> toggles = mWidthToggle.getToggles();
        Toggle item;
        if (mBrushSize == 1) {
            item = toggles.get(0);
        } else if (mBrushSize == 4) {
            item = toggles.get(1);
        } else {
            item = toggles.get(2);
        }
        mWidthToggle.selectToggle(item);
    }

    public void setStatus(String status) {
        mStatus.setText(status);
    }

    @Override
    public void start(Stage primaryStage) {

        mStage = primaryStage;
        mSave = false;
        mFilename = null;
        root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        StackPane stackPane = new StackPane();
        mCanvas = initializeCanvas(Color.WHITE);
        mPermCanvas = initializeCanvas(Color.TRANSPARENT);

        stackPane.getChildren().add(mCanvas);
        stackPane.getChildren().add(mPermCanvas);

        root.setCenter(scrollPane);
        scrollPane.setContent(stackPane);

        // Add the menus
        VBox topBox = new VBox(buildMenuBar());
        root.setTop(topBox);
        mToolbarPosition = ToolbarPosition.LEFT;
        mStatus = new Label("Everything is Copacetic");
        ToolBar statusToolbar = new ToolBar(mStatus);
        root.setBottom(statusToolbar);
        root.setLeft(buildToolBar());
        Scene scene = new Scene(root);
        mPermCanvas.setOnMousePressed(mouseEvent -> onMousePressed(mouseEvent));
        mPermCanvas.setOnMouseDragged(mouseEvent -> onMouseDragged(mouseEvent));
        mPermCanvas.setOnMouseReleased(mouseEvent -> onMouseReleased(mouseEvent));

        primaryStage.setTitle("(untitled)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
