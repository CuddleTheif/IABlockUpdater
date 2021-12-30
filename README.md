# IABlockUpdater
Spigot Plugin that updates IA Blocks if they are the wrong type in world. Checks blocks to see if they are ItemsAdder blocks but they are the wrong type in world (i.e. a spawner when they are now set to REAL_NOTE) and if they are replaces them to update them.

# Commands

## /iab on
Makes the plugin check every block when a not new chunk loads to see if it needs to be updated (PERFORMANCE HEAVY)

## /iab off
Makes the plugin no longer check every block when a not new chunk loads

## /iab force
Makes the plugin check every block in all currently loaded chunks to see if they needed to be updated

## /iab reload
Reloads the config file

# Config

## auto-update
If the IABlockUpdater so check every block when a chunk loads to see if it needs to be updated. (PERFORMANCE HEAVY)
