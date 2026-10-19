package com.bn_2k9.langifyPaper.framework.commands;

import com.bn_2k9.langifyPaper.framework.gui.LanguageSelectionGui;
import com.bn_2k9.langifyPaper.framework.gui.utils.GuiMenuUtil;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

@SuppressWarnings("unused")
public class LanguageCommand {

    @Command({"language", "lang"})
    @Description("A command to select your language!")
    public void language(BukkitCommandActor actor) {
        if (!actor.isPlayer()) {
            return;
        }

        new LanguageSelectionGui(new GuiMenuUtil(actor.asPlayer())).open();
    }

}
