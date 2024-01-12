package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private TextField screen ;
    @FXML
    private Text savedNumber;
    private String Function = "";
    private Calculator calculator = new Calculator();

    @FXML
    void additionButton(ActionEvent event) {addNumberIntoTextField("+");}
    @FXML
    void subtractionButton(ActionEvent event) {addNumberIntoTextField("-");}
    @FXML
    void multiplicationButton(ActionEvent event) {addNumberIntoTextField("*");}
    @FXML
    void divisionButton(ActionEvent event) {addNumberIntoTextField("/");}
    @FXML
    void button1Clicked(ActionEvent event) {addNumberIntoTextField("1");}
    @FXML
    void button2Clicked(ActionEvent event) {addNumberIntoTextField("2");}
    @FXML
    void button3Clicked(ActionEvent event) {addNumberIntoTextField("3");}
    @FXML
    void button4Clicked(ActionEvent event) {addNumberIntoTextField("4");}
    @FXML
    void button5Clicked(ActionEvent event) {addNumberIntoTextField("5");}
    @FXML
    void button6Clicked(ActionEvent event) {addNumberIntoTextField("6");}
    @FXML
    void button7Clicked(ActionEvent event) {addNumberIntoTextField("7");}
    @FXML
    void button8Clicked(ActionEvent event) {addNumberIntoTextField("8");}
    @FXML
    void button9Clicked(ActionEvent event) {addNumberIntoTextField("9");}
    @FXML
    void button0Clicked(ActionEvent event) {addNumberIntoTextField("0");}
    @FXML
    void buttonOpeningBracketClicked(ActionEvent event) {addNumberIntoTextField("(");}
    @FXML
    void buttonClosingBracketClicked(ActionEvent event) {addNumberIntoTextField(")");}
    @FXML
    void buttonDotClicked(ActionEvent event) {
        if (!Function.contains(".")) {
            addNumberIntoTextField(".");
        }
    }
    @FXML
    void buttonEqualClicked(ActionEvent event) {
        String result = calculator.calculate(Function);
        savedNumber.setText(Function);
        screen.setText(result);
        Function = "";

    }
    void setText() {
        screen.setText(Function);
    }
    void addNumberIntoTextField(String string) {
        Function += string;
        setText();
    }

    @FXML
    void delete() {
        Function = "";
        setText();
    }
}
