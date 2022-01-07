package com.cuddletheif.iablockupdater.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lone.itemsadder.api.CustomBlock;

/**
 * Listener for checking if IA Blocks need to be updated
 */
public class IABlockListener implements Listener
{
    private JavaPlugin plugin;
    private boolean on;

    /**
     * Creates an IABlockListener for the given plugin with it default on or off
     * 
     * @param plugin The plugin owning this listener
     * @param on If the auto updates should be on or off at start
     */
    public IABlockListener(JavaPlugin plugin, boolean on) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.on = on;
    }

    /**
     * Turns on or off the IABlockListener
     * 
     * @param on If the listener should be on or off
     */
    public void setOn(boolean on){
        this.on = on;
    }


    /**
     * Checks and updates all blocks current in the world
     */
    public void updateAllBlocks(){
        List<World> worlds = Bukkit.getWorlds();
        for(World world : worlds){
            Chunk[] chunks = world.getLoadedChunks();
            for(Chunk chunk : chunks)
                updateChunk(chunk);
        }
    }

    /**
     * If the listener is on update on chunk loads but only if not new
     * @param e The event triggered
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent e){
        if(on && !e.isNewChunk())
            updateChunk(e.getChunk());
    }

    /**
     * Checks and updates all the blocks in the given chunk
     * 
     * @param Block The chunk to check
     */
    private void updateChunk(Chunk chunk) {
        try {
            int highestBlockHeight;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    highestBlockHeight = chunk.getWorld().getHighestBlockYAt(chunk.getX() * 16 + x, chunk.getZ() * 16 + z, HeightMap.WORLD_SURFACE);
                    for (int y = chunk.getWorld().getMinHeight(); y < highestBlockHeight; y++) {
                        Block block = chunk.getBlock(x, y, z);
                        CustomBlock iaBlock = CustomBlock.byAlreadyPlaced(block);
                        if (iaBlock != null) {
                            String blockType = iaBlock.getConfig().getString("items." + iaBlock.getId() + ".specific_properties.block.placed_model.type");
                            assert blockType != null;
                            if ((blockType.equals("REAL_NOTE") && block.getType() != Material.NOTE_BLOCK) ||
                                    (blockType.equals("TILE") && block.getType() != Material.SPAWNER) ||
                                    (blockType.equals("REAL_TRANSPARENT") && block.getType() != Material.CHORUS_PLANT && block.getType() != Material.CHORUS_FRUIT && block.getType() != Material.CHORUS_FLOWER && block.getType() != Material.POPPED_CHORUS_FRUIT) ||
                                    (blockType.equals("REAL_WIRE") && block.getType() != Material.TRIPWIRE) ||
                                    (blockType.equals("FIRE") && block.getType() != Material.FIRE) ||
                                    (blockType.equals("REAL") && block.getType() != Material.RED_MUSHROOM_BLOCK && block.getType() != Material.BROWN_MUSHROOM_BLOCK)) {
                                this.plugin.getLogger().info("IA BLOCK UPDATED:" + iaBlock.getDisplayName() + " FROM " + block.getType() + " TO " + blockType);
                                iaBlock.place(block.getLocation());
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException ignored) {}
    }
}
