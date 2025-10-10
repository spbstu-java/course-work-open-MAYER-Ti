package com.lyutov.labs_fx.features.lab2;
import com.lyutov.labs_fx.core.ResultPrinter;

public class DemoService {
    private ResultPrinter resultPrinter;

    public DemoService() {} // обязательный конструктор без аргументов

    public void setResultPrinter(ResultPrinter resultPrinter) {
        this.resultPrinter = resultPrinter;
    }

    private void print(String message) {
        if (resultPrinter != null) {
            resultPrinter.println(message);
        } else {
            System.out.println(message); // fallback
        }
    }

    // --- Публичные методы (для полноты задания, вызываться не будут) ---
    public void greet(String name) {
        print("public greet: Hello, " + name);
    }

    public int sum(int a, int b) {
        int r = a + b;
        print("public sum: " + a + " + " + b + " = " + r);
        return r;
    }

    // --- Защищённые методы (аннотированы) ---
    @Repeat(2)
    protected void logAction(String action, int code) {
        print("protected logAction: action=" + action + ", code=" + code);
    }

    @Repeat(3)
    protected int square(int x) {
        int r = x * x;
        print("protected square(" + x + ") = " + r);
        return r;
    }

    // --- Приватные методы (аннотированы) ---
    @Repeat(1)
    private String secretConcat(String a, String b, String c) {
        String r = a + b + c;
        print("private secretConcat -> \"" + r + "\"");
        return r;
    }

    @Repeat(4)
    private static void staticSecret(int n, boolean flag) {
        System.out.println("private staticSecret: n=" + n + ", flag=" + flag);
    }
}

