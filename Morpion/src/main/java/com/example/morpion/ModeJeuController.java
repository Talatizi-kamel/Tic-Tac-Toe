package com.example.morpion;



import ai.MultiLayerPerceptron;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

    public class ModeJeuController {

        Parent root;
        ProgressBarController progressBarController;

        String conf [][] = new String[3][4];
        String fichier;
        MultiLayerPerceptron net;

        public static String NomModel;
        @FXML
        TextField text;

        @FXML
        RadioButton moyen;
        public static String Pesudo;


        @FXML
        ToggleGroup group;

        @FXML
        public String [][] nomModel(String fichier) throws FileNotFoundException {
            File file = new File("./resources/" + fichier);
            Scanner scanner = new Scanner(file);


            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(":");
                for (int i = 0; i < values.length; i++) {
                    conf[row][i] = values[i];
                }
                row++;
            }
            return conf;
        }

        @FXML
        public void play1(Event event) throws IOException {

            FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Humain-vsIa-view.fxml"));
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
        public void play(Event event) throws IOException {
            setnom();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("progressbar-view.fxml"));
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            progressBarController = loader.getController();

            RadioButton radioButton = (RadioButton) group.getSelectedToggle();

            String ModJeu;
            ModJeu = radioButton.getText();
            //System.out.println(conf[1][1]);

            if(ModJeu.equals("Moyen")){
                conf = nomModel("config.txt");
                fichier = "model_"+conf[1][1]+"_"+conf[1][2]+"_"+conf[1][3]+".srl";
                System.out.println(conf[1][1]);
                progressBarController.h = Integer.parseInt(conf[1][1]);

                progressBarController.lr = Double.parseDouble(conf[1][2]);
                progressBarController.l= Integer.parseInt(conf[1][3]);
                progressBarController.fichier=fichier;
                net =  MultiLayerPerceptron.load("resources/models/"+ fichier);
                NomModel = fichier;
                testModel(fichier,event);


            }

            if(ModJeu.equals("Facile")){
                conf = nomModel("config.txt");
                fichier = "model_"+conf[0][1]+"_"+conf[0][2]+"_"+conf[0][3]+".srl";
                System.out.println(conf[0][1]);
                progressBarController.h = Integer.parseInt(conf[0][1]);
                progressBarController.lr = Double.parseDouble(conf[0][2]);
                progressBarController.l= Integer.parseInt(conf[0][3]);
                progressBarController.fichier=fichier;
                net =  MultiLayerPerceptron.load("resources/models/"+ fichier);
                NomModel = fichier;
                testModel(fichier,event);


            }

            if(ModJeu.equals("Difficile")){
                conf = nomModel("config.txt");
                fichier = "model_"+conf[2][1]+"_"+conf[2][2]+"_"+conf[2][3]+".srl";
                //System.out.println(conf[0][1]);
                progressBarController.h = Integer.parseInt(conf[2][1]);

                progressBarController.lr = Double.parseDouble(conf[2][2]);
                progressBarController.l= Integer.parseInt(conf[2][3]);
                progressBarController.fichier=fichier;
                net =  MultiLayerPerceptron.load("resources/models/"+ fichier);
                NomModel = fichier;
                testModel(fichier,event);

            }

            if(ModJeu.equals(null)){
                conf = nomModel("config.txt");
                fichier = "model_"+conf[2][1]+"_"+conf[2][2]+"_"+conf[2][3]+".srl";
                //System.out.println(conf[0][1]);
                progressBarController.h = Integer.parseInt(conf[2][1]);

                progressBarController.lr = Double.parseDouble(conf[2][2]);
                progressBarController.l= Integer.parseInt(conf[2][3]);
                progressBarController.fichier=fichier;
                net =  MultiLayerPerceptron.load("resources/models/"+ fichier);
                NomModel = fichier;
                testModel(fichier,event);

            }



        }

        @FXML
        public void initialize(){
            moyen.setSelected(true);
        }
        @FXML
        public void setnom() {

            if ((text.getText() != "")) {
                HumaiVsIaController.nomx = text.getText();
            } else {
                HumaiVsIaController.nomx = "Player 1 ";
            }
        }
        @FXML
        public void testModel(String filename,Event event) throws IOException {
            File file;
            file = new File("resources/models/"+ filename);
            boolean created = false;
            try {
                created = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (created) {

                progressBarController.start();
                Stage stage = new Stage();
                stage.setTitle("chargement de l IA");
                stage.setScene(new Scene(root));
                stage.show();
                progressBarController.probar.progressProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() >= 0.99999) {

                        stage.close();
                    }
                });

            } else {
                play1(event);
            }
        }
    }

