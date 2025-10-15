package it.einjojo.polarrealms.world.creation;

import com.google.gson.JsonObject;
import it.einjojo.polarrealms.template.Template;
import lombok.Builder;
import lombok.Data;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Data
@Builder
public class CreationContext {

    public static String RANDOM_REALM_NAME = "__RANDOM__";

    @Builder.Default
    @NonNull
    private final UUID realmId = UUID.randomUUID();

    @NonNull
    private final UUID owner;

    @NonNull
    private final Template template;

    @Builder.Default
    private final String realmName = RANDOM_REALM_NAME;

    /**
     * Merged with the template properties.
     */
    @Nullable
    private final JsonObject customProperties;

}
