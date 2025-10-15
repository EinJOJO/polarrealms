package it.einjojo.polarrealms.template;

import it.einjojo.polarrealms.world.RealmProperties;
import it.einjojo.polarrealms.world.creation.CreationContext;
import lombok.Data;

@Data
public class Template {
    private final String name;
    private String permission;
    private final RealmProperties defaultProperties;

    /**
     * Property factory method. It is used when a realm is created.
     *
     * @param context the creation context
     * @return a JsonObject containing the properties of the template
     */
    public RealmProperties createProperties(CreationContext context) {

    }


}
