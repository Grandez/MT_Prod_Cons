package com.sdp.poc.threading.base.config;

public class Config {
    private Config() {}
    private static class ConfigInner    { private static final Config INSTANCE = new Config(); }
    public  static Config getInstance() { return ConfigInner.INSTANCE; }
}
