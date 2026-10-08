package com.bn_2k9.langifyCore.framework.interfaces;

import com.bn_2k9.langifyCore.framework.utils.LogInfoType;

@SuppressWarnings("unused")
public interface LoggerInterface {

    /**
     * Info logger that can be overwritten for any platform.
     * @param type The type of log info.
     * @param message The message.
     */
    void logInfo(LogInfoType type, String message);

}
