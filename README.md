# SmartCrystalCleaner

**SmartCrystalCleaner** is a lightweight Minecraft plugin that automatically despawns Ender Crystals after a configurable amount of time.  
It is fully compatible with [Folia](https://github.com/PaperMC/Folia) thanks to [FoliaLib](https://github.com/tcoded/folialib).

---

## 🚀 Features

- ⏱️ Automatically removes Ender Crystals after a set number of seconds
- 🌍 Only works in configured worlds
- 🔄 Supports reloading configuration with `/smartcrystalcleaner reload`
- ⚙️ Fully compatible with Folia's asynchronous region scheduling
- 📦 No external dependencies

---

## 📥 Installation

1. Download the plugin `.jar` file from the [Releases](https://github.com/Tuanvo29110/SmartCrystalCleaner/releases) section.
2. Place it into your server’s `plugins/` folder.
3. Start or restart your server.
4. Edit the `config.yml` file as needed.
5. Use `/smartcrystalcleaner reload` to apply config changes without restarting.

---

## ⚙️ Configuration (`config.yml`)

```yaml
# Maximum lifetime of End Crystals (in seconds)
crystal-lifetime-seconds: 300

# List of worlds where End Crystals should be cleaned up
enabled-worlds:
  - world
  - world_nether
  - world_the_end
  - Spawn
