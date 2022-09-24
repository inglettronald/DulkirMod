package dulkirmod

import dulkirmod.command.EnchantRuneCommand
import dulkirmod.command.FairyCommand
import dulkirmod.command.HelpCommand
import dulkirmod.command.SettingsCommand
import dulkirmod.config.Config
import dulkirmod.events.ChatEvent
import dulkirmod.features.NametagCleaner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import org.lwjgl.input.Keyboard
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

@Mod(
    modid = DulkirMod.MOD_ID,
    name = DulkirMod.MOD_NAME,
    version = DulkirMod.MOD_VERSION,
    clientSideOnly = true
)
class DulkirMod {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, "dulkirmod")
        directory.mkdirs()

        // REGISTER COMMANDS HERE        // Help Commands
        ClientCommandHandler.instance.registerCommand(HelpCommand())

                // General
        ClientCommandHandler.instance.registerCommand(EnchantRuneCommand())
        ClientCommandHandler.instance.registerCommand(FairyCommand())
        ClientCommandHandler.instance.registerCommand(SettingsCommand())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        config.init()
        // REGISTER EVENTS HERE
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(ChatEvent())
        MinecraftForge.EVENT_BUS.register(NametagCleaner)

        keyBinds.forEach(ClientRegistry::registerKeyBinding)
    }

    @Mod.EventHandler
    fun postInit(event: FMLLoadCompleteEvent) = scope.launch(Dispatchers.IO) {

    }

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || display == null) return
        mc.displayGuiScreen(display)
        display = null
    }

    @SubscribeEvent
    fun onKey(event: KeyInputEvent) {
        if (keyBinds[0].isPressed) display = config.gui()
    }

    companion object {
        const val MOD_ID = "dulkirmod"
        const val MOD_NAME = "Dulkir Mod"
        const val MOD_VERSION = "0.0.0"
        const val CHAT_PREFIX = "§b§l<§fDulkirMod§b§l>§r"

        val mc: Minecraft = Minecraft.getMinecraft()
        var config = Config
        var display: GuiScreen? = null
        val scope = CoroutineScope(EmptyCoroutineContext)

        val keyBinds = arrayOf(
            KeyBinding("Open Settings", Keyboard.KEY_RSHIFT, "Dulkir Mod"),
        )
    }


    // terminal throttle code


}
