
package it.einjojo.polarrealms.command.parser;

import it.einjojo.polarrealms.exception.ComponentException;
import it.einjojo.polarrealms.world.RealmWorld;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@NullMarked
public class RealmWorldParser implements ArgumentParser.FutureArgumentParser<Source, RealmWorld>, BlockingSuggestionProvider.Strings<Source> {


    public static ParserDescriptor<Source, RealmWorld> descriptor() {
        return ParserDescriptor.of(new RealmWorldParser(), RealmWorld.class);
    }

    @Override
    public CompletableFuture<ArgumentParseResult<RealmWorld>> parseFuture(CommandContext<Source> commandContext, CommandInput commandInput) {
        final String templateName = commandInput.readString();
        return CompletableFuture.supplyAsync(() -> {
            return ArgumentParseResult.failure(ComponentException.translatable("realms.command.parser.realmworld.not-implemented")); //TODO
        });
    }

    @Override
    public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<Source> commandContext, @NonNull CommandInput input) {
        return List.of("realm1"); //TODO
    }
}
