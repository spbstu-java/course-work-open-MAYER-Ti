package com.lyutov.labs_fx.features.lab3;

import com.lyutov.labs_fx.core.ResultPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Lab3Controller {
    @FXML
    private TextArea consoleArea;

    @FXML
    private Button runButton;

    @FXML
    private TextArea langBook;
    @FXML
    private TextArea langQuery;

    private ResultPrinter resultPrinter;

    public void setResultPrinter(ResultPrinter rp) {
        this.resultPrinter = rp;
        if (consoleArea != null) {
            rp.unbind();
            rp.bindTo(consoleArea);
        }
    }

    @FXML
    public void initialize() {
        if (runButton != null) {
            runButton.setOnAction(e -> run());
        }
        langBook.setText("look | смотреть\n" +
                "look forward | ожидать");
        langQuery.setText("dog look to the window, dog look forward");
    }

    public void run() {
        DictionaryLoader loader = new DictionaryLoader();

        try {
            loader.load(langBook.getText());

            Translator translator = new Translator(loader.getDictionary());

            resultPrinter.println("Словарь загружен.");
            String input = langQuery.getText();
            resultPrinter.println("Текст загружен.");

            String output = translator.translate(input);
            resultPrinter.println("Перевод:");
            resultPrinter.println(output);

        } catch (InvalidFileFormatException e) {
            resultPrinter.println("Ошибка формата словаря: " + e.getMessage());
        } catch (FileReadException e) {
            resultPrinter.println("Ошибка чтения словаря: " + e.getMessage());
        }

    }

    @FXML
    public void onClear() {
        if (resultPrinter != null) {
            resultPrinter.clear();
        }
    }

}
