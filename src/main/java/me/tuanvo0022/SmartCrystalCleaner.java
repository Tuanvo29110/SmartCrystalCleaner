package me.tuanvo0022;

import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SmartCrystalCleaner extends JavaPlugin implements Listener {

    private FoliaLib foliaLib;
    private int crystalLifetimeSeconds;
    private List<String> enabledWorlds;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        foliaLib = new FoliaLib(this);
        loadConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("SmartCrystalCleaner enabled.");
    }

    @Override
    public void onDisable() {
        foliaLib.getScheduler().cancelAllTasks();
        getLogger().info("SmartCrystalCleaner disabled.");
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        crystalLifetimeSeconds = config.getInt("crystal-lifetime-seconds", 300);
        enabledWorlds = config.getStringList("enabled-worlds");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // Only handle Ender Crystals
        if (!(event.getEntity() instanceof EnderCrystal crystal)) return;

        World world = crystal.getWorld();
        if (!enabledWorlds.contains(world.getName())) return;

        // Schedule removal
        foliaLib.getScheduler().runAtEntityLater(crystal, () -> {
            if (crystal.isValid() && !crystal.isDead()) {
                crystal.remove();
            }
        }, crystalLifetimeSeconds, TimeUnit.SECONDS);
    }

    // Handle /smartcrystalcleaner reload command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("smartcrystalcleaner.reload")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
                return true;
            }

            reloadConfig();
            loadConfig();
            sender.sendMessage(ChatColor.GREEN + "SmartCrystalCleaner config reloaded.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Usage: /" + label + " reload");
        return true;
    }
}