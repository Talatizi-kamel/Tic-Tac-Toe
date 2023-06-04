package com.example.morpion;



import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

    public class SettingsController {

        @FXML
        Pane pane1;

        @FXML
        GridPane gridPane;

        @FXML
        AnchorPane anchorPane;
        static String conf [][] = new String[3][4];
        @FXML
        public void setting() throws FileNotFoundException {
            File file = new File("./resources/config.txt");
            Scanner scanner = new Scanner(file);



            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(":");
                for (int i = 0; i < values.length; i++) {
                    TextField textField = new TextField(values[i]);
                    gridPane.add(textField, i, row);
                    conf[row][i] = values[i];
                }

                row++;
            }


            Button saveButton = new Button("Enregistrer");
            saveButton.setStyle("-fx-background-color: #4CAF50;");
            Button Accueil = new Button("Accueil");
            Accueil.setStyle("-fx-background-color: #2053c6;");
            Accueil.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Accueil-view.fxml"));
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
            saveButton.setOnAction(event -> {
                // Créer une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Êtes-vous sûr de vouloir enregistrer les paramètres?");
                alert.setContentText("Cliquez sur OK pour enregistrer ou Annuler pour annuler.");

                // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                alert.showAndWait().ifPresent(result -> {
                    if (result == ButtonType.OK) {
                        try {
                            PrintWriter writer = new PrintWriter(file);
                            int cpt = 0;
                            String temp = "";
                            for (Node node : gridPane.getChildren()) {
                                if (node instanceof TextField) {
                                    String value = ((TextField) node).getText();
                                    temp = temp + value + ":";
                                    cpt++;
                                    if (cpt % 4 == 0) {
                                        int lastIndex = temp.lastIndexOf(":");
                                        if (lastIndex >= 0) {
                                            temp = temp.substring(0, lastIndex) + temp.substring(lastIndex + 1);
                                        }
                                        writer.print(temp);
                                        writer.print("\n");
                                        temp = "";
                                    }
                                }
                            }
                            writer.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });

            Button button = new Button("Parametre par defaut");
            button.setStyle("-fx-background-color: #4CAF50;");
            button.setOnAction(event -> {
                try {
                    pardefaut();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            anchorPane.setRightAnchor(button,0.0);
            anchorPane.setBottomAnchor(button, 0.0);
            gridPane.add(saveButton, 0, row, 2, 1);
            anchorPane.getChildren().addAll(Accueil,button);



        }

        @FXML
        public void Accueil(Event event) throws IOException {
            FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Accueil-view.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }

        @FXML
        public void initialize() throws FileNotFoundException {
            setting();
            //pour centrer mon gridpane
            anchorPane.setTopAnchor(gridPane, (anchorPane.getHeight() - gridPane.getHeight()) / 2.0);
            AnchorPane.setBottomAnchor(gridPane, (anchorPane.getHeight() - gridPane.getHeight()) / 2.0);
            AnchorPane.setLeftAnchor(gridPane, (anchorPane.getWidth() - gridPane.getWidth()) / 2.0);
            AnchorPane.setRightAnchor(gridPane, (anchorPane.getWidth() - gridPane.getWidth()) / 2.0);

        }

        @FXML
        public void pardefaut() throws FileNotFoundException {
            try {
                File sourceFile = new File("./resources/config_defaut.txt");
                File destinationFile = new File("./resources/config.txt");
                BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
                writer.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le fichier a été copié avec succès !");
                alert.setHeaderText(null);
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Une erreur est survenue lors de la copie du fichier : " + e.getMessage(), ButtonType.OK);
                alert.setHeaderText(null);
                alert.showAndWait();
            }

            File file = new File("./resources/config.txt");
            Scanner scanner = new Scanner(file);
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(":");
                for (int i = 0; i < values.length; i++) {
                    TextField textField = new TextField(values[i]);
                    gridPane.add(textField, i, row);
                    conf[row][i] = values[i];
                }

                row++;
            }
        }

    }

