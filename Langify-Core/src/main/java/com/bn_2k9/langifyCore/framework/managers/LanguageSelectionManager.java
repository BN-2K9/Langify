package com.bn_2k9.langifyCore.framework.managers;

import java.util.HashMap;
import java.util.UUID;

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
    public boolean loadSelectedLanguageFormDataStore(UUID uuid) {
        return true;
    }
}
