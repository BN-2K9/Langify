package com.bn_2k9.langifyPaper.framework.commands;

import com.bn_2k9.langifyPaper.LangifyPaper;
import com.bn_2k9.langifyPaper.framework.ComponentParser;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@SuppressWarnings("unused")
public class LangifyCommand {

    @Command("langify")
    @Description("A command to manage langify.")
    @CommandPermission("Langify.Admin")
    public void langify(BukkitCommandActor actor, @Suggest({"reload"}) String arg) {
        if (arg.equals("reload")) {
            LangifyPaper.reloadPlugin();
            actor.sender().sendMessage(ComponentParser.parse("<green>Plugin Reloaded"));
        }
    }

}
