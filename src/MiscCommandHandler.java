package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;

public class MiscCommandHandler {
    private UltraCommand plugin;
    
    public MiscCommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand reload", permission="ultracommand.configure")
    private boolean doReload(FlexCommandContext ctx) {
        plugin.loadCustomCommands();
        sender.sendMessage(ChatColor.YELLOW + "Commands configuration reloaded.");
        return true;
    }
    
    @FlexHandler(value="ultracommand save", permission="ultracommand.configure")
    private boolean doSave(FlexCommandContext ctx) {
        plugin.saveCustomCommands();
        sender.sendMessage(ChatColor.YELLOW + "Commands configuration saved.");
        return true;
    }
}
