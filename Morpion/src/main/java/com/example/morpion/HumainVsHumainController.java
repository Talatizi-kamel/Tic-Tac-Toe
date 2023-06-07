package com.example.morpion;


import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

    public class HumainVsHumainController {



        public static String nomx;
        public static String nomo;

        @FXML
        Label play;
        @FXML
        Label playy;
        boolean isGameEnds = false;
        boolean isGameWon = false;
        boolean isFirstPlayerTurn = true;
        int XOCounter = 0;
        Color xColor = Color.WHITE;
        Color oColor = Color.WHITE;
        @FXML
        Label firstPlayerScore,secondPlayerScore, playerTurn;
        @FXML
        Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
        @FXML
        Button exitId,resetId;
        @FXML
        Line line;

        @FXML
        public void setpsudo(){

        }
        @FXML
        public void Accueil(Event event) throws IOException {
            FXMLLoader loader = new FXMLLoader(SettingsController.class.getResource("Accueil-view.fxml"));
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
        public  void btnVersSoit(){
            startNewGame();
        }

        // Handle Button click for the game board
        @FXML
        public void btn0Click(){
            actionPerformed(btn0);
        }
        @FXML
        public void btn1Click(){
            actionPerformed(btn1);
        }
        @FXML
        public void btn2Click(){
            actionPerformed(btn2);
        }
        @FXML
        public void btn3Click(){
            actionPerformed(btn3);
        }
        @FXML
        public void btn4Click(){
            actionPerformed(btn4);
        }
        @FXML
        public void btn5Click(){
            actionPerformed(btn5);
        }
        @FXML
        public void btn6Click(){actionPerformed(btn6);}
        @FXML
        public void btn7Click(){
            actionPerformed(btn7);
        }
        @FXML
        public void btn8Click(){
            actionPerformed(btn8);
        }

        public HumainVsHumainController(){}

        @FXML
        public void initialize() {

            playerTurn.setMaxWidth(Double.MAX_VALUE);
            AnchorPane.setLeftAnchor(playerTurn, 0.0);
            AnchorPane.setRightAnchor(playerTurn, 0.0);
            playerTurn.setAlignment(Pos.CENTER);
            playy.setText(nomx);
            play.setText(nomo);
            System.out.println(nomx);
            timer();

        }

        // Start a new Game
        private void startNewGame() {

            stopTimer(timeline);
            timer();
            isGameEnds = false;
            isGameWon = false;
            setCurrentTurn();

            btn0.setText("");btn0.setStyle(" -fx-padding :  0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn1.setText(""); btn1.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn2.setText(""); btn2.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn3.setText(""); btn3.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn4.setText(""); btn4.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn5.setText(""); btn5.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn6.setText(""); btn6.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn7.setText(""); btn7.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");
            btn8.setText(""); btn8.setStyle(" -fx-padding : 0; -fx-border-style : none; -fx-background-color: none; -fx-cursor: hand;");

        }

        private void actionPerformed(Button clickedButton) {

            if( isGameEnds == false && clickedButton.getText().equals("") ) {

                if(isFirstPlayerTurn) {
                    clickedButton.setTextFill(xColor);
                    clickedButton.setText("X");

                }
                else {
                    clickedButton.setTextFill(oColor);
                    clickedButton.setText("O");

                }
                XOCounter++;
                setCurrentTurn();
                checkIfGameEnds();
                isFirstPlayerTurn = !isFirstPlayerTurn;

            }
        }

        // Check if the game is ended
        private void checkIfGameEnds() {
            String t00 = btn0.getText();
            String t01 = btn1.getText();
            String t02 = btn2.getText();
            String t10 = btn3.getText();
            String t11 = btn4.getText();
            String t12 = btn5.getText();
            String t20=  btn6.getText();
            String t21 = btn7.getText();
            String t22 = btn8.getText();

            if (t00.equals(t01) && t00.equals(t02) && !t00.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn0, btn1, btn2);
                setWinner();
            }
            if (t10.equals(t11) && t10.equals(t12) && !t10.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn3, btn4, btn5);
                setWinner();
            }
            if (t20.equals(t21) && t20.equals(t22) && !t20.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn6, btn7, btn8);
                setWinner();
            }
            if (t00.equals(t10) && t00.equals(t20) && !t00.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn0, btn3, btn6);
                setWinner();
            }
            if (t01.equals(t11) && t01.equals(t21) && !t01.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn1, btn4, btn7);
                setWinner();
            }
            if (t02.equals(t12) && t02.equals(t22) && !t02.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn2, btn5, btn8);
                setWinner();
            }
            if (t00.equals(t11) && t00.equals(t22) && !t00.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn0, btn4, btn8);
                setWinner();
            }
            if (t02.equals(t11) && t02.equals(t20) && !t02.equals("")) {
                isGameEnds = true;
                isGameWon = true;
                colorBackgroundWinnerButtons(btn2, btn4, btn6);
                setWinner();
            }

            // Check if all cells are played
            if( XOCounter >= 9) {
                isGameEnds = true;
                isFirstPlayerTurn = true;
                XOCounter = 0;
                stopTimer(timeline);


            }

            // Check if the game is ended
            if(isGameEnds == true) {

                // Check if the game is Won
                if(isGameWon) {
                    if(isFirstPlayerTurn)
                        firstPlayerScore.setText(Integer.valueOf(firstPlayerScore.getText()) + 1 + "");
                    else
                        secondPlayerScore.setText(Integer.valueOf(secondPlayerScore.getText()) + 1 + "");
                    XOCounter = 0;

                    // Draw Game
                }else {
                    playerTurn.setText("Match Null");
                    playerTurn.setTextFill(Color.WHITE);
                    // Déclencher la transition
                    FadeTransition transitionMatchNul = new FadeTransition(Duration.seconds(5), playerTurn);
                    transitionMatchNul.setFromValue(0.0);
                    transitionMatchNul.setToValue(1.0);
                    transitionMatchNul.setAutoReverse(true);
                    transitionMatchNul.setCycleCount(Animation.INDEFINITE);
                    transitionMatchNul.play();
                    playerTurn.setVisible(true);
                }
            }
        }


        // Color the three cells when win
        private void colorBackgroundWinnerButtons(Button b1, Button b2, Button b3) {
            b1.setStyle("-fx-background-radius: 0;-fx-padding : 0; -fx-border-style : none;-fx-background-color:  #2053c6;");
            b2.setStyle("-fx-background-radius: 0;-fx-padding : 0; -fx-border-style : none;-fx-background-color:  #2053c6;");
            b3.setStyle("-fx-background-radius: 0;-fx-padding : 0; -fx-border-style : none;-fx-background-color:  #2053c6;");
            AnimateBackgroundColor(b1);
            AnimateBackgroundColor(b2);
            AnimateBackgroundColor(b3);
        }

        // Animate the 3 cells background
        public static void AnimateBackgroundColor( Button btn) {
            FadeTransition ft = new FadeTransition();
            ft.setNode(btn);
            ft.setDuration(new Duration(1000));
            ft.setFromValue(1.0);
            ft.setToValue(0.7);
            ft.setCycleCount(Animation.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }


        // Mention the Current Player
        private void setCurrentTurn() {
            if(isFirstPlayerTurn) {
                playerTurn.setText(nomo);
                playerTurn.setTextFill(oColor);
            }else {
                playerTurn.setText(nomx);
                playerTurn.setTextFill(xColor);
            }
        }

        // Mention the Winner
        private void setWinner() {
            stopTimer(timeline);
            if(isFirstPlayerTurn) {
                playerTurn.setText(nomx+" a gagner");

            }else {
                playerTurn.setText(nomo+" a gagner");
                playerTurn.setTextAlignment(TextAlignment.CENTER);
            }
            playerTurn.setTextFill(Color.WHITE);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), playerTurn);
            transition.setByY(-50);
            transition.setAutoReverse(true);
            transition.setCycleCount(2);
            transition.play();
        }

        @FXML
        Label timer;

        Timeline timeline;
        public void timer(){
            AtomicInteger seconds = new AtomicInteger();
            // Créer une Timeline pour mettre à jour le label toutes les secondes
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds.getAndIncrement();
                timer.setText("Temps :"+ seconds + "s");
            }));
            timeline.setCycleCount(Timeline.INDEFINITE); // Mettre la Timeline en boucle
            timeline.play();
        }

        private void stopTimer(Timeline timeline) {
            timeline.stop();
        }


}
