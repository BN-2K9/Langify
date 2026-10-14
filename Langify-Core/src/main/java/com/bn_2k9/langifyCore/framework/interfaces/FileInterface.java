package com.bn_2k9.langifyCore.framework.interfaces;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public interface FileInterface {
    /**
     * Gets the language files.
     * @return a list of the language files.
     */
    List<File> getLanguageFiles();

    /**
     * Generate the default folder / file.
     */
    void generateDefaults();

    /**
     * Get only one Language File.
     * @return the language file.
     */
    File getLanguageFile(String filename);
}
