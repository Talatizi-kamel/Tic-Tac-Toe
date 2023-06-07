package com.example.morpion;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class pesudoController {
    @FXML
    TextField nomX;

    @FXML
    TextField nomO;







    @FXML
    public void play(Event event) throws IOException {

        setnom();
        FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("HumainVsHumain-view.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void setnom(){

        if((nomO.getText()!="")&&(nomX.getText()!="")) {
            HumainVsHumainController.nomo = nomO.getText();
            HumainVsHumainController.nomx = nomX.getText();
        }else{
            HumainVsHumainController.nomo = "player1";
            HumainVsHumainController.nomx = "player2";
        }

        System.out.println(nomX.getText());
    }

}
