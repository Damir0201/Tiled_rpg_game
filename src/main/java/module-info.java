module com.example.rpg_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;


    opens com.example.rpg_gui to javafx.fxml;
    opens com.example.rpg_gui.Systems to javafx.fxml;
    exports com.example.rpg_gui;
    opens com.example.rpg_gui.Characters to javafx.fxml;
    opens com.example.rpg_gui.map to javafx.fxml;
}