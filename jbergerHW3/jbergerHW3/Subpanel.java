package jbergerHW3;

import com.sun.codemodel.internal.util.UnicodeEscapeWriter;
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
    public StringProperty pasteProperty;
    int red, green, blue;
    Clipboard clipboard;
    public static DataFormat dataFormat = new DataFormat("JBergerHW3DataFormat");

    public Subpanel(String text) {
        this.mText = new Label(text);
        this.mBackground = new Rectangle();
        onChangeColor();
        Color color = Color.rgb(red, green, blue);
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
        red = random.nextInt(256);
        green = random.nextInt(256);
        blue = random.nextInt(256);

        Color color = Color.rgb(red, green, blue);
        mBackground.setFill(color);
    }

    private void onCopySettings() {
        clipboard = Clipboard.getSystemClipboard();
        clipboard.clear();
        ClipboardContent content = new ClipboardContent();
        content.put(dataFormat, red + " " + green + " " + blue + " " + mText.getText());
        clipboard.setContent(content);

//        System.out.println("Copied successfully!");
//        System.out.println(red + " " + green + " " + blue + " " + mText.getText());
    }

    private void onPasteSettings() {
        clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasContent(dataFormat)) {
            try {
                String returnedSettings = (String) clipboard.getContent(dataFormat);
                String[] content = returnedSettings.split(" ");
                red = Integer.parseInt(content[0]);
                green = Integer.parseInt(content[1]);
                blue = Integer.parseInt(content[2]);

                String text = "";
                for (int ix = 3; ix < content.length; ix++) {
                    text += content[ix] + " ";
                }
                text.trim();
                mText.setText(text);
                mBackground.setFill(Color.rgb(red, green, blue));
                pasteProperty.setValue("Pasted successfully.");
            } catch (NumberFormatException e) {
                pasteProperty.setValue("Paste failed. Couldn't be cast to a String.");
            }
        } else if (clipboard.hasString()) {
            mText.setText(clipboard.getString().trim());
            pasteProperty.setValue("Pasted successfully.");
        } else {
//            System.out.println("Nothing to paste from the clipboard.");
            pasteProperty.setValue("Nothing to paste from the clipboard.");
        }
    }
}
