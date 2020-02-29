package jbergerHW3;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Subpanel extends Region {

    Label mText;
    Rectangle mBackground;

    public Subpanel(String text, Paint backgroundColor) {
        this.mText = new Label(text);
        this.mBackground = new Rectangle();
        mBackground.setFill(backgroundColor);
        mBackground.widthProperty().bind(this.widthProperty());
        mBackground.heightProperty().bind(this.heightProperty());
        mText.setPrefWidth(300);
        mText.setPrefHeight(300);
        getChildren().addAll(mBackground, mText);
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public Label getText() {
        return mText;
    }

    @Override
    protected void layoutChildren() {
        double layoutWidth = super.getWidth();
        double layoutHeight = super.getHeight();

        mText.setMinWidth(50);
        mText.setMinHeight(50);

        double labelWidth = mText.getWidth();
        double labelHeight = mText.getHeight();

        System.out.println(layoutWidth + " " + layoutHeight + " " + labelWidth + " " + labelHeight);
        mText.relocate(layoutWidth / 2 - (labelWidth / 2), layoutHeight / 2 - (labelHeight / 2));
        }
}
