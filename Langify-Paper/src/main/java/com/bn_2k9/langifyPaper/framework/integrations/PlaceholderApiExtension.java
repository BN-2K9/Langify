package com.bn_2k9.langifyPaper.framework.integrations;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.managers.LanguageKeysManager;
import com.bn_2k9.langifyPaper.LangifyPaper;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiExtension extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "langify";
    }

    @Override
    public @NotNull String getAuthor() {
        return LangifyPaper.getInstance().getPluginMeta().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return LangifyPaper.getInstance().getPluginMeta().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        // %langify_key:key.key.key% - Player required.
        // %langify_key:lang:key% - No player required.
        String[] args = params.split(":");

        if (args.length >= 1) {
            switch (args[0]) {
                case "key" -> {
                    if (args.length == 2) {
                        if (player != null) {
                            return LanguageKeysManager.getInstance().getLanguageKey(player.getUniqueId(), args[1]);
                        }
                    } else if (args.length == 3) {
                        return LanguageKeysManager.getInstance().getLanguageKey(args[1], args[2]);
                    }
                }
                case "selectedLang" -> {
                    if (player != null) {
                        return LangifyCore.getInstance().getLanguageSelectionManager().getLanguageForPlayer(player.getUniqueId());
                    } else {
                        return "Langify: No player supplied. Please supply a player.";
                    }
                }
                case "percentageTranslated" -> {
                    if (args.length < 3) {
                        return "Langify: No language specified.";
                    }
                    return String.valueOf(LanguageKeysManager.getInstance().getPercentageTranslated().get(args[2]));
                }
                case null, default -> {
                    return "Langify: Not a valid argument.";
                }
            }
        }

        return null;
    }
}
