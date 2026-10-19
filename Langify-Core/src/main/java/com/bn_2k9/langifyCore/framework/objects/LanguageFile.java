package com.bn_2k9.langifyCore.framework.objects;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

public class LanguageFile {

    @Getter
    private final Map<String, Object> data;

    private final String languageName;

    public LanguageFile(File file) {
        languageName = file.getName();

        Map<String, Object> temp = Map.of();
        LoaderOptions loaderOptions = new LoaderOptions();
        Yaml yaml = new Yaml(new SafeConstructor(loaderOptions));

        try (InputStream stream = Files.newInputStream(file.toPath())) {
            Map<String, Object> loaded = yaml.load(stream);
            temp = Objects.requireNonNullElseGet(loaded, Map::of);
        } catch (IOException e) {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to load language file: " + languageName + "Error: " + e.getMessage());
        }
        this.data = temp;
    }

    /**
     * Get a single string key value.
     * @param key the key to get the string value of.
     * @return The result string.
     */
    public String getString(String key) {
        Object value = data;

        for (String part : key.split("\\.")) {
            if (!(value instanceof Map<?, ?> map)) {
                return null;
            }

            value = map.get(part);
        }

        if (value != null) {
            assert value instanceof String;
            return (String) value;
        }
        return null;
    }

    /**
     * Get a int string key value.
     * @param key the key to get the string value of.
     * @return The result int.
     */
    public int getInt(String key) {
        Object value = data;

        for (String part : key.split("\\.")) {
            if (!(value instanceof Map<?, ?> map)) {
                return -1;
            }

            value = map.get(part);
        }

        if (value != null) {
            assert value instanceof Integer;
            return (Integer) value;
        }
        return -1;
    }

    /**
     * Get a single string key value.
     * @param key the key to get the string value of.
     * @return The result string.
     */
    public boolean getBool(String key) {
        Object value = data;

        for (String part : key.split("\\.")) {
            if (!(value instanceof Map<?, ?> map)) {
                return false;
            }

            value = map.get(part);
        }

        if (value != null) {
            assert value instanceof Boolean;
            return (Boolean) value;
        }
        return false;
    }

    /**
     * Get a List<String> key value.
     * @param key the key to get the string value of.
     * @return The result List<String>.
     */
    public List<String> getStringList(String key) {
        Object value = data;

        for (String part : key.split("\\.")) {
            if (!(value instanceof Map<?, ?> map)) {
                return List.of();
            }

            value = map.get(part);
        }

        if (!(value instanceof List<?> list)) {
            return List.of();
        }

        List<String> result = new ArrayList<>();

        for (Object element : list) {
            if (!(element instanceof String string)) {
                return List.of();
            }

            result.add(string);
        }

        return result;
    }

    /**
     * Get a section of the language file.
     * @param key the section to get.
     * @return returns the string values.
     */
    public Map<String, String> getSection(String key) {
        Object value = data;

        for (String part : key.split("\\.")) {
            if (!(value instanceof Map<?, ?> map)) {
                return Map.of();
            }

            value = map.get(part);
        }

        if (!(value instanceof Map<?, ?> map)) {
            return Map.of();
        }

        Map<String, String> result = new HashMap<>();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getKey() instanceof String name) {
                result.put(name, (String) entry.getValue());
            }
        }

        return result;
    }


    public Language getLanguage() {
        return new Language(languageName, getString("Gui.ItemName"), getString("Gui.Material"), getInt("Gui.InventorySlot"), getStringList("Gui.Lore"), getBool("Gui.ShowInGui"), new HashMap<>(getSection("keys")));
    }

}
