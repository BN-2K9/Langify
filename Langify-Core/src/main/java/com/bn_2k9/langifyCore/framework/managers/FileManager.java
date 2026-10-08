package com.bn_2k9.langifyCore.framework.managers;

import com.bn_2k9.langifyCore.LangifyCore;
import com.bn_2k9.langifyCore.framework.interfaces.FileInterface;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    @Getter
    public FileInterface fileInterface;

    @Getter
    public List<String> fileNames = new ArrayList<>();

    public FileManager(FileInterface fileInterface) {
        this.fileInterface = fileInterface;

        for (File f : fileInterface.getLanguageFiles()) {
            fileNames.add(f.getName());
        }
    }

    public FileManager getInstance() {
        return LangifyCore.getInstance().getFileManager();
    }

}
