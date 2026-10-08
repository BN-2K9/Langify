package com.bn_2k9.langifyCore.framework.utils;

public enum LangDataStoreMethod {
    /**
     * Load all load every message key in every language in to memory. This increases the memory usage but lightens the database/config file reads.
     */
    Load_All,
    /**
     * Dymnamic load loads every message on request. And caches that request. This reduces initial memory usage but increases database/config file reads.
     */
    Dynamic_Load
}
