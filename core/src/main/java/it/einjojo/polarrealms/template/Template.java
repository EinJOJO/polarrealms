package it.einjojo.polarrealms.template;

import it.einjojo.polarrealms.world.RealmProperties;
import it.einjojo.polarrealms.world.creation.CreationContext;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;


@Data
public class Template {

    private final String name;

    private String permission;
    @Getter(AccessLevel.PROTECTED)
    private final RealmProperties defaultProperties;

    /**
     * Property factory method. It is used when a realm is created.
     *
     * @param context the creation context
     * @return a JsonObject containing the properties of the template
     */
    public RealmProperties createProperties(CreationContext context) {
        return defaultProperties;
    }


}
