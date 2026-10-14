package com.bn_2k9.langifyPaper.framework.integrations;

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

        // %langify:key.key.key% - Player required.
        // %langify:lang:key% - No player required.
        String[] args = params.split(":");

        if (args.length == 1) {
            if (player != null) {

            }
        } else if (args.length == 2) {
            return LanguageKeysManager.getInstance().getLanguageKey(args[0], args[1]);
        }


        return null;
    }
}
