module com.zhykh.imgtoascii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.zhykh.imgtoascii to javafx.fxml;
    exports com.zhykh.imgtoascii;
}