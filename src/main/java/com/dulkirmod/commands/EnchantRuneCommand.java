package com.dulkirmod.commands;

import com.dulkirmod.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class EnchantRuneCommand extends ClientCommandBase {

    public EnchantRuneCommand() {
        super("enchantrune");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                EnumChatFormatting.BLACK+ "" + EnumChatFormatting.BOLD + "TOGGLED."));
        Settings.EnchantRune = !Settings.EnchantRune;
    }
}