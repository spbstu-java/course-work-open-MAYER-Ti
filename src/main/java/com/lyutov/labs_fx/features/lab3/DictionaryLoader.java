package com.lyutov.labs_fx.features.lab3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class DictionaryLoader {
    private final Map<String, String> dictionary = new HashMap<>();

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public void load(String langBook) throws InvalidFileFormatException, FileReadException {
        String[] lines = langBook.split("\\R");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length != 2) {
                throw new InvalidFileFormatException("Неверный формат строки: " + line);
            }
           String key = parts[0].trim().toLowerCase();
           String value = parts[1].trim();
           dictionary.put(key, value);
        }
    }
}
