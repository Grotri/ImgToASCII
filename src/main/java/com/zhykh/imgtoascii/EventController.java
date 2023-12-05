package com.zhykh.imgtoascii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class EventController {
    @FXML
    private Button selectImgButton;
    @FXML
    private ChoiceBox comprLvl;
    @FXML
    private Label currPix;
    @FXML
    private TextFlow imagePane;
    private final Stage soloWindow = new Stage();
    private final Stage batchWindow = new Stage();
    private BufferedImage img;
    private int w;
    private int h;
    @FXML
    private void soloModeWindow() throws IOException {
        openSoloWindow(soloWindow);
    }
    private void openSoloWindow(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("soloOptions.fxml")));
        stage.setTitle("Solo mode options");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }
    @FXML
    private void selectImg() {
        img = getImg(selectImgButton, soloWindow);
        w = (int)(Math.floor((double)img.getWidth() / Integer.parseInt(comprLvl.getValue().toString())));
        h = (int)Math.floor((double)img.getHeight() / Integer.parseInt(comprLvl.getValue().toString()));
        currPix.setText("Current image ratio: "+ w + "x" + h + " symbols");
    }
    private BufferedImage getImg(Button selectImgButton, Stage stage){
        FileChooser imgChoose = new FileChooser();
        imgChoose.setTitle("Choose image");
        imgChoose.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        File imgFile = imgChoose.showOpenDialog(stage);
        selectImgButton.setText(imgFile.getName());
        try {
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void updateCurrPix() {
        if (img != null) {
            w = (int)(Math.floor(img.getWidth() / (double)Integer.parseInt(comprLvl.getValue().toString())));
            h = (int)Math.floor(img.getHeight() / (double)Integer.parseInt(comprLvl.getValue().toString()));
            currPix.setText("Current image ratio: "+ w + "x" + h + " symbols");
        }
    }
    @FXML
    private void soloPressed() {
//        printImage(0);

        Text test1 = new Text("$$$$$$ | ");
        test1.setFill(Color.RED);
        Text test2 = new Text("...... | ");
        test2.setFill(Color.BLUE);
        imagePane.getChildren().addAll(test1, test2);
    }
//    @FXML
//    private batchPressed() {
//
//    }
    private void printImage(int mode) {
        if (mode == 0) {
            Deque<String> chars = imageProcessing.getChars(img, Integer.parseInt(comprLvl.getValue().toString()));

        }
    }
}