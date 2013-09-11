UltraCommand allows a server administrator to define custom commands that can send the executing player messages, run commands as the player or as the console, and more!

# Usage

Add a command named `<name>`:

    /uc add <name>

Add an element with content `<value>` to the command `<name>`:

    /uc add <element> <name> <value

where `<element>` is one of:

* `text` - Text that will be sent to the player. Colour codes using '&' can be used.
* `chat` - Chat messages that will be force-sent by the player. Colour codes using `&` can be used.
* `pcmd` - Commands that will be run as the player. The leading slash is optional.
* `ccmd` - Commands that will be run as the console. The leading slash is optional.

If the command named `<name>` does not exist when this command is run, it is automatically created. This makes it optional to create it with `/uc add <name>` beforehand.

The element value can contain substitutions (placeholders for data that is specific to a certain time and context a command is run in). A list of substitions is available further down the page.

# Examples

Define `/ranks` to display a list of server ranks to the player:

    [/uc add ranks]
    /uc add text ranks &eOur server's ranks:
    /uc add text ranks &e- &aBuilder
    /uc add text ranks &e- &aForeman
    /uc add text ranks &e- &aArchitect
    /uc add text ranks &e- &aPlanner
    /uc add text ranks &e- &cAdmin
    /uc add text ranks &e- &bCEO

Define `/welcome <player>` to show a welcome message to a new player in a chat message:

    [/uc add welcome]
    /uc add chat welcome Welcome to the server, $1!

The `$1` substitution is used to refer to the actual content of `<player>` part of the command above.

Define `/nightvision` to give the player the Night Vision effect for an hour.

    [/uc add nightvision]
    /uc add ccmd nightvision /effect $p 16 3600 10

The `$p` substitution is used to refer to the player's username.

# Substitutions

* `$p` - The username of the player running the custom command.
* `$d` - The display name of the player running the custom command.
* `$a` - All arguments given to the custom command, separated with spaces (e.g. for passing to another command).
* `$1`, `$2`, `$3` etc. - The arguments given to the custom command.
* `$2+`, `$3+` etc. - Like `$a`, but beginning with the second, third etc. argument.

# Commands

`/ultracommand` can be used as an alias to `/uc`.

* `/uc add <name>` - Add a command named `<name>`.
* `/uc add text <name> <text>` - Add text `<text>` to command `<name>`.
* `/uc add chat <name> <chat>` - Add chat message `<chat>` to command `<name>`.
* `/uc add pcmd <name> <command>` - Add player command `<text>` to command `<name>`.
* `/uc add ccmd <name> <command>` - Add console command `<text>` to command `<name>`.
* `/uc list [name]` - List all commands, or the elements of command `<name>`.
* `/uc reload` - Reload the configuration from disk.
* `/uc remove <name>` - Remove command `<name>`.
* `/uc remove text <name>` - Remove all text from command `<name>`.
* `/uc remove chat <name>` - Remove all chat messages from command `<name>`.
* `/uc remove pcmd <name>` - Remove all player commands from command `<name>`.
* `/uc remove ccmd <name>` - Remove all console commands from command `<name>`.
* `/uc save` - Save configuration modified in-game to disk (this is done once per minute automatically).

# Permissions

* `ultracommand.configure` - Gives access to use `/uc` and `/ultracommand`.
* `ultracommand.commands.*` - Gives access to use all custom commands defined with this plugin.
* `ultracommand.commands.<name>` - Gives access to use the command named `<name>` defined with this plugin.

# Development Builds

Development builds can be found on the [build server][build-server].

**Use these at your own risk. They are not guaranteed to have been fully tested.**

[build-server]: http://bukkit.kierdavis.com/UltraCommand/
