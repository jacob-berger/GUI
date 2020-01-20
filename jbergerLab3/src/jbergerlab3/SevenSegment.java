package jbergerlab3;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
		mCanvas = new Canvas(200, 200);
		getChildren().add(mCanvas);
		mCurrentValue = 10;
		setPrefSize(logicalWidth * 1000, logicalHeight * 1000);
	}
	
	public SevenSegment(int value) {
		this();
		if (value < 0 || value > 10) {
			throw new IllegalArgumentException("Initial value outside allowed range.");
		} else {
			mCurrentValue = value;
		}
	}
	
	protected void layoutChildren() {
		double x = getWidth();
		double y = getHeight();
		
		if (x > y) {
			mCanvas.setWidth(x * 14 / 22);
			mCanvas.setHeight(y);
		} else {
			mCanvas.setHeight(x * 22 / 14);
			mCanvas.setWidth(y);
		}
		
		layoutInArea(mCanvas, 0, 0, x, y, 0, HPos.CENTER, VPos.CENTER);
	}

	public void draw() {
		GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, getWidth(), getHeight());
		
		//top
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
		graphicsContext.restore();
		
		//top right
		graphicsContext.save();
		if (segmentStates[1] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(10, 2);
		graphicsContext.rotate(90);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
		
		//bottom right
		graphicsContext.save();
		if (segmentStates[2] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(10, 10);
		graphicsContext.rotate(90);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
		
		//bottom
		graphicsContext.save();
		if (segmentStates[3] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(2, 18);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
		
		//bottom left
		graphicsContext.save();
		if (segmentStates[4] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(2, 10);
		graphicsContext.rotate(90);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
		
		//top left
		graphicsContext.save();
		if (segmentStates[5] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(2, 2);
		graphicsContext.rotate(90);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
		
		//center
		graphicsContext.save();
		if (segmentStates[6] == true) {
			graphicsContext.setFill(onPaint);
		} else {
			graphicsContext.setFill(offPaint);
		}
		graphicsContext.scale(xScale, yScale);
		graphicsContext.translate(2, 10);
		graphicsContext.fillPolygon(xCoordinates, yCoordinates, 6);
		graphicsContext.restore();
	}

}
