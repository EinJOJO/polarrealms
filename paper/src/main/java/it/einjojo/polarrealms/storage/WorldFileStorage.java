package it.einjojo.polarrealms.storage;

import it.einjojo.polarrealms.template.Template;

import java.io.IOException;
import java.util.UUID;

/**
 * Has direct access to template files.
 */
public interface WorldFileStorage {

    void createNewWorldFile(UUID realmId, Template template) throws IOException;

    /**
     * saves the specified polar serialized world data.
     *
     * @param id         the id of the realm world to save
     * @param polarWorld polar serialized world data
     */
    void saveWorld(UUID id, byte[] polarWorld) throws IOException;

    /**
     *
     * @param id the id of the realm world to load
     * @return the polar serialized world data.
     * @throws IOException if an I/O error occurs.
     */
    byte[] loadWorld(UUID id) throws IOException;

    /**
     * implemented by the storage method.
     *
     * @return the storage method id.
     */
    String storageMethodId();
}
