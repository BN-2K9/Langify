package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class LanguageSelectionManager {

    /**
     * The Language selection cache wil hold the selected languages for the loaded players.
     */
    static HashMap<UUID, String> selectedLanguageCache = new HashMap<>();

    /**
     * Loads the selected language from the selected DataStore type.
     * @param uuid The give uuid of the player.
     * @return Returns True if the selected language is loaded. Returns false if it failed to load the selected language. (Might also not have selected a language.)
     */
    public String loadSelectedLanguageFormDataStore(UUID uuid) {

        String selectedLanguage = LangifyCore.getInstance().getDataStoreManager().getSelectedLanguageForPlayer(uuid);
        if (selectedLanguage == null) {
            selectedLanguage = LangifyCore.getInstance().getConfig().getDefaultLanguage();
            LangifyCore.getInstance().getDataStoreManager().setSelectedLanguageForPlayer(uuid, selectedLanguage);
        }

        selectedLanguageCache.put(uuid, selectedLanguage);
        return selectedLanguage;
    }

    /**
     * Sets a selected language for a player.
     * @param uuid the player to set the language to.
     * @param language the language to set for the player.
     */
    public void setSelectedLanguageForPlayer(UUID uuid, String language) {
        if (LangifyCore.getInstance().getLanguageKeysManager().getAvailableLanguages().contains(language)) {
            LangifyCore.getInstance().getDataStoreManager().setSelectedLanguageForPlayer(uuid, language);
            selectedLanguageCache.put(uuid, language);
        } else {
            LangifyCore.getInstance().getLogger().logInfo(LogInfoType.ERROR, "Failed to set language for player: " + uuid.toString() + ", Language: " + language + " is invalid.");
        }
    }

    public String getLanguageForPlayer(UUID uuid) {

        // Check if the selected language is already in the cache.
        if (selectedLanguageCache.containsKey(uuid)) {
            return selectedLanguageCache.get(uuid);
        }

        return loadSelectedLanguageFormDataStore(uuid);
    }

    public static LanguageSelectionManager getInstance() {
        return LangifyCore.getInstance().getLanguageSelectionManager();
    }
}
