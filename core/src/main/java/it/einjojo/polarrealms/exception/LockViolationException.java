package it.einjojo.polarrealms.exception;

import it.einjojo.polarrealms.host.RealmHost;
import lombok.Getter;

import java.util.UUID;

/**
 * Thrown if a lock operation fails like release or claiming.
 */
@Getter
public class LockViolationException extends RuntimeException {
    private final UUID realmId;
    private final String lockOwner;
    private final RealmHost currentHost;

    public LockViolationException(UUID realmId, RealmHost currentHost, String lockOwner) {
        super(realmId + "-Lock is owned by " + lockOwner + " but current host is " + currentHost.getInternalName());
        this.realmId = realmId;
        this.lockOwner = lockOwner;
        this.currentHost = currentHost;
    }
}
