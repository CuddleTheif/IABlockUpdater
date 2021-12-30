package com.cuddletheif.iablockupdater;

import com.cuddletheif.iablockupdater.listeners.IABlockListener;

import org.bukkit.plugin.java.JavaPlugin;


public class IABlockUpdater extends JavaPlugin
{

    @Override
    public void onEnable() {
        getLogger().info(this.getDataFolder().getAbsolutePath());
        new IABlockListener(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }

}
