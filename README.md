# Langify

![bStats Servers](https://img.shields.io/bstats/servers/34700)
![bStats Players](https://img.shields.io/bstats/players/34700)
[![Latest Release](https://img.shields.io/github/v/release/BN_2K9/Langify)](https://github.com/BN_2K9/Langify/releases)
[![GitHub Issues](https://img.shields.io/github/issues/BN_2K9/Langify)](https://github.com/BN_2K9/Langify/issues)
[![License](https://img.shields.io/github/license/BN_2K9/Langify)](https://github.com/BN_2K9/Langify)

---

A plugin that adds multi-language support to your Minecraft server!

You might ask you're self, Why not just use the Minecraft Language system?

- Langify offers [MiniMessage](https://docs.papermc.io/adventure/minimessage/) support.
- Langify is able to parse messages to other plugins.
- Langify does not take up any space in your resource pack.

## Features

---

- [MiniMessage](https://docs.papermc.io/adventure/minimessage/) support.
- Automatic Language file completion check. _Automatically check against the completion of you language files against the default language._
- Lightweight.

#### Supported plugins

| Plugin                                                       | Description                 | 
|--------------------------------------------------------------|-----------------------------|
| [PlaceholderApi](https://modrinth.com/plugin/placeholderapi) | Placeholder parsing support |

## Installation

---

1. 🌐 [Download](https://modrinth.com/project/langify) the plugin.
2. 🗃️ Put the plugin in your `plugins` folder.
3. ⌚ Restart the server.
4. ✅ Langify should have created all the required files.

## Configuration

---

## [PlaceholderApi](https://modrinth.com/plugin/placeholderapi) Placeholders

---
| Placeholder                           | Description                                                                                            |
|---------------------------------------|--------------------------------------------------------------------------------------------------------|
| `%langify_key:(Key path)%`            | Returns a string of the language key. (A player is required to get the key from the correct langauge.) |
| `%langify_key:(Key path):(language)%` | Returns a string of the language key.                                                                  |
| `%langify_selectedLang%`              | Returns the selected language of the player.                                                           |

## Developer Information

---

### Used Libraries

---

#### 🌍 Core

| Libary                                                  | Description                          |
|---------------------------------------------------------|--------------------------------------|
| [HikariCP](https://github.com/brettwooldridge/hikaricp) | Used for the MySql DataStoreType.    |
| [SnakeYaml](https://github.com/snakeyaml/snakeyaml)     | Used for parsing the language files. |
| [SqLite-jdbc](https://github.com/xerial/sqlite-jdbc)    | Used for accesing SqLite databases.  |

#### 📃 Paper Plugin

| Libary                                                             | Description                                     |
|--------------------------------------------------------------------|-------------------------------------------------|
| [bStats](https://bstats.org/)                                      | Used for plugin statistics. (No sensetive data) |
| [jackson-databind](https://github.com/fasterxml/jackson-databind)  | Used for api communication with Modrinth.       |
| [PlaceholderApi](https://github.com/PlaceholderAPI/PlaceholderAPI) | Used for placeholder parsing.                   |

### Interacting with the api

---
