package it.einjojo.polarrealms.command;


import it.einjojo.polarrealms.PaperPolarRealms;
import it.einjojo.polarrealms.exception.ComponentException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.brigadier.BrigadierSetting;
import org.incendo.cloud.bukkit.BukkitCommandContextKeys;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.exception.ArgumentParseException;
import org.incendo.cloud.exception.CommandExecutionException;
import org.incendo.cloud.exception.NoPermissionException;
import org.incendo.cloud.exception.handling.ExceptionContext;
import org.incendo.cloud.exception.handling.ExceptionHandler;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CommandManager {
    private final PaperPolarRealms plugin;
    private final PaperCommandManager<Source> commandManager;

    public CommandManager(PaperPolarRealms plugin) {
        this.plugin = plugin;
        commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper()).executionCoordinator(ExecutionCoordinator.coordinatorFor(ExecutionCoordinator.nonSchedulingExecutor())).buildOnEnable(plugin);
        commandManager.brigadierManager().settings().set(BrigadierSetting.FORCE_EXECUTABLE, true);

        registerExceptionControllers(commandManager);


    }

    private void registerExceptionControllers(PaperCommandManager<Source> commandManager) {
        commandManager.exceptionController().registerHandler(CommandExecutionException.class, ExceptionHandler.unwrappingHandler()) // Unwrap the exception and pass it to the next handler
                .registerHandler(ArgumentParseException.class, (ExceptionContext<Source, ArgumentParseException> context) -> {
                    Throwable cause = context.exception().getCause();
                    CommandSender sender = extractSender(context.context());
                    if (cause instanceof ComponentException message) {
                        sender.sendMessage(message);
                    } else {
                        sender.sendMessage(Component.translatable("realms.generic-error"));
                        plugin.getSLF4JLogger().error("An error occurred while parsing arguments for a command", cause);
                    }
                }).registerHandler(ComponentException.class, context -> {
                    CommandSender sender = extractSender(context.context());
                    sender.sendMessage(context.exception());
                }).registerHandler(NoPermissionException.class, context -> {
                    CommandSender sender = extractSender(context.context());
                    sender.sendMessage(Component.translatable("realms.no-permission"));
                });
    }

    public void registerCommands() {
        AnnotationParser<Source> parser = new AnnotationParser<>(commandManager, Source.class);
        try {
            var registeredCommanadsCollection = parser.parseContainers(plugin.getClass().getClassLoader());
            for (var command : registeredCommanadsCollection) {
                plugin.getComponentLogger().info(Component.text("Registered Command: " + command.rootComponent().name(), NamedTextColor.GREEN));
            }
        } catch (Exception e) {
            plugin.getSLF4JLogger().error("Could not register commands", e);
        }
    }

    private CommandSender extractSender(CommandContext<Source> source) {
        return source.get(BukkitCommandContextKeys.BUKKIT_COMMAND_SENDER);
    }

}
