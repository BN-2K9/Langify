package com.bn_2k9.langifyPaper.framework;

import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;
import org.bukkit.Bukkit;

public class Logger implements LoggerInterface {

    /**
     * Log a message to the console. Supports the minimessage format.
     * @param type the type of message being sent.
     * @param message message that needs to be sent.
     */
    @Override
    public void logInfo(LogInfoType type, String message) {

        // Make sure that debug messages are only being sent when enabled.
        if (type == LogInfoType.DEBUG && !ConfigManager.getConfig().getDebug()) {
            return;
        }

        // The console serializer.
        ANSIComponentSerializer serializer = ANSIComponentSerializer.builder()
                .flattener(ComponentFlattener.basic())
                .colorLevel(ColorLevel.TRUE_COLOR)
                .build();

        // Send the message.
        Bukkit.getServer().getConsoleSender().sendMessage(serializer.serialize(MiniMessage.miniMessage().deserialize(ConfigManager.getConfig().getPrefix() + "<green>INFO <dark_grey>>> <white>" + message)));

    }
}
