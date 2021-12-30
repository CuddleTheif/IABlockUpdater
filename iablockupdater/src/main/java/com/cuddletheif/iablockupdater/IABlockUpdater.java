package com.cuddletheif.iablockupdater;

import java.util.ArrayList;
import java.util.List;

import com.cuddletheif.iablockupdater.listeners.IABlockListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class IABlockUpdater extends JavaPlugin
{
    IABlockListener listener;
    FileConfiguration config;

    @Override
    public void onEnable() {

        // Get and update the config
        this.saveDefaultConfig();
        this.config = this.getConfig();

        listener = new IABlockListener(this, this.config.getBoolean("auto-update", true));
    }

    /**
     * Handle a command of this plugin
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        // Check which subcommand it is
        if(args[0].equals("on")){

            listener.setOn(true);
            this.config.set("auto-update", true);
            this.saveConfig();
            sender.sendMessage("Turned ON the auto updates for IABlockUpdater");
            return true;

        }
        else if(args[0].equals("off")){

            listener.setOn(false);
            this.config.set("auto-update", false);
            this.saveConfig();
            sender.sendMessage("Turned OFF the auto updates for IABlockUpdater");
            return true;

        }
        else if(args[0].equals("force")){

            sender.sendMessage("Force updating all IA Blocks in currently loaded Chunks");
            listener.updateAllBlocks();
            return true;

        }
        else if(args[0].equals("reload")){

            this.reloadConfig();
            this.saveDefaultConfig();
            this.config = this.getConfig();
            listener.setOn(this.config.getBoolean("auto-update", true));
            sender.sendMessage("Reloaded the config file");
            return true;

        }
        return false;

    }

    
    /**
     * Give tab list of on, off, force
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        List<String> completions = new ArrayList<String>();

        // tab complete based on the current args
        if(args.length<=1){
            if(args.length==0 || args[0].trim().equals("")){
                completions.add("on");
                completions.add("off");
                completions.add("force");
                completions.add("reload");
            }
            else if("on".startsWith(args[0].trim()))
                completions.add("on");
            else if("off".startsWith(args[0].trim()))
                completions.add("off");
            else if("force".startsWith(args[0].trim()))
                completions.add("force");
            else if("force".startsWith(args[0].trim()))
                completions.add("reload");
        }

        return completions;
    }
    
}
