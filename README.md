<p align="center" style='text-align: center; margin-top: 2rem'>
    <img alt="Polar Realms" height="200" src="./Writerside/images/icon.svg" width="200"/>
</p>
<h1 align="center">Polar Realms - WIP 🏝️</h1>



Polar Realms is a work-in-progress world management plugin for Paper Minecraft servers. While initially designed for use
on wandoria.net, this plugin is built to be highly extendable and adaptable to other server environments. Its primary
goal is to provide a robust system for managing world data and properties across multiple servers.

## 🛠️ Features

This plugin aims to provide a comprehensive set of features for managing world data. The core functionality is being
developed with multi-server compatibility and a flexible property system in mind.

#### Multi-Server Compatibility

This feature allows the plugin to handle world data seamlessly across multiple interconnected servers.

- SFTP/SSH Storage: Utilizes the PolarPaper format to store world data in compact byte-files, enabling efficient and
  reliable storage and retrieval across different servers.

- PostgreSQL Storage: Planned support for a PostgreSQL database to provide an alternative, more scalable storage
  solution for world data.

#### Property System

This system provides a structured way to define and manage custom properties for different worlds.
This will be useful when writing plugins that want to read and write realm-related data.

## 🧩 Dependencies

This project relies on several key dependencies to function correctly.

- PolarPaper: A crucial dependency that provides a very small world format for efficient data storage. You can find more
  information on their [GitHub page](https://github.com/MinehubMC/PolarPaper).

- Redis: Used for high-speed, in-memory data caching => State management and world data retrieval?

- PostgreSQL: The planned database solution for scalable world data storage.

## 🤝 Contributing

Contributions are welcome! Please feel free to open a pull request or submit an issue to report bugs or suggest new
features.

## 📚 Documentation

The official documentation for this project is being developed using _Writerside_.
It is not hosted yet and has not been written yet.