package com.lyutov.labs_fx.features.lab3;

import java.util.*;

public class Translator {
    private Map<String, String> dictionary = new HashMap<>();

    public Translator() {}

    public Translator(Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public void setDictionary(Map<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public String translate(String text) {
        if (dictionary == null || dictionary.isEmpty()) {
            return text;
        }

        String[] words = text.split("\\s+");
        List<String> keys = new ArrayList<>(dictionary.keySet());

        // сортируем: сначала длинные ключи (для look vs look forward)
        keys.sort((a, b) -> Integer.compare(b.length(), a.length()));

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String translation = null;
            String originalWord = words[i];

            // проверяем фразы
            for (String key : keys) {
                String[] keyParts = key.split(" ");
                int keyLen = keyParts.length;

                if (i + keyLen <= words.length) {
                    StringBuilder phraseBuilder = new StringBuilder();
                    for (int j = 0; j < keyLen; j++) {
                        if (j > 0) phraseBuilder.append(" ");
                        phraseBuilder.append(words[i + j].toLowerCase());
                    }
                    String phrase = phraseBuilder.toString();

                    if (phrase.equals(key)) {
                        translation = dictionary.get(key);
                        i += keyLen - 1; // пропускаем использованные слова
                        break;
                    }
                }
            }

            if (translation == null) {
                result.append(originalWord);
            } else {
                result.append(translation);
            }

            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}

