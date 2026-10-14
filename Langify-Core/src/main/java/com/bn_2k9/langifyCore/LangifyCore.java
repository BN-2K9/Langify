package com.bn_2k9.langifyCore;

import com.bn_2k9.langifyCore.framework.interfaces.ConfigInterface;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.managers.FileManager;
import com.bn_2k9.langifyCore.framework.managers.LanguageSelectionManager;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;

public class LangifyCore {

    @Getter
    public static LangifyCore instance;
    @Getter
    public LanguageSelectionManager languageSelectionManager;
    @Getter
    public final LoggerInterface logger;
    @Getter
    public final FileManager fileManager;
    @Getter
    public final ConfigInterface configInterface;

    public LangifyCore(LoggerInterface logger, FileInterface fileInterface, ConfigInterface configInterface) {
        // Register the custom logger.
        this.logger = logger;
        // Register the custom file interface.
        this.fileManager = new FileManager(fileInterface);

        this.configInterface = configInterface;

        configInterface.safeDefaultConfig();

    }

    public void init() {

        // Save the current instance.
        instance = this;
        // Create the languageSelectionManager.
        languageSelectionManager = new LanguageSelectionManager();

        logger.logInfo(LogInfoType.INFO, "Langify core has started.");
    }

}
