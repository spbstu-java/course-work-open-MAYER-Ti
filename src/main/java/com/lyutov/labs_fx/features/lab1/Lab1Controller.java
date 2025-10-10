package com.lyutov.labs_fx.features.lab1;

import com.lyutov.labs_fx.core.ResultPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class Lab1Controller {

    @FXML
    private TextArea consoleArea;
    @FXML
    private Button runButton;
    @FXML
    private ComboBox<String> movementSelector;
    @FXML
    private ComboBox<String> citiesSelector;

    private ResultPrinter resultPrinter;

    HorseMovement horse = new HorseMovement();
    LegsMovement legs = new LegsMovement();

    Point cities[] = {
            new Point("saintPetersburg", 59.9386, 30.3141),
            new Point("moscow", 55.7522, 37.6156),
            new Point("minsk", 53.9, 27.5667)};

    Hero aleshaPopovich = new Hero(legs, cities[0]);

    public void setResultPrinter(ResultPrinter rp) {
        this.resultPrinter = rp;
        if (consoleArea != null) {
            rp.unbind();
            rp.bindTo(consoleArea);
        }
    }

    @FXML
    public void initialize() {
        movementSelector.getItems().addAll("Пешком", "На лошади");
        movementSelector.setValue(movementSelector.getItems().getFirst());
        movementSelector.setOnAction(e -> {
            if ("Пешком".equals(movementSelector.getValue())) {
                aleshaPopovich.setMovement(new LegsMovement());
            } else {
                aleshaPopovich.setMovement(new HorseMovement());
            }
        });
        citiesSelector.getItems().addAll(cities[0].toString(), cities[1].toString(), cities[2].toString());
        citiesSelector.setValue(citiesSelector.getItems().getFirst());
        if (runButton != null) {
            runButton.setOnAction(e -> moveHero());
        }
    }

    public void moveHero() {
        //resultPrinter.clear();
        resultPrinter.println("Алеша Попович находится в " + aleshaPopovich.getDislocation().toString());
        aleshaPopovich.setMovement(stringToMovement(movementSelector.getValue()));
        if (Objects.equals(movementSelector.getValue(), "Пешком")) {
            resultPrinter.println("Разминает ноги и отправляется в путь");
        }
        else if (Objects.equals(movementSelector.getValue(), "На лошади")) {
            resultPrinter.println("Запрягает своего верного коня");
        }
        else {
            resultPrinter.println("Непонятно что делает");
        }
        aleshaPopovich.move(cities[citiesSelector.getSelectionModel().getSelectedIndex()]);
        resultPrinter.println("Алеша Попович находится в " + aleshaPopovich.getDislocation().toString());
    }

    public void onClear() {
        if (resultPrinter != null) resultPrinter.clear();
    }


    private Movement stringToMovement(String move) {
        switch (move) {
            case "Пешком" -> { return new LegsMovement();}
            case "На лошади" -> {return new HorseMovement();}
            default -> { return new LegsMovement();  }
        }
    }
}

