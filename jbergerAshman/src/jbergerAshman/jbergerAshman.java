package jbergerAshman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    long previousPlayerTime = System.currentTimeMillis();
    long previousGhostTime = System.currentTimeMillis();
    GameBoard gameBoard;
    boolean goNorth, goEast, goSouth, goWest;
    Line line;
    private boolean paused;
    private AnimationTimer playerTimer, ghostTimer;
    BorderPane root;

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

    public int getCakesRemaining() {
        return remaining;
    }

    public void setCakesRemaining(int remaining) {
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

        root = new BorderPane();

        gameBoard = new GameBoard();
        //224 cakes total including where player starts
        setCakesRemaining(223);
        setCakesEaten(1);
        setNumGhosts(2);
        setLevel(1);
        setPaused(false);
        playerLocation = new int[]{0, 0};
        player = new Image("Images/Player.png", 20, 20, false, false);

        createGhosts();
        setGhostSpeed(1);

        root.setCenter(gameBoard);

        //Add the menus
        root.setTop(buildMenuBar());
        mStatus = new Label("Everything is copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        Scene scene = new Scene(root, width, height);

        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(toolBar);

        root.setBottom(bottomBox);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = true;
                    goSouth = false;
                    break;
                case RIGHT:
                    goEast = true;
                    goWest = false;
                    break;
                case DOWN:
                    goNorth = false;
                    goSouth = true;
                    break;
                case LEFT:
                    goEast = false;
                    goWest = true;
                    break;
            }
        });

        //Hopefully just for testing
        primaryStage.setResizable(false);

        playerTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!getPaused()) {
                    if (getCakesRemaining() == 0) {
                        nextLevel();
                    }
                    onPlayerTimer(now);
                }
            }
        };
        playerTimer.start();

        ghostTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!getPaused()) {
                    onGhostTimer(now);
                }
            }
        };

        ghostTimer.start();

        primaryStage.setTitle("Jacob Berger Ashman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void nextLevel() {
        setLevel(2);
        setGhostSpeed(2);
        setCakesEaten(224);
        setCakesRemaining(224);
        setNumGhosts(4);
        gameBoard = new GameBoard();
        playerLocation = new int[]{0, 0};
        player = new Image("Images/Player.png", 20, 20, false, false);
        createGhosts();

        playerTimer.stop();
        ghostTimer.stop();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Level 1 passed!");
        alert.setContentText("Press OK to continue...");
//        alert.setOnHidden();
        alert.show();

        root.setCenter(gameBoard);

        playerTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!getPaused()) {
                    if (getCakesRemaining() == 0) {
                        //gameWon();
                    }
                    onPlayerTimer(now);
                }
            }
        };
        playerTimer.start();

        ghostTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!getPaused()) {
                    onGhostTimer(now);
                }
            }
        };

        setPaused(false);
    }

    private void onGhostTimer(long now) {
        now = System.currentTimeMillis();
        long elapsed = (now - previousGhostTime);
        if (elapsed >= 200 / getGhostSpeed()) {
            previousGhostTime = now;
            moveGhost(1);
            moveGhost(2);
            if (getNumGhosts() > 2) {
                moveGhost(3);
                moveGhost(4);
            }
        }
    }

    private void movePlayer() {
        if (goNorth) {
            if (playerLocation[0] != 0) {
                if (gameBoard.board[playerLocation[0] - 1][playerLocation[1]] != Tile.WALL) {
                    playerLocation[0] -= 1;
                    if (gameBoard.board[playerLocation[0]][playerLocation[1]] == Tile.CAKE) {
                        setCakesEaten(getCakesEaten() + 1);
                        setCakesRemaining(getCakesRemaining() - 1);
                        setNodeAtPlayer(Tile.EMPTY);
                    }
                    gameBoard.board[playerLocation[0]][playerLocation[1]] = Tile.EMPTY;
                    setNodeAtLocation(playerLocation[0] + 1, playerLocation[1], new Image("Images/Empty.png", 20, 20, false, false));
                } else {
                    goNorth = false;
                }
            }
        }
        if (goEast) {
            if (playerLocation[1] != 19) {
                if (gameBoard.board[playerLocation[0]][playerLocation[1] + 1] != Tile.WALL) {
                    playerLocation[1] += 1;
                    if (gameBoard.board[playerLocation[0]][playerLocation[1]] == Tile.CAKE) {
                        setCakesEaten(getCakesEaten() + 1);
                        setCakesRemaining(getCakesRemaining() - 1);
                        setNodeAtPlayer(Tile.EMPTY);
                    }
                    gameBoard.board[playerLocation[0]][playerLocation[1]] = Tile.EMPTY;
                    setNodeAtLocation(playerLocation[0], playerLocation[1] - 1, new Image("Images/Empty.png", 20, 20, false, false));
                } else {
                    goEast = false;
                }
            }
        }
        if (goSouth) {
            if (playerLocation[0] != 19) {
                if (gameBoard.board[playerLocation[0] + 1][playerLocation[1]] != Tile.WALL) {
                    playerLocation[0] += 1;
                    if (gameBoard.board[playerLocation[0]][playerLocation[1]] == Tile.CAKE) {
                        setCakesEaten(getCakesEaten() + 1);
                        setCakesRemaining(getCakesRemaining() - 1);
                        setNodeAtPlayer(Tile.EMPTY);
                    }
                    gameBoard.board[playerLocation[0]][playerLocation[1]] = Tile.EMPTY;
                    setNodeAtLocation(playerLocation[0] - 1, playerLocation[1], new Image("Images/Empty.png", 20, 20, false, false));
                } else {
                    goSouth = false;
                }
            }
        }
        if (goWest) {
            if (playerLocation[1] != 0) {
                if (gameBoard.board[playerLocation[0]][playerLocation[1] - 1] != Tile.WALL) {
                    playerLocation[1] -= 1;
                    if (gameBoard.board[playerLocation[0]][playerLocation[1]] == Tile.CAKE) {
                        setCakesEaten(getCakesEaten() + 1);
                        setCakesRemaining(getCakesRemaining() - 1);
                        setNodeAtPlayer(Tile.EMPTY);
                    }
                    gameBoard.board[playerLocation[0]][playerLocation[1]] = Tile.EMPTY;
                    setNodeAtLocation(playerLocation[0], playerLocation[1] + 1, new Image("Images/Empty.png", 20, 20, false, false));
                } else {
                    goWest = false;
                }
            }
        }

        drawPlayer();
    }

    private void moveGhost(int ghostNumber) {
        Image ghost;
        int[] ghostLocation = new int[2];
        switch (ghostNumber) {
            case 1:
                ghost = ghost1;
                ghostLocation = ghost1location;
                break;
            case 2:
                ghost = ghost2;
                ghostLocation = ghost2location;
                break;
            case 3:
                ghost = ghost3;
                ghostLocation = ghost3location;
                break;
            case 4:
                ghost = ghost4;
                ghostLocation = ghost4location;
                break;
        }

        Random random = new Random();
        int direction = random.nextInt(4);
        if (direction == 0) {
            if (ghostLocation[0] != 0) {
                if (gameBoard.board[ghostLocation[0] - 1][ghostLocation[1]] != Tile.WALL) {
                    if (gameBoard.board[ghostLocation[0] - 1][ghostLocation[1]] == Tile.EMPTY) {
                        ghostLocation[0] -= 1;
                        setNodeAtLocation(ghostLocation[0] + 1, ghostLocation[1], new Image("Images/Empty.png", 20, 20, false, false));
                    } else {
                        ghostLocation[0] -= 1;
                        setNodeAtLocation(ghostLocation[0] + 1, ghostLocation[1], new Image("Images/Cake.png", 20, 20, false, false));
                    }
                }
            }
        }
        if (direction == 1) {
            if (ghostLocation[1] != 19) {
                if (gameBoard.board[ghostLocation[0]][ghostLocation[1] + 1] != Tile.WALL) {
                    if (gameBoard.board[ghostLocation[0]][ghostLocation[1] + 1] == Tile.EMPTY) {
                        ghostLocation[1] += 1;
                        setNodeAtLocation(ghostLocation[0], ghostLocation[1] - 1, new Image("Images/Empty.png", 20, 20, false, false));
                    } else {
                        ghostLocation[1] += 1;
                        setNodeAtLocation(ghostLocation[0], ghostLocation[1] - 1, new Image("Images/Cake.png", 20, 20, false, false));
                    }
                }
            }
        }
        if (direction == 2) {
            if (ghostLocation[0] != 19) {
                if (gameBoard.board[ghostLocation[0] + 1][ghostLocation[1]] != Tile.WALL) {
                    if (gameBoard.board[ghostLocation[0] + 1][ghostLocation[1]] == Tile.EMPTY) {
                        ghostLocation[0] += 1;
                        setNodeAtLocation(ghostLocation[0] - 1, ghostLocation[1], new Image("Images/Empty.png", 20, 20, false, false));
                    } else {
                        ghostLocation[0] += 1;
                        setNodeAtLocation(ghostLocation[0] - 1, ghostLocation[1], new Image("Images/Cake.png", 20, 20, false, false));
                    }
                }
            }
        }
        if (direction == 3) {
            if (ghostLocation[1] != 0) {
                if (gameBoard.board[ghostLocation[0]][ghostLocation[1] - 1] != Tile.WALL) {
                    if (gameBoard.board[ghostLocation[0]][ghostLocation[1] - 1] == Tile.EMPTY) {
                        ghostLocation[1] -= 1;
                        setNodeAtLocation(ghostLocation[0], ghostLocation[1] + 1, new Image("Images/Empty.png", 20, 20, false, false));
                    } else {
                        ghostLocation[1] -= 1;
                        setNodeAtLocation(ghostLocation[0], ghostLocation[1] + 1, new Image("Images/Cake.png", 20, 20, false, false));
                    }
                }
            }
        }

        switch (ghostNumber) {
            case 1:
                ghost1location = ghostLocation;
                break;
            case 2:
                ghost2location = ghostLocation;
                break;
            case 3:
                ghost3location = ghostLocation;
                break;
            case 4:
                ghost4location = ghostLocation;
                break;
        }

        drawGhosts();
    }

    private void drawPlayer() {
        setNodeAtLocation(playerLocation[0], playerLocation[1], player);
    }

    private void drawGhosts() {
        setNodeAtLocation(ghost1location[0], ghost1location[1], ghost1);
        setNodeAtLocation(ghost2location[0], ghost2location[1], ghost2);
        if (getNumGhosts() > 2) {
            setNodeAtLocation(ghost3location[0], ghost3location[1], ghost3);
            setNodeAtLocation(ghost4location[0], ghost4location[1], ghost4);
        }
    }

    private void onPlayerTimer(long now) {
        now = System.currentTimeMillis();
        boolean ghostCollision = false;
        long elapsed = (now - previousPlayerTime);
        int playerX = playerLocation[0];
        int playerY = playerLocation[1];
        int ghost1X = ghost1location[0];
        int ghost1Y = ghost1location[1];
        int ghost2X = ghost2location[0];
        int ghost2Y = ghost2location[1];
        if (getNumGhosts() > 2) {
            int ghost3X = ghost3location[0];
            int ghost3Y = ghost3location[1];
            int ghost4X = ghost4location[0];
            int ghost4Y = ghost4location[1];
            if (playerX == ghost3X && playerY == ghost3Y) {
                ghostCollision = true;
            }
            if (playerX == ghost4X && playerY == ghost4Y) {
                ghostCollision = true;
            }
        }
        if (playerX == ghost1X && playerY == ghost1Y) {
            ghostCollision = true;
        }
        if (playerX == ghost2X && playerY == ghost2Y) {
            ghostCollision = true;
        }
        if (ghostCollision) {
            gameOver();
        }
        if (elapsed >= 100) {
            previousPlayerTime = now;
            movePlayer();
            setStatus(getCakesEaten() + "");
        }

        //Stop at 200 for testing without eating all cakes
        if (getCakesRemaining() == 200) {
            nextLevel();
        }
    }

    private void gameOver() {
        setPaused(true);
        playerTimer.stop();
        ghostTimer.stop();
        System.out.println("You hit a ghost!");

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
        alert.setHeaderText("Jacob Berger, CSCD370 Ashman, Winter 2020");
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
                getNodeAtPlayer().setImage(new Image("Images/Empty.png"));
                break;
            case CAKE:
                getNodeAtPlayer().setImage(new Image("Images/Cake.png", 20, 20, false, false));
                break;
            case WALL:
                getNodeAtPlayer().setImage(new Image("Images/Wall"));
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
