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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public String get(String key) {
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
        return new Language(languageName, new HashMap<>(getSection("keys")));
    }

}
