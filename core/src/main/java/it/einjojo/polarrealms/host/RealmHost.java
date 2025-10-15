package it.einjojo.polarrealms.host;

/**
 * Implemented by a singleton instance on the host system to identify it.
 */

public interface RealmHost {

    /**
     * @return the name of the server running the host system which also used inside velocity's server configuration.l
     */
    String getInternalName();

}
