package jbergerlab3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SevenSegment extends Region {
	
	public Canvas mCanvas;
	public int mCurrentValue;
	public boolean[] segmentStates = new boolean[7];
	public static final int marginSize = 2;
	public static final int logicalWidth = 14;
	public static final int logicalHeight = 22;
	public static final Paint onPaint = new Color(1, 0, 0, 1);
	public static final Paint offPaint = new Color(1, 0, 0, .1);
	public static final double[] xCoordinates = {0, 1, 7, 8, 7, 1};
	public static final double[] yCoordinates = {0, -1, -1, 0, 1, 1};
	
	public SevenSegment() {
		Region region = new Region();
		getChildren().add(mCanvas);
		mCurrentValue = 10;
		for (boolean segment : segmentStates) {
			segment = false;
		}
		
		region.setPrefSize(logicalWidth * 5, logicalHeight * 5);
	}
	
	public SevenSegment(int value) {
		this();
		if (value < 0 || value > 10) {
			throw new IllegalArgumentException("Initial value outside allowed range.");
		} else {
			mCurrentValue = value;
		}
	}
	
	public void draw() {
		GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
		graphicsContext.save();
		double xScale = mCanvas.getWidth()/logicalWidth;
		double yScale = mCanvas.getHeight()/logicalHeight;
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(2, 2);
		if (segmentStates[0] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
	}

}
