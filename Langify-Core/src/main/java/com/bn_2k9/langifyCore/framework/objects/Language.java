package com.bn_2k9.langifyCore.framework.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Language {

    public String name;
    public HashMap<String, String> keys;

    public Language(String name, HashMap<String, String> keys) {
        this.name = name;
        this.keys = keys;
    }

}
