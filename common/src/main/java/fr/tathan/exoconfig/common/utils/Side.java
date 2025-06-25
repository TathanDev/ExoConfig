package fr.tathan.exoconfig.common.utils;

public enum Side {
    CLIENT(false, "client"),
    SERVER(true, "server"),
    COMMON(true, "common");

    public boolean sync;
    public String name;

    Side(Boolean sync, String name) {
        this.sync = sync;
        this.name = name;
    }
}
