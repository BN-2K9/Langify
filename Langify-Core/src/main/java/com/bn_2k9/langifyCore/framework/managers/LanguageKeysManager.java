package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyApi.interfaces.LanguageKeyApi;
import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.objects.Language;
import com.bn_2k9.langifyCore.framework.objects.LanguageFile;
import com.bn_2k9.langifyCore.framework.utils.LangDataStoreMethod;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LanguageKeysManager implements LanguageKeyApi {

    /**
     * A list of available Languages.
     */
    @Getter
    List<String> availableLanguages = new ArrayList<>();

    /**
     * A hashmap for all the languageKeys, So: The first string is the Language_Name, The second hashmap is the key, entry.
     */
    @Getter
    HashMap<String, Language> languageKeys = new HashMap<>();

    /**
     * A hashmap of the percent that is translated for a language in view of the default language.
     */
    @Getter
    HashMap<String, Integer> percentageTranslated = new HashMap<>();

    public void loadLanguageKeys() {

        // Remove the old languages. (for reloading)
        availableLanguages = new ArrayList<>();
        List<File> files = LangifyCore.getInstance().getFileInterface().getLanguageFiles();

        // Get the default language keys.
        HashMap<String, String> defaultLanguageKeys = new HashMap<>();
        for (File file : files) {
            if (file.getName().equals(LangifyCore.getInstance().getConfig().getDefaultLanguage())) {
                defaultLanguageKeys = new LanguageFile(file).getLanguage().getKeys();
            }
        }

        // Put all the new languages in.
        int countOfLanguages = 0;
        for (File file : files) {
            availableLanguages.add(file.getName());
            countOfLanguages++;

            LanguageFile languageFile = new LanguageFile(file);
            Language language = languageFile.getLanguage();

            // Check if the language did serialize.
            if (language == null) {
                LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Language did not load from file. Ignoring language.");
                availableLanguages.remove(file.getName());
                return;
            }

            // Calculate the percentage per language.
            int defaultKeyCount = defaultLanguageKeys.size();
            int languageKeyCount = language.getKeys().size();
            percentageTranslated.put(language.getName(), (languageKeyCount/defaultKeyCount) * 100);

            // If AnnounceMissingParts is enabled print it to the console.
            if (LangifyCore.getInstance().getConfig().getAnnounceMissingParts()) {
                for (String defaultKey : defaultLanguageKeys.keySet()) {
                    if (!language.getKeys().containsKey(defaultKey)) {
                        LangifyCore.getInstance().getLogger().logInfo(LogInfoType.WARNING, "The key: " + defaultKey + " is missing in language: " + language.getName());
                    }
                }
            }

            // Load the keys in to the variable for the selected type.
            if (LangifyCore.getInstance().getConfig().getLangDataStoreMethod() == LangDataStoreMethod.Load_All) {
                languageKeys.put(file.getName(), languageFile.getLanguage());
            } else if (LangifyCore.getInstance().getConfig().getLangDataStoreMethod() == LangDataStoreMethod.Dynamic_Load) {
                // Clear the language keys to load dynamically.
                language.setKeys(new HashMap<>());
                languageKeys.put(file.getName(), languageFile.getLanguage());
            }
        }

        // Print the loaded languages to the console.
        LangifyCore.getInstance().getLogger().logInfo(LogInfoType.DEBUG, "Loaded (" + countOfLanguages + ") languages.");
    }

    /**
     * Get the language key for a specific player.
     * @param uuid The player to get the language key for.
     * @param key The language key to get.
     * @return The value of the language key.
     */
    public String getLanguageKey(UUID uuid, String key) {
        return getLanguageKey(LangifyCore.getInstance().getLanguageSelectionManager().getLanguageForPlayer(uuid), key);
    }

    /**
     * Get the language key from the cache or if not in the cache from the config.
     * @param lang the language should be the full file name with extension.
     * @param key the key of the language file that should be retrieved.
     * @return the message that is retuned.
     */
    public String getLanguageKey(String lang, String key) {
        // Check if the full file name is given. If not see is you can find it with .yml extensions.
        if (!lang.contains(".yml")) {
            if (lang.contains(".")) {
                return "Langify: Invalid file type given.";
            } else {
                lang = lang + ".yml";
            }
        }

        if (LangifyCore.getInstance().getConfig().getLangDataStoreMethod() == LangDataStoreMethod.Disable_Cache) {

            // Load it every time from the file itself. (Might actually be stupid but alright.)
            LanguageFile languageFile = new LanguageFile(LangifyCore.getInstance().getFileInterface().getLanguageFile(lang));
            return languageFile.getString(key);

        } else {
            if (languageKeys.containsKey(lang)) {

                // Load the language key from the cache.
                String message = languageKeys.get(lang).getKeys().get(key);
                if (message == null) {
                    return "Langify: the requested key: " + key + " was not found in language: " + lang;
                } else {
                    return message;
                }

            } else if (availableLanguages.contains(lang)) {

                // Dynamically load the language.
                LanguageFile languageFile = new LanguageFile(LangifyCore.getInstance().getFileInterface().getLanguageFile(lang));
                String message = languageFile.getLanguage().getKeys().get(key);

                // Add the language key to the cached language.
                Language language = languageFile.getLanguage();
                HashMap<String, String> keys = language.getKeys();
                keys.put(key, message);
                language.setKeys(keys);

                languageKeys.put(lang, languageFile.getLanguage());

                if (message == null) {
                    return "Langify: the requested key: " + key + " was not found in language: " + lang;
                } else {
                    return message;
                }

            }
        }

        return "Langify: Requested language: " + lang + " was not found.";
    }

    public InputStream getDefaultLanguage() {
        return LangifyCore.class.getResourceAsStream("/en_us.yml");
    }

    public HashMap<String, Language> getLanguages() {
        switch (LangifyCore.getInstance().getConfig().getLangDataStoreMethod()) {
            case Load_All, Dynamic_Load -> {return getLanguageKeys();}
            case Disable_Cache -> {
                HashMap<String, Language> hashMap = new HashMap<>();
                for (File file : LangifyCore.getInstance().getFileInterface().getLanguageFiles()) {
                    LanguageFile languageFile = new LanguageFile(file);
                    Language language = languageFile.getLanguage();
                    hashMap.put(file.getName(), language);
                }
                return hashMap;
            }
        }
        return null;
    }

    /**
     * Returns the LanguageKeyManager instance.
     * @return The LanguageKeyManager instance.
     */
    public static LanguageKeysManager getInstance() {
        return LangifyCore.getInstance().getLanguageKeysManager();
    }

}
