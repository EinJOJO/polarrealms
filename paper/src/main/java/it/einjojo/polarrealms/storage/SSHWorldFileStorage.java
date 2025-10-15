package it.einjojo.polarrealms.storage;

import it.einjojo.polarrealms.template.Template;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.xfer.scp.SCPFileTransfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Uses SSHJ to access files on a remote server like a Hetzner Storage Box.
 */
public class SSHWorldFileStorage implements WorldFileStorage {
    private final Path tempFolder;
    private final String remoteWorkingDirectory;
    private final SSHClient sshClient;

    /**
     *
     * @param tempFolder             the temporary folder used to store files.
     * @param remoteWorkingDirectory the remote working directory.
     * @throws IllegalArgumentException if parameters are invalid
     */
    public SSHWorldFileStorage(Path tempFolder, String remoteWorkingDirectory, SSHClient sshClient) {
        this.sshClient = sshClient;
        if (remoteWorkingDirectory.trim().isBlank()) {
            throw new IllegalArgumentException("remoteWorkingDirectory cannot be blank");
        }
        this.tempFolder = tempFolder;
        this.remoteWorkingDirectory = withTrailingSlash(remoteWorkingDirectory);
    }


    public void createNewWorldFile(UUID realmId, Template template) throws IOException {

    }

    @Override
    public void saveWorld(UUID id, byte[] polarWorld) throws IOException {

    }

    @Override
    public byte[] loadWorld(UUID id) throws IOException {
        String remotePath = new File(remoteWorkingDirectory, id.toString()).toString();
        SCPFileTransfer scp = sshClient.newSCPFileTransfer();
        Path downloadedBinaryFile = tempFolder.resolve(id.toString() + System.currentTimeMillis() + ".polar");
        scp.download(remotePath, downloadedBinaryFile.toFile().getAbsolutePath());
        byte[] data = Files.readAllBytes(downloadedBinaryFile);
        Files.deleteIfExists(downloadedBinaryFile);
        return data;
    }

    @Override
    public String storageMethodId() {
        return "ssh";
    }

    private String withTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }
}
