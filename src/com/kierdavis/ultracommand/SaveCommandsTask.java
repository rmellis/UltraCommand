package com.kierdavis.ultracommand;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveCommandsTask extends BukkitRunnable {
  private UltraCommand plugin;
  
  public SaveCommandsTask(UltraCommand paramUltraCommand) {
    this.plugin = paramUltraCommand;
  }
  
  public void run() {
    if (this.plugin.isDirty())
      this.plugin.saveCustomCommands(); 
  }
}
