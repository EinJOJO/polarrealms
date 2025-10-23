
package it.einjojo.polarrealms.command.parser;

import com.google.gson.Gson;
import it.einjojo.polarrealms.PolarRealmsPlugin;
import it.einjojo.polarrealms.world.RealmProperties;
import it.einjojo.polarrealms.world.RealmWorld;
import org.bukkit.entity.Player;
import org.incendo.cloud.bukkit.parser.PlayerParser;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.parser.aggregate.AggregateParser;
import org.incendo.cloud.parser.standard.StringParser;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

/**
 * A realm-world argument is expected to be two elements.
 * <p>First the realm owner, second optionally the realm name.</p>
 */
@NullMarked
public class RealmWorldParser {


    public static ParserDescriptor<Source, RealmWorld> descriptor() {
        AggregateParser<Source, RealmWorld> parser = AggregateParser.<Source>builder()
                .withComponent("owner", PlayerParser.playerParser())
                .withComponent("alias", StringParser.quotedStringParser())
                .withMapper(RealmWorld.class, (commandContext, context) -> {
                    Player player = context.get("owner");
                    String alias = context.getOrDefault("alias", "default");
                    return ArgumentParseResult.successFuture(
                            new RealmWorld(UUID.randomUUID(),
                                    player.getUniqueId(),
                                    alias,
                                    System.currentTimeMillis(),
                                    System.currentTimeMillis(),
                                    new RealmProperties(new Gson()),
                                    PolarRealmsPlugin.getInstance().getApi())
                    );
                }).build();
        return ParserDescriptor.of(parser, RealmWorld.class);
    }


}
