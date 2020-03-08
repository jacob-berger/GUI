package jbergerAshman;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameBoard extends Region {

    public Tile[][] board;
    public TilePane tilePane;


    public GameBoard() {
        tilePane = new TilePane();
        tilePane.setPrefColumns(20);
        tilePane.setPrefRows(20);
        board = createBoard();
        getChildren().add(tilePane);
    }

    private Tile[][] createBoard() {

        Tile[][] result = new Tile[20][20];
        //Row 1
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 2 || ix == 6 || ix == 7 || ix == 8) {
                result[0][ix] = Tile.CAKE;
            } else {
                result[0][ix] = Tile.WALL;
            }
        }

        //Row 2
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 2 || ix == 3 || ix == 4 || ix == 6 || ix == 8) {
                result[1][ix] = Tile.CAKE;
            } else {
                result[1][ix] = Tile.WALL;
            }
        }

        //Row 3
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 4 || ix == 6 || ix == 7 || ix == 8 || ix == 9) {
                result[2][ix] = Tile.CAKE;
            } else {
                result[2][ix] = Tile.WALL;
            }
        }

        //Row 4
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 1 || ix == 2 || ix == 4 || ix == 6 || ix == 8) {
                result[3][ix] = Tile.CAKE;
            } else {
                result[3][ix] = Tile.WALL;
            }
        }

        //Row 5
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 2 || ix == 4 || ix == 5 || ix == 6 || ix == 7 || ix == 8) {
                result[4][ix] = Tile.CAKE;
            } else {
                result[4][ix] = Tile.WALL;
            }
        }

        //Row 6
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 2 || ix == 3 || ix == 4 || ix == 8) {
                result[5][ix] = Tile.CAKE;
            } else {
                result[5][ix] = Tile.WALL;
            }
        }

        //Row 7
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 1 || ix == 2 || ix == 4 || ix == 5 || ix == 6 || ix == 8 || ix == 9) {
                result[6][ix] = Tile.CAKE;
            } else {
                result[6][ix] = Tile.WALL;
            }
        }

        //Row 8
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 4 || ix == 6 || ix == 7 || ix == 8) {
                result[7][ix] = Tile.CAKE;
            } else {
                result[7][ix] = Tile.WALL;
            }
        }

        //Row 9
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 1 || ix == 2 || ix == 3 || ix == 4 || ix == 6 || ix == 8 || ix == 9) {
                result[8][ix] = Tile.CAKE;
            } else {
                result[8][ix] = Tile.WALL;
            }
        }

        //Row 10
        for (int ix = 0; ix < 10; ix++) {
            if (ix == 0 || ix == 4 || ix == 8) {
                result[9][ix] = Tile.CAKE;
            } else {
                result[9][ix] = Tile.WALL;
            }
        }

        ////////////////MIRRORING LEFT AND RIGHT
        for (int ix = 10; ix < 20; ix++) {
            result[0][ix] = result[0][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[1][ix] = result[1][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[2][ix] = result[2][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[3][ix] = result[3][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[4][ix] = result[4][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[5][ix] = result[5][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[6][ix] = result[6][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[7][ix] = result[7][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[8][ix] = result[8][19 - ix];
        }
        for (int ix = 10; ix < 20; ix++) {
            result[9][ix] = result[9][19 - ix];
        }

        for (int ix = 10; ix < 20; ix++) {
            for (int iy = 0; iy < 20; iy++) {
                result[ix][iy] = result[19 - ix][iy];
            }
        }

//        for (int ix = 0; ix < 20; ix++) {
//            for (int iy = 0; iy < 20; iy++) {
//                System.out.println("Left " + ix + ": " + result[ix][19-iy] + " Right " + iy + ": " + result[ix][iy]);
//            }
//        }

        Paint empty = Color.WHITE;
        Paint wall = Color.BLACK;

        for (int ix = 0; ix < 20; ix++) {
            for (int iy = 0; iy < 20; iy++) {
                if (result[ix][iy] == Tile.CAKE) {
//                    Rectangle rectangle = new Rectangle(getWidth() / 20, getWidth() / 20);
//                    Rectangle rectangle = new Rectangle(20,20);
//                    rectangle.setFill(empty);
                    Image image = new Image("Images/Cake.png", 20, 20, false, false);
                    ImageView imageView = new ImageView(image);
//                    tilePane.getChildren().add(rectangle);
                    tilePane.getChildren().add(imageView);
                } else {
//                    Rectangle rectangle = new Rectangle(getWidth() / 20, getWidth() / 20);
                    Rectangle rectangle = new Rectangle(20,20);
                    rectangle.setFill(wall);
                    tilePane.getChildren().add(rectangle);
                }
            }
        }

        return result;
    }

    protected void layoutChildren() {
        double x = getWidth();
        double y = getHeight();

        this.setHeight(x * 20 + 10);
        this.setWidth(x * 20);

        layoutInArea(tilePane, 0,0, x, y, 0, HPos.CENTER, VPos.TOP);
    }

}
