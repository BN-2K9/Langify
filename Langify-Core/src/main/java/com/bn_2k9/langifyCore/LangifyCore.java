package com.bn_2k9.langifyCore;

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
    public LoggerInterface logger;
    @Getter
    public FileManager fileManager;

    public void init(LoggerInterface logger, FileInterface fileInterface) {

        // Save the current instance.
        instance = this;
        // Create the languageSelectionManager.
        languageSelectionManager = new LanguageSelectionManager();
        // Register the custom logger.
        this.logger = logger;
        // Register the custom file interface.
        this.fileManager = new FileManager(fileInterface);

        logger.logInfo(LogInfoType.INFO, "Langify core has started.");
    }

}
