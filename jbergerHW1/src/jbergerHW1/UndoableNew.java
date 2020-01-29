package jbergerHW1;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class UndoableNew extends AbstractUndoableEdit {
	
	Canvas mCanvas;
	WritableImage mBackup;
	
	public UndoableNew(Canvas canvas) {
		mBackup = canvas.snapshot(null, null);
		mCanvas = canvas;
	}

	@Override
	public void undo() throws CannotUndoException {
		//Maybe?
		GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
		graphicsContext.drawImage(mBackup, 0, 0);
	}

	@Override
	public String getPresentationName() {
		return "New";
	}
	
	

}
