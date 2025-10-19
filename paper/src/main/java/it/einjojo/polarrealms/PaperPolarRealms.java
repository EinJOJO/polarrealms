package it.einjojo.polarrealms;

import com.google.common.base.Preconditions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.Credentials;
import it.einjojo.polarrealms.command.CommandManager;
import it.einjojo.polarrealms.config.RealmsGlobalConfiguration;
import it.einjojo.polarrealms.repository.TemplateRepository;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.nio.file.Files;

public class PaperPolarRealms extends JavaPlugin {
    private MiniMessage miniMessage;
    private RealmsGlobalConfiguration config;
    @Getter
    private TemplateRepository templateRepository;

    @Getter
    private static PaperPolarRealms instance;

    private HikariDataSource dataSource;

    @Override
    public void onLoad() {

        try {
            if (Files.notExists(getDataPath())) {
                Files.createDirectory(getDataPath());
            }
            config = RealmsGlobalConfiguration.load(getDataPath().resolve("config.json"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        setupHikari();
        flywayPostgres();
        new CommandManager(this);
    }

    @Override
    public void onDisable() {
        if (dataSource != null) {
            dataSource.close();
            getSLF4JLogger().info("Closed database connection");
        }
    }

    private void setupHikari() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setCredentials(Credentials.of("developer", "27IwIQMBBCauFxdrcaXmDjj94CPz"));
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/development");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setSchema("polarrealms");
        dataSource = new HikariDataSource(hikariConfig);
    }

    private void flywayPostgres() {
        Preconditions.checkNotNull(dataSource);
        Flyway flyway = Flyway.configure(getClassLoader())
                .dataSource(dataSource)
                .locations("classpath:db/")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }

    public static MiniMessage miniMessage() {
        return instance.miniMessage;
    }
}
