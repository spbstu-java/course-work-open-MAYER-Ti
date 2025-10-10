package com.lyutov.labs_fx.features.lab2;

import com.lyutov.labs_fx.core.ResultPrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.lang.reflect.*;

public class Lab2Controller {
    @FXML
    private TextArea consoleArea;

    @FXML
    private Button runButton;

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
    }

    public void run() {
        resultPrinter.clear();
        try {
            Class<?> clazz = DemoService.class;
            Object instance = createInstance(clazz);

            // Передаем resultPrinter в DemoService
            if (instance instanceof DemoService) {
                ((DemoService) instance).setResultPrinter(resultPrinter);
            }

            for (Method m : clazz.getDeclaredMethods()) {
                if (!m.isAnnotationPresent(Repeat.class)) {
                    continue;
                }

                int mods = m.getModifiers();
                if (!(Modifier.isProtected(mods) || Modifier.isPrivate(mods))) {
                    continue;
                }

                int times = m.getAnnotation(Repeat.class).value();
                m.setAccessible(true);
                Object[] params = buildDefaultArgs(m.getParameterTypes());
                Object target = Modifier.isStatic(mods) ? null : instance;

                for (int i = 0; i < times; i++) {
                    Object result = m.invoke(target, params);
                    if (m.getReturnType() != void.class) {
                        resultPrinter.println("Result of " + m.getName() + " #" + (i + 1) + ": " + result);
                    }
                }
                // выбросит NoSuchMethodException
                Method fake = DemoService.class.getMethod("noSuchMethod");
            }
        } catch (ClassNotFoundException e) {
            resultPrinter.println("No class found: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            resultPrinter.println("No method found: " + e.getMessage());
        } catch (InvocationTargetException e) {
            resultPrinter.println("Error on execution method: "
                    + e.getTargetException().getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            resultPrinter.println("Error on initialize object: " + e.getMessage());
        } catch (Exception e) {
            resultPrinter.println("Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    public void onClear() {
        if (resultPrinter != null) resultPrinter.clear();
    }

    private static Object createInstance(Class<?> clazz) throws Exception {
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    private static Object[] buildDefaultArgs(Class<?>[] types) {
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            args[i] = defaultValue(types[i]);
        }
        return args;
    }

    private static Object defaultValue(Class<?> type) {
        if (!type.isPrimitive()) {
            if (type == String.class) return "";
            if (type.isArray()) return Array.newInstance(type.getComponentType(), 0);
            if (type.isEnum()) {
                Object[] constants = type.getEnumConstants();
                return (constants != null && constants.length > 0) ? constants[0] : null;
            }
            return null;
        }
        if (type == boolean.class) return false;
        if (type == byte.class)    return (byte) 0;
        if (type == short.class)   return (short) 0;
        if (type == int.class)     return 0;
        if (type == long.class)    return 0L;
        if (type == float.class)   return 0f;
        if (type == double.class)  return 0d;
        if (type == char.class)    return '\0';
        return null;
    }

}
