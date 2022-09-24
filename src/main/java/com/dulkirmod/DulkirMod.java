package com.dulkirmod;

import com.dulkirmod.commands.Commands;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.swing.text.JTextComponent;

@Mod(modid = "dulkirmod", version = "1.0.0")
public class DulkirMod {
    public Commands commands;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Dirt: " + Blocks.dirt.getUnlocalizedName());
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        this.commands = new Commands();
    }


}
