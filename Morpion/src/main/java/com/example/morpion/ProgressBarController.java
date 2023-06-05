package com.example.morpion;


import ai.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.util.HashMap;

import static ai.Test.*;

    public class ProgressBarController {


        public  int h;
        public  double lr;
        public  int l;
        public  String fichier;



        double epochs = 10000;
        double error = 0.0;
        boolean verbose = true;

        @FXML
        private Button  button;

        @FXML
        ProgressIndicator probar;
        @FXML
        TextField textFiled = new TextField();

        private Task<Void> task;
        HashMap<Integer, Coup> mapTrain = loadGames("./resources/dataset/Tic_tac_initial_results.csv");

        @FXML
        private ProgressBar progressbar;



        public void initialize() {
            task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    for (int i = 0; i < epochs +1; i++) {
                        Coup c = null;
                        while (c == null)
                            c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));
                        error += push().backPropagate(c.in, c.out);
                        if (i % 1000 == 0 && verbose) updateMessage("Error at step " + i + " is " + (error / (double) i));
                        updateProgress(i + 1, epochs);
                    }
                    if (verbose) {
                        updateMessage("Learning completed!");
                        push().save("resources/models/" + fichier);
                    }
                    return null;
                }
            };
            probar.progressProperty().bind(task.progressProperty());
            // textField.textProperty().bind(task.messageProperty());
            task.messageProperty().addListener((observableValue, s, t1) -> {
                textFiled.setText(t1);
            });

        }

        protected MultiLayerPerceptron push() {
            //
            // LOAD DATA ...
            //
            HashMap<Integer, Coup> coups = loadGames("./resources/dataset/Tic_tac_initial_results.csv");
            saveGames(coups, "./resources/train_dev_test/", 0.7);
            //
            // LOAD CONFIG ...
            //
            ConfigFileLoader cfl = new ConfigFileLoader();
            cfl.loadConfigFile("./resources/config.txt");
            Config config = cfl.get("F");
            System.out.println("Test.main() : "+config);
            //
            //TRAIN THE MODEL ...
            //
            //
            int size = 9;
            try {
                if ( verbose ) {
                    System.out.println();
                    System.out.println("START TRAINING ...");
                    System.out.println();
                }
                //
                //      int[] layers = new int[]{ size, 128, 128, size };
                int[] layers = new int[l+2];
                layers[0] = size ;
                for (int i = 0; i < l; i++) {
                    layers[i+1] = h ;
                }
                layers[layers.length-1] = size ;
                //
                MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

                if ( verbose ) {
                    System.out.println("---");
                    System.out.println("Load data ...");
                    System.out.println("---");
                }
                return net ;
            }
            catch (Exception e) {
                System.out.println("Test.learn()");
                e.printStackTrace();
                System.exit(-1);
            }
            return null;
        }
        @FXML
        public  void start(){
            new Thread(task).start();

        }
    }
