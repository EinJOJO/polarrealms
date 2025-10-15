package it.einjojo.polarrealms.storage;

import it.einjojo.polarrealms.template.Template;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Uses SSHJ to access files on a remote server like a Hetzner Storage Box.
 */
public class SSHWorldFileStorage implements WorldFileStorage {
    private final Path tempFolder;
    private final String remoteWorkingDirectory;

    /**
     *
     * @param tempFolder             the temporary folder used to store files.
     * @param remoteWorkingDirectory the remote working directory.
     * @throws IllegalArgumentException if parameters are invalid
     */
    public SSHWorldFileStorage(Path tempFolder, String remoteWorkingDirectory) {
        if (remoteWorkingDirectory.trim().isBlank()) {
            throw new IllegalArgumentException("remoteWorkingDirectory cannot be blank");
        }
        this.tempFolder = tempFolder;
        this.remoteWorkingDirectory = withTrailingSlash(remoteWorkingDirectory);
    }


    @Override
    public void createNewWorldFile(Template template) throws IOException {

    }

    @Override
    public void saveWorld(UUID id, byte[] polarWorld) throws IOException {

    }

    @Override
    public byte[] loadWorld(UUID id) throws IOException {
        return new byte[0];
    }

    @Override
    public String storageMethodId() {
        return "ssh";
    }

    private String withTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }
}
