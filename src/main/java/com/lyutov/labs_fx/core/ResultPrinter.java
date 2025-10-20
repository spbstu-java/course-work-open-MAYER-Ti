package com.lyutov.labs_fx.core;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ResultPrinter {
    private TextArea console;

    public void bindTo(TextArea textArea) {
        unbind();
        this.console = textArea;
    }

    public void unbind() {
        if (console != null) {
            // Очистить предыдущие слушатели при необходимости
            console = null;
        }
    }

    public void println(String s) {
        if (console == null) {
            System.out.println(s);
            return;
        }
        Platform.runLater(() -> {
            console.appendText(s + "\n");
        });
    }

    public void clear() {
        if (console == null) return;
        Platform.runLater(() -> console.clear());
    }
}
