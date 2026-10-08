package com.bn_2k9.langifyCore;

import com.bn_2k9.langifyCore.framework.interfaces.LoggerInterface;
import com.bn_2k9.langifyCore.framework.managers.LanguageSelectionManager;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import lombok.Getter;

public class LangifyCore {

    @Getter
    public static LangifyCore instance;
    @Getter
    public LanguageSelectionManager languageSelectionManager;
    @Getter
    public LoggerInterface logger;

    public void init(LoggerInterface logger) {

        // Save the current instance.
        instance = this;
        // Create the languageSelectionManager.
        languageSelectionManager = new LanguageSelectionManager();
        // Register the custom logger.
        this.logger = logger;

        logger.logInfo(LogInfoType.INFO, "Langify core has started.");
    }

}
