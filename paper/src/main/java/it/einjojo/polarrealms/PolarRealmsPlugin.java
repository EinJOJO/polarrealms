package it.einjojo.polarrealms;

import com.google.common.base.Preconditions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
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

public class PolarRealmsPlugin extends JavaPlugin {
    private MiniMessage miniMessage = MiniMessage.miniMessage();
    private RealmsGlobalConfiguration config;
    @Getter
    private TemplateRepository templateRepository;

    @Getter
    private static PolarRealmsPlugin instance;

    @Getter
    private PolarRealms api;
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
        if (!setupHikari()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
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

    private boolean setupHikari() {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setCredentials(Credentials.of("developer", "27IwIQMBBCauFxdrcaXmDjj94CPz"));
            hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/development");
            hikariConfig.setDriverClassName("org.postgresql.Driver");
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setSchema("polarrealms");
            dataSource = new HikariDataSource(hikariConfig);
            getSLF4JLogger().info("Verbindung zur Datenbank hergestellt");
            return true;
        } catch (HikariPool.PoolInitializationException ex) {
            getSLF4JLogger().error("""
                    Datenbank verbindung konnte nicht hergestellt werden.
                    => {}
                    
                    1. Prüfe deine PostgreSQL Config
                    2. Ist der Datenbank Server erreichbar?
                    3. Wende dich an den Support.
                    """, ex.getMessage());
            return false;
        }
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
