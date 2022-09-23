package com.example;

import com.example.commands.Commands;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "examplemod", version = "1.0.0")
public class ExampleMod {
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
