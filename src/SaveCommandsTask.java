package com.kierdavis.ultracommand;

import org.bukkit.scheduler.BukkitRunnable;

public class SaveCommandsTask extends BukkitRunnable {
    private UltraCommand plugin;
    
    public SaveCommandsTask(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    public void run() {
        if (plugin.isDirty()) {
            plugin.saveCustomCommands();
        }
    }
}
