package com.kierdavis.ultracommand;

import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {
    private UltraCommand plugin;
    
    public PlayerListener(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        plugin.getLogger().info(event.getPlayer().getName() + ": " + event.getMessage());
        
        if (event.isCancelled()) return;
        
        String cmdStr = event.getMessage();
        String[] parts = cmdStr.split(" ");
        String cmdName = parts[0].toLowerCase().substring(1);
        
        plugin.getLogger().info("Command: " + cmdStr);
        plugin.getLogger().info("Name: " + cmdName);
        
        if (plugin.commands.containsKey(cmdName)) {
            CustomCommand cmd = plugin.commands.get(cmdName);
            cmd.execute(event.getPlayer(), Arrays.copyOfRange(parts, 1, parts.length));
            event.setCancelled(true);
        }
    }
}
