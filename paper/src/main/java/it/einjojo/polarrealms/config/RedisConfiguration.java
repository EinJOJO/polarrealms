package it.einjojo.polarrealms.config;

public record RedisConfiguration(String host, String user, String password, int port) {
    public static RedisConfiguration DEFAULT = new RedisConfiguration("localhost", "default", "", 6379);
}
