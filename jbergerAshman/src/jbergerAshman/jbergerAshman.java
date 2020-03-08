package jbergerAshman;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Random;

public class jbergerAshman extends Application {

    Label mStatus;
    int level;
    int cakesEaten;
    int remaining;
    int numGhosts;
    double ghostSpeed;
    int[] playerLocation;
    Image player;
    Paint ash = Color.GREEN;
    Paint ghost = Color.RED;
    double width = 400;
    double height = 500;
    boolean run;
    Image ghost1, ghost2, ghost3, ghost4;
    int[] ghost1location, ghost2location, ghost3location, ghost4location;
    long previousTime = System.currentTimeMillis();
    GameBoard gameBoard;
    private boolean paused;
    private AnimationTimer timer;

    boolean goNorth, goEast, goSouth, goWest;

    Line line;

    public static void main(String[] args) {
        launch(args);
    }

    public int[] getGhostLocation(int ghostNumber) {
        switch (ghostNumber) {
            case 1:
                return ghost1location;
            case 2:
                return ghost2location;
            case 3:
                return ghost3location;
            default:
                return ghost4location;
        }
    }

    public void setGhostlocation(int ghostNumber, int[] location) {
        switch (ghostNumber) {
            case 1:
                ghost1location = location;
                break;
            case 2:
                ghost2location = location;
                break;
            case 3:
                ghost3location = location;
                break;
            default:
                ghost4location = location;
                break;
        }
    }

    public Label getStatus() {
        return mStatus;
    }

    public void setStatus(Label mStatus) {
        this.mStatus = mStatus;
    }

    public void setStatus(String status) {
        mStatus.setText(status);
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
        setRemaining(223);
        setCakesEaten(1);
        setNumGhosts(2);
        setLevel(1);
        setPaused(false);
        playerLocation = new int[]{0, 0};
        player = new Image("Images/Player.png", 20, 20, false, false);
        Circle circle = new Circle(10, ash);
        circle.setFill(ash);
        gameBoard.tilePane.getChildren().set(0, circle);
        createGhosts();


        root.setCenter(gameBoard);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, width, height);

        //Timer?
        Button button = new Button("Start timer");
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onTimer(now);
                if (goNorth) setStatus("North");
                if (goEast) setStatus("East");
                if (goSouth) setStatus("South");
                if (goWest) setStatus("West");

                movePlayer();
            }
        };

        button.setOnAction(actionEvent -> timer.start());
        HBox bottomBox = new HBox();
        line = new Line();
        line.setStrokeWidth(20);
        bottomBox.getChildren().addAll(button, toolBar, line);

        root.setBottom(bottomBox);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: goNorth = true; break;
                    case RIGHT: goEast = true; break;
                    case DOWN: goSouth = true; break;
                    case LEFT: goWest = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                }
            }
        });


        //Hopefully just for testing
        primaryStage.setResizable(false);


        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void movePlayer() {
        if (goNorth) {
            playerLocation[0] -= 1;
        }
        if (goEast) {
            playerLocation[1] += 1;
        }
        if (goSouth) {
            playerLocation[0] += 1;
        }
        if (goWest) {
            playerLocation[1] -= 1;
        }

        drawPlayer();
    }

    private void drawPlayer() {
        setNodeAtLocation(playerLocation[0], playerLocation[1], player);
    }

    private void rotate() {
        Rotate rotate = new Rotate();
        rotate.setAngle(1.5);
        line.getTransforms().add(rotate);
    }

    private void onTimer(long now) {
        now = System.currentTimeMillis();
        long elapsed = (now - previousTime);
        previousTime = now;
        setStatus("" + elapsed);
        rotate();
    }

    private void createGhosts() {
        int ix = 0;
        Random random = new Random();
        boolean success = false;
        while (!success) {
            int x = random.nextInt(18) + 1;
            int y = random.nextInt(19);
            ghost1 = new Image("Images/Stu.jpg", 20, 20, false, false);
            if (setNodeAtLocation(x, y, ghost1)) {
                success = true;
                setGhostlocation(1, new int[]{x, y});
            }
        }
        success = false;

        while (!success) {
            int x = random.nextInt(18) + 1;
            int y = random.nextInt(19);
            ghost2 = new Image("Images/Stu.jpg", 20, 20, false, false);
            if (x != getGhostLocation(1)[0] || y != getGhostLocation(1)[1]) {
                if (setNodeAtLocation(x, y, ghost2)) {
                    success = true;
                    setGhostlocation(2, new int[]{x, y});
                }
            }
        }
        success = false;

        if (getNumGhosts() > 2) {
            while (!success) {
                int x = random.nextInt(18) + 1;
                int y = random.nextInt(19);
                ghost3 = new Image("Images/Stu.jpg", 20, 20, false, false);
                if ((x != getGhostLocation(1)[0] || y != getGhostLocation(1)[1]) && (x != getGhostLocation(2)[0] || y != getGhostLocation(2)[1])) {
                    if (setNodeAtLocation(x, y, ghost3)) {
                        success = true;
                        setGhostlocation(3, new int[]{x, y});
                    }
                }
            }
            success = false;

            while (!success) {
                int x = random.nextInt(18) + 1;
                int y = random.nextInt(19);
                ghost4 = new Image("Images/Stu.jpg", 20, 20, false, false);
                if ((x != getGhostLocation(1)[0] || y != getGhostLocation(1)[1]) && (x != getGhostLocation(2)[0] || y != getGhostLocation(2)[1]) && (x != getGhostLocation(3)[0] || y != getGhostLocation(3)[1])) {
                    if (setNodeAtLocation(x, y, ghost4)) {
                        success = true;
                        setGhostlocation(4, new int[]{x, y});
                    }
                }
            }
        }
    }

    public boolean getPaused() {
        return paused;
    }

    private void setPaused(boolean b) {
        paused = b;
    }

    private int getNumGhosts() {
        return numGhosts;
    }

    private void setNumGhosts(int i) {
        numGhosts = i;
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

    private ImageView getNodeAtPlayer() {
        int currentTile = playerLocation[0] * 20 + playerLocation[1];

        return (ImageView) gameBoard.tilePane.getChildren().get(currentTile);
    }

    private void setNodeAtPlayer(Tile tile) {
        Rectangle rectangle = new Rectangle(20, 20);
        Paint paint;
        switch (tile) {
            case EMPTY:
                paint = Color.WHITE;
                rectangle.setFill(paint);
                getNodeAtPlayer().setImage(new Image(String.valueOf(rectangle)));
                break;
            case CAKE:
                getNodeAtPlayer().setImage(new Image("Images/Cake.png", 20, 20, false, false));
                break;
            case WALL:
                paint = Color.BLACK;
                rectangle.setFill(paint);
                getNodeAtPlayer().setImage(new Image(String.valueOf(rectangle)));
                break;
        }
    }

    private Node getNodeAtLocation(int x, int y) {
        return gameBoard.tilePane.getChildren().get(x * 20 + y);
    }

    private boolean setNodeAtLocation(int x, int y, Image image) {
        Node node = getNodeAtLocation(x, y);
        if (node.getClass() == ImageView.class) {
            ((ImageView) node).setImage(image);
            return true;
        } else {
//            System.out.println(String.format("No ImageView at %d, %d", x, y));
            return false;
        }
    }



}
