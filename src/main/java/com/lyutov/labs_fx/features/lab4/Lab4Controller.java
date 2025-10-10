package com.lyutov.labs_fx.features.lab4;

import com.lyutov.labs_fx.core.ResultPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.*;
import java.util.stream.*;

public class Lab4Controller {
    @FXML
    private TextArea consoleArea;

    @FXML
    private Button runButton;

    @FXML
    private TextArea numbersArea;

    @FXML
    private TextArea stringsArea;

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
        numbersArea.setText("1\n2\n3\n4\n5\n5");
        stringsArea.setText("apple\nbanana\ncherry");
    }

    public void run() {
        List<Integer> nums = numbersArea.getText().lines()
                .filter(line -> !line.trim().isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<String> strs = stringsArea.getText().lines()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());

        resultPrinter.println("Среднее: " + average(nums));
        resultPrinter.println("Upper + prefix: " + toUpperWithPrefix(strs));
        resultPrinter.println("Уникальные квадраты: " + uniqueSquares(nums));
        resultPrinter.println("Последний элемент: " + lastElement(strs));
        resultPrinter.println("Сумма чётных: " + sumEven(new int[]{1,2,3,4,5,6}));
        resultPrinter.println("Map: " + toMap(strs));
    }

    @FXML
    public void onClear() {
        if (resultPrinter != null) resultPrinter.clear();
    }

    // 1. Среднее значение списка целых чисел
    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Список пуст"));
    }

    // 2. Все строки в верхний регистр + префикс "_new_"
    public static List<String> toUpperWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    // 3. Квадраты элементов, встречающихся только 1 раз
    public static List<Integer> uniqueSquares(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> Collections.frequency(numbers, n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // 4. Последний элемент коллекции
    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    // 5. Сумма чётных чисел в массиве
    public static int sumEven(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    // 6. Преобразовать строки в Map: первый символ -> остальная строка
    public static Map<Character, String> toMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> existing // если ключ повторяется
                ));
    }
}
