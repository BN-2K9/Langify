package com.bn_2k9.langifyCore.framework.utils;

public enum LangDataStoreMethod {
    /**
     * Load all load every language entry in to memory. This increases the memory usage but lightens the config file reads.
     */
    Load_All,
    /**
     * Dymnamic load loads every language entry on request. And caches that request. This reduces initial memory usage but increases config file reads.
     */
    Dynamic_Load,
    /**
     * Disable the cache and load it every time from the file itself. (Might actually the worst option. But i thought it would still be a nice to have!)
     */
    Disable_Cache
}
