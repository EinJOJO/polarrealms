package it.einjojo.polarrealms.event;

import it.einjojo.polarrealms.util.ServerNameDiscovery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Fired before the plugin starts loading or anything from the realm system is initialized.
 * <p>Important for tweaking variables and logic</p>
 * <ul>
 *     <li>Server Name Discovery</li>
 * </ul>
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PrePolarRealmsLoadEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private ServerNameDiscovery serverNameDiscovery;

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
