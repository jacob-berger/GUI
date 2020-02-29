package jbergerLab9;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line implements Serializable {
	
	private Point2D mStart, mEnd;
	private int mWidth;
	private Color mColor;
	public ArrayList<Line> lines;
	
	public Line(Point2D start, Point2D end, int width, Color color) {
		if (start == null) throw new NullPointerException("Starting point cannot be null.");
		if (end == null) throw new NullPointerException("Ending point cannot be null.");
		if (color == null) throw new NullPointerException("Color cannot be null.");
		
		mStart = start;
		mEnd = end;
		mWidth = width;
		mColor = color;
	}
	
	public void draw(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(mColor);
		gc.strokeLine(mStart.getX(), mStart.getY(), mEnd.getX(), mEnd.getY());
	}
	
	public void AddLine(Line line) {
		if (lines == null) {
			lines = new ArrayList<Line>();
		}
		lines.add(line);
	}

	public Point2D getStart() {
		return mStart;
	}

	public Point2D getEnd() {
		return mEnd;
	}
}
