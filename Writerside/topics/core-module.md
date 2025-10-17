# Core and Paper Modules

The project is divided into two main modules: `core` and `paper`. This separation of concerns allows for a clean and extensible architecture.

## Core Module

The `core` module contains the main business logic of the plugin. It is responsible for managing realms, players, and the overall state of the system. This module is designed to be platform-agnostic, meaning it does not contain any code that is specific to a particular Minecraft server implementation (e.g., Paper, Spigot).

Key components of the `core` module include:

- **`PolarRealms`**: The main entry point for the API.
- **`Realm` and `RealmManager`**: Classes for managing realms and their properties.
- **`OnlinePlayerHandle` and `OnlinePlayerHandleFactory`**: Interfaces for managing players in a platform-agnostic way.
- **`RealmStateManager`**: Manages the lifecycle of realms.

## Paper Module

The `paper` module is the implementation of the `core` module for the Paper server platform. It contains all the platform-specific code, such as event listeners, command handlers, and integrations with the Paper API.

Key components of the `paper` module include:

- **`PaperPolarRealms`**: The main plugin class for the Paper platform.
- **`PaperRealmLoader`**: The Paper-specific implementation of the `RealmLoader` interface.
- **Platform-specific implementations**: Implementations of the `OnlinePlayerHandle` and other interfaces from the `core` module.

This modular design makes it easier to maintain and extend the project. For example, if you wanted to add support for a different server platform, you would only need to create a new module that implements the interfaces from the `core` module.