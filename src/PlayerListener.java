package com.kierdavis.ultracommand;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {
    private UltraCommand plugin;
    
    public PlayerListener(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;
        
        String[] parts = event.getMessage().split(" ");
        boolean success = plugin.doCommand(event.getPlayer(), parts);
        
        event.setCancelled(success); // If we succeeded, block the event from further processing.
    }
}
