package com.bn_2k9.langifyCore;

import com.bn_2k9.langifyCore.framework.interfaces.ConfigInterface;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.managers.LanguageKeysManager;
import com.bn_2k9.langifyCore.framework.managers.LanguageSelectionManager;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;

public class LangifyCore {

    @Getter
    private static LangifyCore instance;

    @Getter
    public LanguageSelectionManager languageSelectionManager;
    @Getter
    public LanguageKeysManager languageKeysManager;

    @Getter
    public final LoggerInterface logger;
    @Getter
    public final FileInterface fileInterface;
    @Getter
    public final ConfigInterface configInterface;

    public LangifyCore(LoggerInterface loggerInterface, FileInterface fileInterface, ConfigInterface configInterface) {
        // Save the current instance.
        instance = this;

        // Register the custom logger.
        this.logger = loggerInterface;
        // Register the custom file interface.
        this.fileInterface = fileInterface;
        // Register the config interface.
        this.configInterface = configInterface;

        // Register the languageKeyManager.
        this.languageKeysManager = new LanguageKeysManager();
        // Create the languageSelectionManager.
        languageSelectionManager = new LanguageSelectionManager();

        // Save the default language and create the folder.
        fileInterface.generateDefaults();

        // Save the default config.
        configInterface.safeDefaultConfig();
    }

    public void init() {

        logger.logInfo(LogInfoType.INFO, "Langify core has started.");
    }

}
