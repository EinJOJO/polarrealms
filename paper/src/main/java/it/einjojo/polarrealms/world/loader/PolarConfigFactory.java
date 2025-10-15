package it.einjojo.polarrealms.world.loader;

import it.einjojo.polarrealms.world.RealmWorld;
import live.minehub.polarpaper.Config;

public class PolarConfigFactory {
    public static PolarConfigFactory DEFAULT = new PolarConfigFactory();


    public Config createConfig(RealmWorld realmWorld) {
        return Config.DEFAULT;
    }

}
