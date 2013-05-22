package com.kierdavis.ultracommand;

import java.util.Arrays;
import org.bukkit.ChatColor;
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
        
        Player player = event.getPlayer();
        String cmdStr = event.getMessage();
        String[] parts = cmdStr.split(" ");
        String cmdName = parts[0].substring(1);
        CustomCommand cmd = plugin.getCustomCommand(cmdName);
        
        if (cmd != null) {
            event.setCancelled(true);
            
            String perm = "ultracommand.commands." + cmdName;
            if (!player.hasPermission(perm) && !player.hasPermission("ultracommand.commands.*")) {
                player.sendMessage(ChatColor.YELLOW + "You don't have permission for this command (" + perm + ")");
                return;
            }
            
            cmd.execute(player, Arrays.copyOfRange(parts, 1, parts.length));
        }
    }
}
