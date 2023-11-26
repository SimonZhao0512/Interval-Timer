package com.example.intellij_infinite_timer_real;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Controller {

    @FXML

    public void initialize() {
        System.out.println("let's get started");
        timer1.setCycleCount(Timeline.INDEFINITE);
        timer2.setCycleCount(Timeline.INDEFINITE);
        monitorRepeatAll();
    }

    boolean isAllRepeating = false;
    int elapsedTime1;
    int elapsedTimeHistory1;
    int seconds1 = (elapsedTime1 / 1000) % 60;
    int mins1 = (elapsedTime1 / 60000) % 60;
    int hours1 = (elapsedTime1 / 3600000);
    boolean started1 = false;
    boolean isRepeating1 = false;
    String seconds_string1 = String.format("%02d", seconds1);
    String mins_string1 = String.format("%02d", mins1);
    String hours_string1 = String.format("%02d", hours1);

    public Button btn_repeatAll = new Button("Repeat All (Off)");
    public Button btn_start1 = new Button("Start");
    public Button btn_reset1 = new Button("Reset");
    public TextField txt_setHour1 = new TextField();
    public TextField txt_setMin1 = new TextField();
    public TextField txt_setSec1 = new TextField();
    public Button btn_repeat1 = new Button("Repeat");
    public Label label_time1 = new Label();

    Timeline timer1 = new Timeline(

            new KeyFrame(Duration.seconds(1), event -> {
                // TODO Auto-generated method stub
                // System.out.println(elapsedTime);

                if (elapsedTime1 > 0) {
                    perfromCountingDown1(); // perform counting down probably has problems
                }

                else if (elapsedTime1 == 0 && isRepeating1) {
                    // Toolkit.getDefaultToolkit().beep();
                    playCustomSound();
                    setTime1(txt_setHour1.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setHour1.getText().trim().replaceAll("[^0-9]", "")),
                            txt_setMin1.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setMin1.getText().trim().replaceAll("[^0-9]", "")),
                            txt_setSec1.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setSec1.getText().trim().replaceAll("[^0-9]", ""))
                                    + 1);
                    perfromCountingDown1();
                } else if (this.elapsedTime1 <= 0 && !isRepeating1) {
                    stop1();
                    // Toolkit.getDefaultToolkit().beep();
                    playCustomSound();
                }

            }));

    public void btn_start1_Click(ActionEvent e) {

        if (!started1) {
            this.elapsedTimeHistory1 = this.elapsedTime1;
            started1 = true;
            btn_start1.setText("Stop");
            timer1.play();

        } else {
            started1 = false;
            btn_start1.setText("Start");

            timer1.stop();

        }
    }

    public void btn_reset1_Click(ActionEvent e) {
        started1 = false;
        btn_start1.setText("Start");
        reset1();
    }

    public void btn_set1_Click(ActionEvent e) {
        setTime1();
    }

    public void btn_repeat1_Click(ActionEvent e) {
        if (isRepeating1 == false) {
            isRepeating1 = true;
            btn_repeat1.setStyle("-fx-background-color: green;");

        } else {
            isRepeating1 = false;
            btn_repeat1.setStyle("-fx-background-color: -fx-default-button");
        }
    }

    public void start1() {
        timer1.play();
        started1 = true;
        btn_start1.setText("Stop");
    }

    public void stop1() {
        timer1.stop();
        // isRunning = false;
        started1 = false;
        btn_start1.setText("Start");
    }

    public void reset1() {
        timer1.stop();
        hours1 = 0;
        mins1 = 0;
        seconds1 = 0;
        this.elapsedTime1 = 0;
        // placeholder for hours numbers and seconds: 00 00 00
        this.seconds_string1 = String.format("%02d", this.seconds1);
        this.mins_string1 = String.format("%02d", this.mins1);
        this.hours_string1 = String.format("%02d", this.hours1);
        this.label_time1.setText(this.hours_string1 + " : " + this.mins_string1 + " : " + this.seconds_string1);

    }

    public void setTime1(int myhour, int mymin, int mysec) {
        hours1 = myhour;
        mins1 = mymin;
        seconds1 = mysec;

        this.elapsedTime1 = hours1 * 60 * 60 * 1000 + mins1 * 1000 * 60 + seconds1 * 1000;
        // placeholder for hours numbers and seconds: 00 00 00
        this.seconds_string1 = String.format("%02d", this.seconds1);
        this.mins_string1 = String.format("%02d", this.mins1);
        this.hours_string1 = String.format("%02d", this.hours1);
        this.label_time1.setText(this.hours_string1 + " : " + this.mins_string1 + " : " + this.seconds_string1);

    }

    public void setTime1() {
        hours1 = txt_setHour1.getText().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setHour1.getText().trim().replaceAll("[^0-9]", ""));
        mins1 = txt_setMin1.getText().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setMin1.getText().trim().replaceAll("[^0-9]", ""));
        seconds1 = txt_setSec1.getText().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setSec1.getText().trim().replaceAll("[^0-9]", ""));

        this.elapsedTime1 = hours1 * 60 * 60 * 1000 + mins1 * 1000 * 60 + seconds1 * 1000;
        System.out.println("elapsedTimeHistory1: " + elapsedTimeHistory1);
        // placeholder for hours numbers and seconds: 00 00 00
        this.seconds_string1 = String.format("%02d", this.seconds1);
        this.mins_string1 = String.format("%02d", this.mins1);
        this.hours_string1 = String.format("%02d", this.hours1);
        this.label_time1.setText(this.hours_string1 + " : " + this.mins_string1 + " : " + this.seconds_string1);
    }

    public void perfromCountingDown1() {
        elapsedTime1 -= 1000;
        hours1 = (elapsedTime1 / 3600000);
        mins1 = (elapsedTime1 / 60000) % 60;
        seconds1 = (elapsedTime1 / 1000) % 60;
        String seconds_string1 = String.format("%02d", seconds1);
        String mins_string1 = String.format("%02d", mins1);
        String hours_string1 = String.format("%02d", hours1);
        label_time1.setText(hours_string1 + " : " + mins_string1 + " : " + seconds_string1);
    }

    // ---------------------------------------------------------------------------------------------

    int elapsedTime2;
    int elapsedTimeHistory2;
    int seconds2 = (elapsedTime2 / 1000) % 60;
    int mins2 = (elapsedTime2 / 60000) % 60;
    int hours2 = (elapsedTime2 / 3600000);
    boolean started2 = false;
    boolean isRepeating2 = false;
    String seconds_string2 = String.format("%02d", seconds2);
    String mins_string2 = String.format("%02d", mins2);
    String hours_string2 = String.format("%02d", hours2);

    public Button btn_start2 = new Button("Start");
    public Button btn_reset2 = new Button("Reset");
    public TextField txt_setHour2 = new TextField();
    public TextField txt_setMin2 = new TextField();
    public TextField txt_setSec2 = new TextField();
    public Button btn_repeat2 = new Button("Repeat");
    public Label label_time2 = new Label();

    Timeline timer2 = new Timeline(

            new KeyFrame(Duration.seconds(1), event -> {
                // TODO Auto-generated method stub
                // System.out.println(elapsedTime);

                if (elapsedTime2 > 0) {
                    perfromCountingDown2(); // perform counting down probably has problems
                }

                else if (elapsedTime2 == 0 && isRepeating2) {
                    // Toolkit.getDefaultToolkit().beep();
                    playCustomSound();
                    setTime2(txt_setHour2.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setHour2.getText().trim().replaceAll("[^0-9]", "")),
                            txt_setMin2.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setMin2.getText().trim().replaceAll("[^0-9]", "")),
                            txt_setSec2.getText().isEmpty() ? 0
                                    : parseAndTakeFirstTwoDigits(txt_setSec2.getText().trim().replaceAll("[^0-9]", ""))
                                    + 1);
                    perfromCountingDown2();
                } else if (this.elapsedTime2 <= 0 && !isRepeating2) {
                    stop2();
                    // Toolkit.getDefaultToolkit().beep();
                    playCustomSound();
                }

            }));

    public void btn_start2_Click(ActionEvent e) {

        if (!started2) {
            this.elapsedTimeHistory2 = this.elapsedTime2;
            started2 = true;
            btn_start2.setText("Stop");
            timer2.play();

        } else {
            started2 = false;
            btn_start2.setText("Start");

            timer2.stop();

        }
    }

    public void btn_reset2_Click(ActionEvent e) {
        started2 = false;
        btn_start2.setText("Start");
        reset2();
    }

    public void btn_set2_Click(ActionEvent e) {
        setTime2();
    }

    public void btn_repeat2_Click(ActionEvent e) {
        if (isRepeating2 == false) {
            isRepeating2 = true;
            btn_repeat2.setStyle("-fx-background-color: green;");

        } else {
            isRepeating2 = false;
            btn_repeat2.setStyle("-fx-background-color: -fx-default-button");
        }
    }

    public void start2() {
        timer2.play();
        btn_start2.setText("Stop");
    }

    public void stop2() {
        timer2.stop();
        // isRunning = false;
        started2 = false;
        btn_start2.setText("Start");
    }

    public void getTimeFromTxtField() {
        hours2 = txt_setHour2.getText().isEmpty() ? 0 : Integer.parseInt(txt_setHour2.getText().trim());
        mins2 = txt_setMin2.getText().isEmpty() ? 0 : Integer.parseInt(txt_setMin2.getText().trim());
        seconds2 = txt_setSec2.getText().isEmpty() ? 0 : Integer.parseInt(txt_setSec2.getText().trim());

    }

    public void reset2() {
        timer2.stop();
        hours2 = 0;
        mins2 = 0;
        seconds2 = 0;
        this.elapsedTime2 = 0;

        this.seconds_string2 = String.format("%02d", this.seconds2);
        this.mins_string2 = String.format("%02d", this.mins2);
        this.hours_string2 = String.format("%02d", this.hours2);
        this.label_time2.setText(this.hours_string2 + " : " + this.mins_string2 + " : " + this.seconds_string2);

    }

    private int parseAndTakeFirstTwoDigits(String input) {
        try {
            int number = Integer.parseInt(input);
            String numberAsString = String.valueOf(Math.max(number, 0));
            if (numberAsString.length() >= 2) {
                return Integer.parseInt(numberAsString.substring(0, 2));
            } else {
                return Integer.parseInt(numberAsString);
            }
        } catch (NumberFormatException e) {
            // Handle invalid input, e.g., return a default value
            return 0;
        }
    }

    public void setTime2(int myhour, int mymin, int mysec) {
        hours2 = myhour;
        mins2 = mymin;
        seconds2 = mysec;

        this.elapsedTime2 = hours2 * 60 * 60 * 1000 + mins2 * 1000 * 60 + seconds2 * 1000;
        // placeholder for hours numbers and seconds: 00 00 00
        this.seconds_string2 = String.format("%02d", this.seconds2);
        this.mins_string2 = String.format("%02d", this.mins2);
        this.hours_string2 = String.format("%02d", this.hours2);
        this.label_time2.setText(this.hours_string2 + " : " + this.mins_string2 + " : " + this.seconds_string2);

    }

    public void setTime2() {
        hours2 = txt_setHour2.getText().trim().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setHour2.getText().trim().replaceAll("[^0-9]", ""));
        mins2 = txt_setMin2.getText().trim().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setMin2.getText().trim().replaceAll("[^0-9]", ""));
        seconds2 = txt_setSec2.getText().trim().isEmpty() ? 0
                : parseAndTakeFirstTwoDigits(txt_setSec2.getText().trim().replaceAll("[^0-9]", ""));

        this.elapsedTime2 = hours2 * 60 * 60 * 1000 + mins2 * 1000 * 60 + seconds2 * 1000;
        System.out.println("elapsedTimeHistory2: " + elapsedTimeHistory2);
        // placeholder for hours numbers and seconds: 00 00 00
        this.seconds_string2 = String.format("%02d", this.seconds2);
        this.mins_string2 = String.format("%02d", this.mins2);
        this.hours_string2 = String.format("%02d", this.hours2);
        this.label_time2.setText(this.hours_string2 + " : " + this.mins_string2 + " : " + this.seconds_string2);
    }

    public void perfromCountingDown2() {
        elapsedTime2 -= 1000;
        hours2 = (elapsedTime2 / 3600000);
        mins2 = (elapsedTime2 / 60000) % 60;
        seconds2 = (elapsedTime2 / 1000) % 60;
        String seconds_string2 = String.format("%02d", seconds2);
        String mins_string2 = String.format("%02d", mins2);
        String hours_string2 = String.format("%02d", hours2);
        label_time2.setText(hours_string2 + " : " + mins_string2 + " : " + seconds_string2);
    }

    // --------------------------------------------------
    public void playCustomSound() {
        try {

            // Load your custom sound file (e.g., a WAV file)
            File file = new File(
                    "/Users/yuhongzhao/Desktop/programming/projects/myJavaProjects/useable_projects/JavaFX_Infinite_Timer/src/asset/mixkit-interface-hint-notification-911.wav");
            // URL url = file.toURI().toURL();
            // Create an AudioInputStream from the sound file
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {

                // Get the default Clip
                Clip clip = AudioSystem.getClip();

                // Open the AudioInputStream and start playing
                clip.open(audioInputStream);
                clip.start();

            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Handle the exception appropriately, e.g., log it
            e.printStackTrace();
        }
    }

    public void btn_resetALL_Click(ActionEvent e) {
        reset1();
        reset2();
        btn_start1.setText("Start");
        btn_start2.setText("Start");
        txt_setHour1.clear();
        txt_setMin1.clear();
        txt_setSec1.clear();
        txt_setHour2.clear();
        txt_setMin2.clear();
        txt_setSec2.clear();
        elapsedTime1 = 0;
        elapsedTime2 = 0;

        if (isAllRepeating) {
            isAllRepeating = false;
            btn_repeatAll.setText("Repeat All (Off)");
            btn_repeatAll.setStyle("-fx-background-color: -fx-default-button");

            elapsedTimeHistory1 = 0;
            elapsedTimeHistory2 = 0;
        }
    }

    public void monitorRepeatAll() {
        Thread monitorThread = new Thread(() -> {
            try {

                while (true) {

                    Platform.runLater(() -> {
                        if (isAllRepeating) {
                            // when 1 times runs out, the next one will go
                            // if (elapsedTime1 != 0 && elapsedTime2 != 0) {
                            // timer1.stop();
                            // timer2.stop();
                            // }

                            // ---------------------------------------------------------------
                            if (elapsedTime1 == 0 && elapsedTime2 != 0) {
                                playCustomSound();
                                start2();

                                btn_start2.setText("Stop");
                                setTime1();
                                timer1.stop();
                            }
                            if (elapsedTime2 == 0 && elapsedTime1 != 0 && elapsedTimeHistory1 != 0) {
                                playCustomSound();
                                start1();

                                btn_start1.setText("Stop");
                                setTime2();
                                timer2.stop();
                            }

                            // -----------------------------------------------------------------
                            // if (stopwatch1.elapsedTime == 0 && stopwatch2.elapsedTime == 0 &&
                            // stopwatch1.elapsedTimeHistory != 0) {
                            // stopwatch1.setTime(stopwatch1.elapsedTimeHistory / 1000 / 60);

                            // }

                        }

                    });

                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        monitorThread.start();
    }

    public void btn_repeatAll_Click(ActionEvent e) {
        // System.out.println("test test");

        if (isAllRepeating == false) {
            isAllRepeating = true;
            btn_repeatAll.setText("Repeat All (On)");
            btn_repeatAll.setStyle("-fx-background-color: green;");

        } else {
            isAllRepeating = false;
            btn_repeatAll.setText("Repeat All (Off)");
            btn_repeatAll.setStyle("-fx-background-color: -fx-default-button");

            elapsedTimeHistory1 = 0;
            elapsedTimeHistory2 = 0;
        }
    }

}
