package io.github.portablestew;

import org.bukkit.plugin.java.*;

import io.github.portablestew.commands.*;

/**
 * Plugin entry point
 */
public final class McSpigotKids extends JavaPlugin {

    // JavaPlugin
    @Override
    public void onEnable() {
        this.getLogger().info("McSpigotKids - plugin enabled.");

        this.getCommand("snap").setExecutor(new SnapExecutor());
        this.getCommand("bigboom").setExecutor(new BigBoomExecutor());
    }

    @Override
    public void onDisable() {
        this.getLogger().warning("McSpigotKids - plugin disabled.");
    }
}
