package it.einjojo.polarrealms.host;

/**
 * Implemented on the host system to identify it.
 */

public interface RealmHost {

    /**
     * This name must not change during the lifetime of the host.
     *
     * @return the name of the server running the host system which also used inside velocity's server configuration.
     */
    String getInternalName();

}
