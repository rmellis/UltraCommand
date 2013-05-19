package com.kierdavis.ultracommand;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Server;

public class CustomCommand {
    private List<String> text;
    private List<String> chat;
    private List<String> commands;
    private List<String> consoleCommands;
    
    public CustomCommand() {
        text = null;
        chat = null;
        commands = null;
        consoleCommands = null;
    }
    
    public void addText(String s) {
        if (text == null) text = new ArrayList<String>();
        text.add(s);
    }
    
    public void addChat(String s) {
        if (chat == null) text = new ArrayList<String>();
        chat.add(s);
    }
    
    public void addCommand(String s) {
        if (commands == null) text = new ArrayList<String>();
        commands.add(s);
    }
    
    public void addConsoleCommand(String s) {
        if (consoleCommands == null) text = new ArrayList<String>();
        consoleCommands.add(s);
    }
    
    public void execute(Player player, String[] args) {
        execText(player, args);
        execChat(player, args);
        execCommands(player, args);
        execConsoleCommands(player, args);
    }
    
    private void execText(Player player, String[] args) {
        if (text == null) return;
        
        for (int i = 0; i < text.size(); i++) {
            String s = text.get(i);
            s = doSubs(s, player, args);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.sendMessage(s);
        }
    }
    
    private void execChat(Player player, String[] args) {
        if (chat == null) return;
        
        for (int i = 0; i < chat.size(); i++) {
            String s = chat.get(i);
            s = doSubs(s, player, args);
            s = ChatColor.translateAlternateColorCodes('&', s);
            player.chat(s);
        }
    }
    
    private void execCommands(Player player, String[] args) {
        if (commands == null) return;
        
        Server server = player.getServer();
        
        for (int i = 0; i < commands.size(); i++) {
            String s = commands.get(i);
            s = doSubs(s, player, args);
            if (s.startswith("/")) s = s.substring(1);
            server.dispatchCommand(player, s);
        }
    }
    
    private void execConsoleCommands(Player player, String[] args) {
        if (consoleCommands == null) return;
        
        Server server = player.getServer();
        ConsoleCommandSender consoleSender = server.getConsoleSender();
        
        for (int i = 0; i < consoleCommands.size(); i++) {
            String s = consoleCommands.get(i);
            s = doSubs(s, player, args);
            if (s.startswith("/")) s = s.substring(1);
            server.dispatchCommand(consoleSender, s);
        }
    }
    
    private static String doSubs(String s, Player player, String[] args) {
        for (int i = 0; i < args.length; i++) {
            s = s.replaceAll("\\$" + Integer.toString(i + 1), Matcher.quoteReplacement(args[i]));
        }
        
        s = s.replaceAll("\\$p", Matcher.quoteReplacement(player.getName()));
        s = s.replaceAll("\\$d", Matcher.quoteReplacement(player.getDisplayName()));
        
        return s;
    }
}
