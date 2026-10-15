package com.bn_2k9.langifyPaper.framework;

import com.bn_2k9.langifyPaper.LangifyPaper;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;

@SuppressWarnings("unused")
public class ComponentParser {

    /**
     * Parses a string to a component.
     * @param string The string to parse.
     * @return The component.
     */
    public static Component parse(String string) {

        if (LangifyPaper.getPlaceholderApiEnabled()) {
            string = PlaceholderAPI.setPlaceholders(null, string);
        }

        return MiniMessage.miniMessage().deserialize(string);
    }

    /**
     * Parses a string to a component.
     * @param string The string to parse.
     * @param offlinePlayer the offline player to parse the placeholders for.
     * @return The component.
     */
    public static Component parse(String string, OfflinePlayer offlinePlayer) {

        if (LangifyPaper.getPlaceholderApiEnabled()) {
            string = PlaceholderAPI.setPlaceholders(offlinePlayer, string);
        }

        return MiniMessage.miniMessage().deserialize(string);
    }

}