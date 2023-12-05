package com.zhykh.imgtoascii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class mainFrame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainFrame.fxml")));
        stage.setTitle("ImgToASCII");
        stage.setScene(new Scene(root, 1800, 1000));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}