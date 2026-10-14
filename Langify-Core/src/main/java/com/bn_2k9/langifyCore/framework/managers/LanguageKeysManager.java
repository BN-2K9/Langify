package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.framework.utils.LangDataStoreMethod;

import java.util.HashMap;

public class LanguageKeysManager {

    /**
     * The method selected for loading the languageKeys
     */
    LangDataStoreMethod langDataStoreMethod;

    /**
     * A has map for all the languageKeys, So: The first string is the Language_Name, The second hashmap is the key, entry.
     */
    HashMap<String, HashMap<String, String>> languageKeys = new HashMap<>();

    public void loadLanguageKeys() {
        if (langDataStoreMethod == LangDataStoreMethod.Load_All) {

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
            return languageKeys.get(lang).getOrDefault(key, "Langify: Requested key was not found.");
        } else if (FileManager.getInstance().fileNames.contains(lang)) {

        } else  {
            return "Langify: Requested language was not found.";
        }
        return "Langify: Requested language was not found.";
    }

}
