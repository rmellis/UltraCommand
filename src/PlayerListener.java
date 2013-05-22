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
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;
        
        String cmdStr = event.getMessage();
        String[] parts = cmdStr.split(" ");
        String cmdName = parts[0].substring(1);
        CustomCommand cmd = plugin.getCustomCommand(cmdName);
        
        if (cmd != null) {
            cmd.execute(event.getPlayer(), Arrays.copyOfRange(parts, 1, parts.length));
            event.setCancelled(true);
        }
    }
}
