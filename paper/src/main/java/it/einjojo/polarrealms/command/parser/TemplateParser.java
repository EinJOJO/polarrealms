package it.einjojo.polarrealms.command.parser;

import it.einjojo.polarrealms.repository.TemplateRepository;
import it.einjojo.polarrealms.template.Template;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class TemplateParser implements ArgumentParser.FutureArgumentParser<Source, Template>, SuggestionProvider<Source> {
    private final TemplateRepository templateRepository;

    public TemplateParser(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public static ParserDescriptor<Source, Template> descriptor(TemplateRepository templateRepository) {
        return ParserDescriptor.of(new TemplateParser(templateRepository), Template.class);
    }

    @Override
    public CompletableFuture<ArgumentParseResult<Template>> parseFuture(CommandContext<Source> commandContext, CommandInput commandInput) {
        return CompletableFuture.supplyAsync(() -> {

        });
    }

    @Override
    public CompletableFuture<? extends Iterable<? extends Suggestion>> suggestionsFuture(CommandContext<Source> context, CommandInput input) {
        return CompletableFuture.supplyAsync(() -> {

        });
    }
}
