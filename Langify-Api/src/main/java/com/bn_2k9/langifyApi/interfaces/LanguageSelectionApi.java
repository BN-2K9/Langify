package com.bn_2k9.langifyApi.interfaces;

import java.util.UUID;

@SuppressWarnings("unused")
public interface LanguageSelectionApi {

    /**
     * Sets a selected language for a player.
     * @param uuid the player to set the language to.
     * @param language the language to set for the player.
     */
    void setSelectedLanguageForPlayer(UUID uuid, String language);

    /**
     * Gets the selected language for a player.
     * @param uuid The player to get the selected language from.
     * @return The selected language filename.
     */
    String getLanguageForPlayer(UUID uuid);
}
