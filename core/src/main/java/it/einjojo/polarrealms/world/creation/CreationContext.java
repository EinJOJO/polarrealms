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
    @Builder.Default
    @NonNull
    private final UUID realmId = UUID.randomUUID();

    @NonNull
    private final UUID owner;

    @NonNull
    private final Template template;

    /**
     * Merged with the template properties.
     */
    @Nullable
    private final JsonObject customProperties;

}
