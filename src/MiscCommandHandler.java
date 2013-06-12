package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;
import org.bukkit.ChatColor;

public class MiscCommandHandler {
    private UltraCommand plugin;
    
    public MiscCommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand reload", permission="ultracommand.configure")
    public boolean doReload(FlexCommandContext ctx) {
        plugin.loadCustomCommands();
        ctx.getSender().sendMessage(ChatColor.YELLOW + "Commands configuration reloaded.");
        return true;
    }
    
    @FlexHandler(value="ultracommand save", permission="ultracommand.configure")
    public boolean doSave(FlexCommandContext ctx) {
        plugin.saveCustomCommands();
        ctx.getSender().sendMessage(ChatColor.YELLOW + "Commands configuration saved.");
        return true;
    }
}
