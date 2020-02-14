package com.github.cjqcn.tinyredis.core.command;

public enum CommandType {
    error(0, "error"),
    get(1, "get"),
    set(2, "set"),
    select(3, "select"),

    ;

    private int code;
    private String desc;

    CommandType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
