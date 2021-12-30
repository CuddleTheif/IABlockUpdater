package com.cuddletheif.iablockupdater.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lone.itemsadder.api.CustomBlock;


public class IABlockListener implements Listener
{
    private JavaPlugin plugin;

    public IABlockListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemBreak(PlayerItemBreakEvent e){

        

    }

    @EventHandler(ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent e){

        if(!e.isNewChunk()){
            for(int x=0;x<16;x++){
                for(int z=0;z<16;z++){
                    for(int y=-64;y<321;y++){
                        try{
                            Block block = e.getChunk().getBlock(x, y, z);
                            if(block!=null){
                                CustomBlock iaBlock = CustomBlock.byAlreadyPlaced(block);
                                if(iaBlock!=null){
                                    String blockType = iaBlock.getConfig().getString("items."+iaBlock.getId()+".specific_properties.block.placed_model.type");
                                    if((blockType.equals("REAL_NOTE") && block.getType()!=Material.NOTE_BLOCK) ||
                                        (blockType.equals("TILE") && block.getType()!=Material.SPAWNER) ||
                                        (blockType.equals("REAL_TRANSPARENT") && block.getType()!=Material.CHORUS_PLANT && block.getType()!=Material.CHORUS_FRUIT && block.getType()!=Material.CHORUS_FLOWER && block.getType()!=Material.POPPED_CHORUS_FRUIT) ||
                                        (blockType.equals("REAL_WIRE") && block.getType()!=Material.TRIPWIRE) ||
                                        (blockType.equals("FIRE") && block.getType()!=Material.FIRE) ||
                                        (blockType.equals("REAL") && block.getType()!=Material.RED_MUSHROOM_BLOCK && block.getType()!=Material.BROWN_MUSHROOM_BLOCK)){
                                            this.plugin.getLogger().info("IA BLOCK UPDATED:"+iaBlock.getDisplayName()+" FROM "+block.getType()+" TO "+blockType);
                                            iaBlock.place(block.getLocation());
                                    }
                                }
                            }
                        }
                        catch(IllegalArgumentException ex){}
                    }
                }
            }
        }

    }
}
