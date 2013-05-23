package com.kierdavis.ultracommand;

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
        
        if (parts[0].startsWith("/")) parts[0] = parts[0].substring(1);
        if (parts[0].length() == 0) return;
        
        boolean success = plugin.doCommand(event.getPlayer(), parts);
        
        event.setCancelled(success); // If we succeeded, block the event from further processing.
    }
}
