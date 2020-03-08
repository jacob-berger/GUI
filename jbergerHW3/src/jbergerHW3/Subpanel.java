package jbergerHW3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Optional;
import java.util.Random;

public class Subpanel extends Region {

    Label mText;
    Rectangle mBackground;
    StringProperty pasteProperty;
    double red, green, blue;
    Clipboard clipboard;

    public Subpanel(String text) {
        this.mText = new Label(text);
        this.mBackground = new Rectangle();
        onChangeColor();
        Color color = new Color(red, green, blue, 1.0);
        mBackground.setFill(color);
        mBackground.widthProperty().bind(this.widthProperty());
        mBackground.heightProperty().bind(this.heightProperty());
        getChildren().addAll(mBackground, mText);
        pasteProperty = new SimpleStringProperty();
        this.setOnMouseClicked(actionEvent -> contextMenu(actionEvent));

    }

    public StringProperty pasteProperty() {return pasteProperty;}

    public Label getText() {
        return mText;
    }

    public void setText(String text) {
        mText.setText(text);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double layoutWidth = super.getWidth();
        double layoutHeight = super.getHeight();

        mText.setMinWidth(50);
        mText.setMinHeight(50);

        double labelWidth = mText.getWidth();
        double labelHeight = mText.getHeight();

        mText.relocate(layoutWidth / 2 - (labelWidth / 2), layoutHeight / 2 - (labelHeight / 2));
    }

    private void contextMenu(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            Label label = new Label("ContextMenu");
            MenuItem changeStringItem = new MenuItem("Change String");
            changeStringItem.setOnAction(actionEvent -> onChangeString());
            MenuItem changeColorItem = new MenuItem("Change Color");
            changeColorItem.setOnAction(actionEvent -> onChangeColor());
            MenuItem copySettingsItem = new MenuItem("Copy Settings");
            copySettingsItem.setOnAction(actionEvent -> onCopySettings());
            MenuItem pasteSettingsItem = new MenuItem("Paste Settings");
            pasteSettingsItem.setOnAction(actionEvent -> onPasteSettings());
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(changeStringItem, changeColorItem, copySettingsItem, pasteSettingsItem);

            TilePane tilePane = new TilePane(label);

            label.setContextMenu(contextMenu);
            contextMenu.show(this, e.getScreenX(), e.getScreenY());
        }
    }

    private void onChangeString() {
        try {
            Dialog<String> dialog = new TextInputDialog();
            dialog.setHeaderText("Change String");
            dialog.setTitle("Change String");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                mText.setText(result.get());
            }

        } catch (Exception e) {
            System.out.println("Exception caught\n" + e.getMessage());
        }
    }

    private void onChangeColor() {
        Random random = new Random();
        red = random.nextDouble();
        green = random.nextDouble();
        blue = random.nextDouble();

        Color color = new Color(red, green, blue, 1);
        mBackground.setFill(color);
    }

    private void onCopySettings() {
//        DataFormat dataFormat = new DataFormat("HW3Data");
//        clipboard = Clipboard.getSystemClipboard();
//        clipboard.clear();
//        ClipboardContent content = new ClipboardContent();
//        content.put(jbergerHW3.getDataFormat(), red + " " + green + " " + blue + " " + mText.getText());
//        clipboard.setContent(content);
//
//        System.out.println("Copied successfully!");
//        System.out.println(red + " " + green + " " + blue + " " + mText.getText());

        jbergerHW3.copySettings(red + " " + green + " " + blue + " " + mText.getText());
    }

    private void onPasteSettings() {
//        DataFormat dataFormat = new DataFormat("HW3Data");
        clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            String returnedSettings = (String) clipboard.getContent(jbergerHW3.getDataFormat());
//            red = Double.parseDouble(content[0]);
//            green = Double.parseDouble(content[1]);
//            blue = Double.parseDouble(content[2]);
//
//            String text = "";
//            for (int ix = 3; ix < content.length; ix++) {
//                text += content[ix] + " ";
//            }
//            text.trim();
//            mText.setText(text);
            mBackground.setFill(new Color(red, green, blue, 1.0));
            System.out.println("\nShould have pasted...");
//            System.out.println(red + " " + green + " " + blue + " " + text);
        } else {
            System.out.println("Nothing to paste from the clipboard.");
        }
    }
}
