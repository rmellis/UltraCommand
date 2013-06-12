package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;

public class CommandHandler {
    private UltraCommand plugin;
    
    public CommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand add", permission="ultracommand.configure")
    public boolean doAdd(FlexCommandContext ctx) {
        ctx.getSender().sendMessage("Hooray, it works!");
        return true;
    }
}
