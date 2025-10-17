# Online Player Abstraction


The `OnlinePlayerHandle` and `OnlinePlayerHandleFactory` are key components for managing players in a platform-agnostic way.

## OnlinePlayerHandle

This is a marker interface that represents a player who is currently online. It provides a common type for all platform-specific player objects, allowing for a more abstract and flexible implementation.

## OnlinePlayerHandleFactory

The `OnlinePlayerHandleFactory` is responsible for creating instances of `OnlinePlayerHandle`. It provides two methods for creating a handle:

- `createHandle(UUID uuid)`: This method creates a handle for a player using their UUID. It may perform additional checks to ensure the player is online.
- `createHandle(Object platformPlayerObject)`: This is the preferred method for creating a handle. It takes a platform-specific player object (e.g., `org.bukkit.entity.Player` on Paper) and returns an `OnlinePlayerHandle` instance.

This factory design allows for a clean separation of concerns, where the core module can work with the abstract `OnlinePlayerHandle` without needing to know the specifics of the underlying platform.