package jbergerHW1;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class UndoableScribble extends AbstractUndoableEdit {
	
	Canvas mCanvas;
	WritableImage mUndo, mRedo;
	
	public UndoableScribble(Canvas canvas) {
		mUndo = canvas.snapshot(null, null);
		mCanvas = canvas;	
	}

	@Override
	public void undo() throws CannotUndoException {
		GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
		mRedo = graphicsContext.getCanvas().snapshot(null, null);
		graphicsContext.drawImage(mUndo, 0, 0);
	}

	@Override
	public void redo() throws CannotRedoException {
		GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
		mUndo = graphicsContext.getCanvas().snapshot(null, null);
		graphicsContext.drawImage(mRedo, 0, 0);
	}

	@Override
	public boolean canRedo() {
		if (mRedo != null) {
			return true;
		}
		
		return false;
	}

}
