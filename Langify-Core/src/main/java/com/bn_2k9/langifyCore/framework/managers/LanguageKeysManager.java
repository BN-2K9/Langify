package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import com.bn_2k9.langifyCore.framework.objects.Language;
import com.bn_2k9.langifyCore.framework.objects.LanguageFile;
import com.bn_2k9.langifyCore.framework.utils.LangDataStoreMethod;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LanguageKeysManager {

    /**
     * The method selected for loading the languageKeys
     */
    LangDataStoreMethod langDataStoreMethod;

    /**
     * A list of available Languages.
     */
    List<String> availableLanguages = new ArrayList<>();

    /**
     * A has map for all the languageKeys, So: The first string is the Language_Name, The second hashmap is the key, entry.
     */
    HashMap<String, Language> languageKeys = new HashMap<>();

    public void loadLanguageKeys() {

        // Remove the old languages.
        availableLanguages = new ArrayList<>();
        // Put all the new languages in.
        int countOfLanguages = 0;
        for (File file : LangifyCore.getInstance().getFileInterface().getLanguageFiles()) {
            availableLanguages.add(file.getName());
            countOfLanguages++;
        }

        LangifyCore.getInstance().getLogger().logInfo(LogInfoType.DEBUG, "Loaded (" + countOfLanguages + ") languages.");

        if (langDataStoreMethod == LangDataStoreMethod.Load_All) {
            for (File file : LangifyCore.getInstance().getFileInterface().getLanguageFiles()) {
                LanguageFile languageFile = new LanguageFile(file);
                Language language = languageFile.getLanguage();

                if (language == null) {
                    LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Language did not load from file. Ignoring language.");
                    availableLanguages.remove(file.getName());
                    return;
                }

                languageKeys.put(file.getName(), languageFile.getLanguage());
            }
        }
    }

    /**
     * Get the language key from the cache or if not in the cache from the config.
     * @param lang
     * @param key
     * @return
     */
    public String getLanguageKey(String lang, String key) {
        if (languageKeys.containsKey(lang)) {
            return languageKeys.get(lang).getKeys().getOrDefault(key, "Langify: Requested key was not found.");
        } else if (availableLanguages.contains(lang)) {

        } else  {

        }
        return "Langify: Requested language was not found.";
    }

    public InputStream getDefaultLanguage() {
        return LangifyCore.class.getResourceAsStream("/en_us.yml");
    }

    /**
     * Returns the LanguageKeyManager instance.
     * @return The LanguageKeyManager instance.
     */
    public static LanguageKeysManager getInstance() {
        return LangifyCore.getInstance().getLanguageKeysManager();
    }

}
