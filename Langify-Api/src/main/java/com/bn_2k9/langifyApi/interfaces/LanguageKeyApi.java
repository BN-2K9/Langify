package com.bn_2k9.langifyApi.interfaces;

import java.util.UUID;

public interface LanguageKeyApi {

    /**
     * Get the language key for a specific player.
     * @param uuid The player to get the language key for.
     * @param key The language key to get.
     * @return The value of the language key.
     */
    String getLanguageKey(UUID uuid, String key);

    /**
     * Get the language key from the cache or if not in the cache from the config.
     * @param lang the language should be the full file name with extension.
     * @param key the key of the language file that should be retrieved.
     * @return the message that is retuned.
     */
    String getLanguageKey(String lang, String key);

}
