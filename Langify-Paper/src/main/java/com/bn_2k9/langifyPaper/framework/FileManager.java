package com.bn_2k9.langifyPaper.framework;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import com.bn_2k9.langifyCore.framework.utils.LogInfoType;
import com.bn_2k9.langifyPaper.LangifyPaper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class FileManager implements FileInterface {

    File langFolder = new File(LangifyPaper.getInstance().getDataFolder() + "/languages/");

    @Override
    public List<File> getLanguageFiles() {
        File folder = langFolder;
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }
        return Arrays.stream(files).toList();
    }

    @Override
    public void generateDefaults() {
        // Make sure that the language directory exists.
        if (!langFolder.exists()) {
            boolean success = langFolder.mkdirs();
            if (!success) {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Failed to create languages folder.");
            }

            try {
                Files.copy(LangifyCore.getInstance().getLanguageKeysManager().getDefaultLanguage(), new File(langFolder.getPath() + "/en_us.yml").toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LangifyPaper.getInstance().getPluginLogger().logInfo(LogInfoType.ERROR, "Failed to create default language file.");
            }
        }
    }

}
