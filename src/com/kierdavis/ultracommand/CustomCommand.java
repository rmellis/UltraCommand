package com.kierdavis.ultracommand;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CustomCommand {
  private static Pattern ARG_PATTERN = Pattern.compile("\\$(\\d+)");
  
  private List<String> text = null;
  
  private List<String> chat = null;
  
  private List<String> playerCommands = null;
  
  private List<String> consoleCommands = null;
  
  public void setText(List<String> paramList) {
    this.text = paramList;
  }
  
  public void setChat(List<String> paramList) {
    this.chat = paramList;
  }
  
  public void setPlayerCommands(List<String> paramList) {
    this.playerCommands = paramList;
  }
  
  public void setConsoleCommands(List<String> paramList) {
    this.consoleCommands = paramList;
  }
  
  public void addText(String paramString) {
    if (this.text == null)
      this.text = new ArrayList<>(); 
    this.text.add(paramString);
  }
  
  public void addChat(String paramString) {
    if (this.chat == null)
      this.chat = new ArrayList<>(); 
    this.chat.add(paramString);
  }
  
  public void addPlayerCommand(String paramString) {
    if (this.playerCommands == null)
      this.playerCommands = new ArrayList<>(); 
    this.playerCommands.add(paramString);
  }
  
  public void addConsoleCommand(String paramString) {
    if (this.consoleCommands == null)
      this.consoleCommands = new ArrayList<>(); 
    this.consoleCommands.add(paramString);
  }
  
  public void execute(Player paramPlayer, String[] paramArrayOfString) {
    if (!checkArgs(paramPlayer, this.text, paramArrayOfString.length))
      return; 
    if (!checkArgs(paramPlayer, this.chat, paramArrayOfString.length))
      return; 
    if (!checkArgs(paramPlayer, this.playerCommands, paramArrayOfString.length))
      return; 
    if (!checkArgs(paramPlayer, this.consoleCommands, paramArrayOfString.length))
      return; 
    execText(paramPlayer, paramArrayOfString);
    execChat(paramPlayer, paramArrayOfString);
    execPlayerCommands(paramPlayer, paramArrayOfString);
    execConsoleCommands(paramPlayer, paramArrayOfString);
  }
  
  private void execText(Player paramPlayer, String[] paramArrayOfString) {
    if (this.text == null)
      return; 
    for (byte b = 0; b < this.text.size(); b++) {
      String str = this.text.get(b);
      str = doSubs(str, paramPlayer, paramArrayOfString);
      str = ChatColor.translateAlternateColorCodes('&', str);
      paramPlayer.sendMessage(str);
    } 
  }
  
  private void execChat(Player paramPlayer, String[] paramArrayOfString) {
    if (this.chat == null)
      return; 
    for (byte b = 0; b < this.chat.size(); b++) {
      String str = this.chat.get(b);
      str = doSubs(str, paramPlayer, paramArrayOfString);
      str = ChatColor.translateAlternateColorCodes('&', str);
      paramPlayer.chat(str);
    } 
  }
  
  private void execPlayerCommands(Player paramPlayer, String[] paramArrayOfString) {
    if (this.playerCommands == null)
      return; 
    Server server = paramPlayer.getServer();
    for (byte b = 0; b < this.playerCommands.size(); b++) {
      String str = this.playerCommands.get(b);
      str = doSubs(str, paramPlayer, paramArrayOfString);
      if (str.startsWith("/"))
        str = str.substring(1); 
      server.dispatchCommand((CommandSender)paramPlayer, str);
    } 
  }
  
  private void execConsoleCommands(Player paramPlayer, String[] paramArrayOfString) {
    if (this.consoleCommands == null)
      return; 
    Server server = paramPlayer.getServer();
    ConsoleCommandSender consoleCommandSender = server.getConsoleSender();
    for (byte b = 0; b < this.consoleCommands.size(); b++) {
      String str = this.consoleCommands.get(b);
      str = doSubs(str, paramPlayer, paramArrayOfString);
      if (str.startsWith("/"))
        str = str.substring(1); 
      server.dispatchCommand((CommandSender)consoleCommandSender, str);
    } 
  }
  
  private static String doSubs(String paramString, Player paramPlayer, String[] paramArrayOfString) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      paramString = paramString.replaceAll("\\$" + Integer.toString(b + 1), Matcher.quoteReplacement(paramArrayOfString[b]));
      if (b > 0)
        stringBuilder.append(" "); 
      stringBuilder.append(paramArrayOfString[b]);
    } 
    paramString = paramString.replaceAll("\\$p", Matcher.quoteReplacement(paramPlayer.getName()));
    paramString = paramString.replaceAll("\\$d", Matcher.quoteReplacement(paramPlayer.getDisplayName()));
    paramString = paramString.replaceAll("\\$a", Matcher.quoteReplacement(stringBuilder.toString()));
    return paramString;
  }
  
  private boolean checkArgs(Player paramPlayer, List<String> paramList, int paramInt) {
    return true;
  }
}
