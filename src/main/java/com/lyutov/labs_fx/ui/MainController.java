package com.lyutov.labs_fx.ui;

import com.lyutov.labs_fx.core.ResultPrinter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    @FXML
    private ListView<String> tasksList;

    @FXML
    private StackPane contentPane;

    private ResultPrinter resultPrinter = new ResultPrinter();

    // Кэшируем FXMLLoader вместо Node
    private Map<String, FXMLLoader> loaders = new HashMap<>();

    @FXML
    public void initialize() {
        tasksList.getItems().addAll("Задание 1: Strategy", "Задание 2: Annotations", "Задание 3: Translator", "Задание 4: Streams");
        tasksList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                try {
                    showTask(newV);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tasksList.getSelectionModel().selectFirst();
    }

    private void showTask(String name) throws IOException {
        FXMLLoader loader;

        // Загружаем FXMLLoader только при первом обращении
        if (!loaders.containsKey(name)) {
            String fxml = switch (name) {
                case "Задание 1: Strategy" -> "/Lab1View.fxml";
                case "Задание 2: Annotations" -> "/Lab2View.fxml";
                case "Задание 3: Translator" -> "/Lab3View.fxml";
                case "Задание 4: Streams" -> "/Lab4View.fxml";
                default -> "/empty.fxml";
            };
            URL path = MainController.class.getResource(fxml);
            if (path == null) {
                throw new IOException("FXML file not found: " + fxml);
            }
            loader = new FXMLLoader(path);
            loader.load(); // Загружаем FXML
            loaders.put(name, loader);
        } else {
            loader = loaders.get(name);
        }

        // Всегда обновляем контент и привязку ResultPrinter
        Node node = loader.getRoot();
        contentPane.getChildren().setAll(node);

        // Критически важная часть: вызываем setResultPrinter КАЖДЫЙ раз при отображении вкладки
        Object controller = loader.getController();
        if (controller != null) {
            try {
                controller.getClass()
                        .getMethod("setResultPrinter", ResultPrinter.class)
                        .invoke(controller, resultPrinter);
            } catch (NoSuchMethodException ignored) {
                // Контроллер не имеет метода setResultPrinter
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
