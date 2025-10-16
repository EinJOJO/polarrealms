package it.einjojo.polarrealms.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Data
public class RealmsGlobalConfiguration {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private RedisConfiguration redis = RedisConfiguration.DEFAULT;


    public static RealmsGlobalConfiguration load(Path jsonConfigFile) throws IOException {
        if (!jsonConfigFile.endsWith(".json")) {
            throw new IllegalArgumentException("The file must be a .json file");
        }
        if (!jsonConfigFile.toFile().exists()) {
            Files.createFile(jsonConfigFile);
            return new RealmsGlobalConfiguration().save(jsonConfigFile);
        }
        try (BufferedReader reader = Files.newBufferedReader(jsonConfigFile)) {
            RealmsGlobalConfiguration config = GSON.fromJson(reader, RealmsGlobalConfiguration.class);
            if (config == null) {
                return new RealmsGlobalConfiguration();
            }
            return config;
        }
    }


    public RealmsGlobalConfiguration save(Path jsonConfigFile) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(jsonConfigFile)) {
            GSON.toJson(this, writer);
        }
        return this;
    }


}
