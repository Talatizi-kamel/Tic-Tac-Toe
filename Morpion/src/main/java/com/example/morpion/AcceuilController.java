package com.example.morpion;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class AcceuilController {

    @FXML
    Button button;
    @FXML
    AnchorPane anchorpane;
    @FXML
    Button button2;


    public void onclickSetting(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Settings-view.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent,600, 400);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.show();

        // Ferme la fenêtre actuelle
        Stage currentStage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        currentStage.close();
    }

    public void onclickModel(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Model-view.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent,700, 400);
        stage.setTitle("Models");
        stage.setScene(scene);
        stage.show();

        // Ferme la fenêtre actuelle
        Stage currentStage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        currentStage.close();
    }

    public void initialize() {
        // Initialisation des éléments de l'interface utilisateur ici
        button2.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(button2, Priority.ALWAYS);

        VBox container = new VBox(button2);
        container.setFillWidth(true);
        anchorpane.getChildren().add(container);
        // Positionne le VBox en bas à droite de l'AnchorPane
        anchorpane.setBottomAnchor(container, 10.0);
        anchorpane.setRightAnchor(container, 10.0);


        button.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(button, Priority.ALWAYS);

        VBox container1 = new VBox(button);
        container1.setFillWidth(true);
        anchorpane.getChildren().add(container1);
        // Positionne le VBox en bas à droite de l'AnchorPane
        anchorpane.setBottomAnchor(container1, 10.0);
        anchorpane.setLeftAnchor(container1, 10.0);
        ;
    }
}
