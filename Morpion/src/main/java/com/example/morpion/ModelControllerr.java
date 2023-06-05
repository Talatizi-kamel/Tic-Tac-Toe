package com.example.morpion;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelControllerr {
    @FXML
    Pane pane;
    @FXML
    Button buttonSupprimer;

    @FXML
    VBox vBox;
    @FXML
    public List<HBox> list (String temp ){
        File repertoire = new File(temp);
        String[] listefichiers = repertoire.list();
        ArrayList<HBox> hboxl = new ArrayList<HBox>();
        for (String nom : listefichiers) {
            HBox hBox = new HBox();
            Text text = new Text(nom);
            CheckBox checkBox = new CheckBox();
            hBox.getChildren().addAll(text,checkBox);
            hboxl.add(hBox);
        }
        return hboxl;
    }

    @FXML
    public void repvide(Pane pane) throws FileNotFoundException {
        pane.getChildren().clear();
        Button button = new Button("Lancer");
        button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("mode-jeu-view.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(parent,600, 400);
            stage.setTitle("Mode de jeux ");
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        });

        Button saveButton = new Button("Enregistrer");
        Button Accueil = new Button("Accueil");
        Accueil.setStyle("-fx-background-color: #2053c6;");
        Accueil.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Acceuil-view.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(parent,800, 600);
            stage.setTitle("TicTacToe");
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        });


        // Définir les propriétés de redimensionnement de l'image pour qu'elle remplisse tout le Pane
        button.layoutXProperty().bind(pane.widthProperty().subtract(button.prefWidth(-1)).divide(2));
        button.layoutYProperty().bind(pane.heightProperty().subtract(button.prefHeight(-1)).divide(2));
        pane.getChildren().setAll(button);



        Text text = new Text("Veuillez lancer Entraînement de l'IA pour charger les models ");
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);


        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), text);
        transition.setByY(-50);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();

        // Positionner le texte en haut et centrer horizontalement
        text.setLayoutY(text.getFont().getSize());
        // Centrer le texte verticalement et horizontalement
        text.layoutXProperty().bind(pane.widthProperty().subtract(text.prefWidth(-1)).divide(2));
        text.layoutYProperty().bind(pane.heightProperty().subtract(text.prefHeight(-1)).divide(4));
        pane.getChildren().addAll(text,Accueil);
    }
    @FXML
    public void onclickmod() throws FileNotFoundException {
        // Création d'un VBox contenant des HBox pour chaque fichier
        File repertoire = new File("./resources/models/");
        String[] listefichiers = repertoire.list();
        if(listefichiers.length==0){
            repvide(pane);
        }else {

            for (String nom : listefichiers) {
                HBox hBox = new HBox();
                CheckBox checkBox = new CheckBox();
                Label label = new Label(nom);
                label.setTextFill(Color.WHITE);

                hBox.setSpacing(10);

                Image image = new Image(new FileInputStream("./resources/images/icon.jpg"));
                ImageView icon = new ImageView(image);
                icon.setFitWidth(32);
                icon.setFitHeight(32);
                label.setGraphic(icon);
                label.setContentDisplay(ContentDisplay.LEFT);

                hBox.getChildren().addAll(checkBox, label);
                vBox.getChildren().addAll(hBox);
            }

            // Création d'un bouton Supprimer


            // Gestionnaire d'événements pour le bouton Supprimer
            buttonSupprimer.setOnMouseClicked(event -> {
                ObservableList<Node> children = vBox.getChildren();
                boolean anySelected = false;
                for (Node child : children) {
                    if (child instanceof HBox) {
                        CheckBox checkBox = (CheckBox) ((HBox) child).getChildren().get(0);
                        if (checkBox.isSelected()) {
                            anySelected = true;
                            break;
                        }
                    }
                }

                if (anySelected) {
                    // Si au moins un fichier a été sélectionné, on affiche la boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Voulez-vous vraiment supprimer les fichiers sélectionnés ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Si l'utilisateur a confirmé, on supprime les fichiers sélectionnés
                        for (Node child : children) {
                            if (child instanceof HBox) {
                                CheckBox checkBox = (CheckBox) ((HBox) child).getChildren().get(0);
                                if (checkBox.isSelected()) {
                                    String nomFichier = ((Label) ((HBox) child).getChildren().get(1)).getText();
                                    File fichierASupprimer = new File(repertoire, nomFichier);
                                    fichierASupprimer.delete();
                                }
                            }
                        }
                    }
                }

                if (list("./resources/models/").size() == 0) {
                    try {
                        repvide(pane);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                vBox.getChildren().clear();

                String[] listefichiers1 = repertoire.list();
                if (listefichiers1.length == 0) {
                    try {
                        repvide(pane);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    for (String nom : listefichiers1) {
                        HBox hBox = new HBox();
                        CheckBox checkBox = new CheckBox();
                        Label label = new Label(nom);
                        label.setTextFill(Color.WHITE);

                        hBox.setSpacing(10);

                        Image image = null;
                        try {
                            image = new Image(new FileInputStream("./resources/images/icon.jpg"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        ImageView icon = new ImageView(image);
                        icon.setFitWidth(32);
                        icon.setFitHeight(32);
                        label.setGraphic(icon);
                        label.setContentDisplay(ContentDisplay.LEFT);

                        hBox.getChildren().addAll(checkBox, label);
                        vBox.getChildren().addAll(hBox);

                    }
                }

            });

            // Ajout du VBox et du bouton Supprimer au Pane


            pane.requestLayout();

        }

    }


    @FXML
    public void Accueil(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Acceuil-view.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent,800, 600);
        stage.setScene(scene);
        stage.show();

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void initialize() throws FileNotFoundException {
        onclickmod();

        // Centrer le VBox dans le Pane
        double vboxWidth = vBox.prefWidth(-1);
        double vboxHeight = vBox.prefHeight(-1);
        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();
        double x = (paneWidth - vboxWidth) / 2;
        double y = (paneHeight - vboxHeight) / 2;
        vBox.setLayoutX(x);
        vBox.setLayoutY(y);

// Redimensionner le VBox si le Pane est redimensionné
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newX = (newValue.doubleValue() - vboxWidth) / 2;
            vBox.setLayoutX(newX);
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newY = (newValue.doubleValue() - vboxHeight) / 2;
            vBox.setLayoutY(newY);
        });
    }
}
