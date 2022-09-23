package com.example.commands;

import com.example.commands.HelpCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class Commands {
    public Commands() {
        // Help Commands
        ClientCommandHandler.instance.registerCommand(new HelpCommand());

        // General
        ClientCommandHandler.instance.registerCommand(new EnchantRuneCommand());
    }
}