package it.einjojo.polarrealms.template;

import it.einjojo.polarrealms.world.CreationContext;
import it.einjojo.polarrealms.world.RealmProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;


@Data
@NullMarked
public class Template {
    private final String name;
    private @Nullable String permission;
    @Getter(AccessLevel.PROTECTED)
    private final RealmProperties defaultProperties;

    private Supplier<byte[]> polarWorldByteSupplier;

    /**
     * Property factory method. It is used when a realm is created.
     *
     * @param context the creation context
     * @return a JsonObject containing the properties of the template
     */
    public RealmProperties createProperties(CreationContext context) {
        return defaultProperties;
    }

    public byte[] getPolarWorld() {
        return polarWorldByteSupplier.get();
    }


}
