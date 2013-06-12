package com.kierdavis.ultracommand;

import com.kierdavis.flex.FlexCommandContext;
import com.kierdavis.flex.FlexHandler;
import org.bukkit.ChatColor;

public class ListCommandHandler {
    private UltraCommand plugin;
    
    public ListCommandHandler(UltraCommand plugin_) {
        plugin = plugin_;
    }
    
    @FlexHandler(value="ultracommand list", permission="ultracommand.configure")
    public boolean doList(FlexCommandContext ctx) {
        if (ctx.numArgs() == 0) {
            Set<String> cmds = plugin.getCustomCommands();
            
            if (cmds.size() == 0) {
                getSender().sendMessage(ChatColor.YELLOW + "No defined commands.");
            }
            
            else {
                Iterator<String> it = cmds.iterator();
                getSender().sendMessage(ChatColor.YELLOW + "Defined commands:");
                
                while (it.hasNext()) {
                    String name = (String) it.next();
                    getSender().sendMessage("  " + ChatColor.YELLOW + "- " + ChatColor.GREEN + name);
                }
            }
            
            return true;
        }
        
        else {
            String name = ctx.getArg(0);
            
            if (!plugin.hasCustomCommand(name)) {
                getSender().sendMessage(ChatColor.YELLOW + "Command " + ChatColor.GREEN + name + ChatColor.YELLOW + " does not exist.");
                return false;
            }
            
            List<String> text = plugin.getText(name);
            List<String> chat = plugin.getChat(name);
            List<String> playerCommands = plugin.getPlayerCommands(name);
            List<String> consoleCommands = plugin.getConsoleCommands(name);
            String usage = plugin.getUsage(name);
            
            getSender().sendMessage(ChatColor.GREEN + name + ChatColor.YELLOW + ":");
            
            if (text == null || text.size() == 0) {
                getSender().sendMessage("  " + ChatColor.YELLOW + "No text for this command.");
            }
            else {
                getSender().sendMessage("  " + ChatColor.YELLOW + "Text:");
                for (int i = 0; i < text.size(); i++) {
                    getSender().sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', text.get(i)));
                }
            }
            
            if (chat == null || chat.size() == 0) {
                getSender().sendMessage("  " + ChatColor.YELLOW + "No chat for this command.");
            }
            else {
                getSender().sendMessage("  " + ChatColor.YELLOW + "Chat:");
                for (int i = 0; i < chat.size(); i++) {
                    getSender().sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', chat.get(i)));
                }
            }
            
            if (playerCommands == null || playerCommands.size() == 0) {
                getSender().sendMessage("  " + ChatColor.YELLOW + "No player commands for this command.");
            }
            else {
                getSender().sendMessage("  " + ChatColor.YELLOW + "Player commands:");
                for (int i = 0; i < playerCommands.size(); i++) {
                    getSender().sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + playerCommands.get(i));
                }
            }
            
            if (consoleCommands == null || consoleCommands.size() == 0) {
                getSender().sendMessage("  " + ChatColor.YELLOW + "No console commands for this command.");
            }
            else {
                getSender().sendMessage("  " + ChatColor.YELLOW + "Console commands:");
                for (int i = 0; i < consoleCommands.size(); i++) {
                    getSender().sendMessage("    " + ChatColor.YELLOW + "- " + ChatColor.GREEN + consoleCommands.get(i));
                }
            }
            
            if (usage == null || usage.length() == 0) {
                getSender().sendMessage("  " + ChatColor.YELLOW + "No usage text for this command.");
            }
            else {
                getSender().sendMessage("  " + ChatColor.YELLOW + "Usage text: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', usage));
            }
            
            return true;
        }
    }
}
