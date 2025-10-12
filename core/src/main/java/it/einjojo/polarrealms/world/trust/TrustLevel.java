package it.einjojo.polarrealms.world.trust;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The trust level describes the access level of a {@link it.einjojo.polarrealms.player.TrustedPlayer} inside a realm.
 */
@Getter
@AllArgsConstructor
public enum TrustLevel {
    /**
     * Only when a realm owner is on the realm.
     */
    GUEST(0),

    /**
     * Only when the realm owner is on the network
     */
    ADDED(1),
    /**
     * Can take actions without restrictions
     */
    ALWAYS(2);
    private final int id;

    public static TrustLevel fromId(int id) {
        return switch (id) {
            case 0 -> GUEST;
            case 1 -> ADDED;
            case 2 -> ALWAYS;
            default -> throw new IllegalArgumentException("No trust level with id " + id);
        };
    }
}
