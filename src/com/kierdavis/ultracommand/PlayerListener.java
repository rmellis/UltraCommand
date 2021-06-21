package com.kierdavis.ultracommand;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {
  private UltraCommand plugin;
  
  public PlayerListener(UltraCommand paramUltraCommand) {
    this.plugin = paramUltraCommand;
  }
  
  @EventHandler
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent) {
    if (paramPlayerCommandPreprocessEvent.isCancelled())
      return; 
    String[] arrayOfString = paramPlayerCommandPreprocessEvent.getMessage().split(" ");
    if (arrayOfString[0].startsWith("/"))
      arrayOfString[0] = arrayOfString[0].substring(1); 
    if (arrayOfString[0].length() == 0)
      return; 
    boolean bool = this.plugin.doCommand(paramPlayerCommandPreprocessEvent.getPlayer(), arrayOfString);
    paramPlayerCommandPreprocessEvent.setCancelled(bool);
  }
}
