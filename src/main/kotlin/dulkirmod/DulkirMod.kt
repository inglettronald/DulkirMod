package dulkirmod

import dulkirmod.command.*
import dulkirmod.config.Config
import dulkirmod.events.ChatEvent
import dulkirmod.features.*
import dulkirmod.features.chat.AbiphoneDND
import dulkirmod.utils.ContainerNameUtil
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils.getArea
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
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

    var lastLongUpdate: Long = 0

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
        ClientCommandHandler.instance.registerCommand(JoinDungeonCommand())
        ClientCommandHandler.instance.registerCommand(LeapNameCommand())
        ClientCommandHandler.instance.registerCommand(HurtCamCommand())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        config.init()
        // REGISTER Classes and such HERE
        val mcBus = MinecraftForge.EVENT_BUS
        mcBus.register(this)
        mcBus.register(ChatEvent())
        mcBus.register(NametagCleaner)
        mcBus.register(titleUtils)
        mcBus.register(ArachneTimer())
        mcBus.register(MatchoAlert())
        mcBus.register(Croesus())
        mcBus.register(ContainerNameUtil())
        mcBus.register(DungeonLeap())
        mcBus.register(AbiphoneDND())
        mcBus.register(KeeperWaypoints())
        mcBus.register(ScalableTooltips)

        keyBinds.forEach(ClientRegistry::registerKeyBinding)
    }

    @Mod.EventHandler
    fun postInit(event: FMLLoadCompleteEvent) = scope.launch(Dispatchers.IO) {

    }

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (Config.noReverse3rdPerson && mc.gameSettings.thirdPersonView == 2)
            mc.gameSettings.thirdPersonView = 0

        if (event.phase == TickEvent.Phase.START && display != null) {
            mc.displayGuiScreen(display)
            display = null
        }

        var longupdate = false
        val currTime: Long = System.currentTimeMillis()
        if (currTime - lastLongUpdate > 1000) {
            longupdate = true
            lastLongUpdate = currTime
        }
        if (longupdate) {
            // EXECUTE STUFF HERE THAT DOESN'T REALLY NEED TO BE RUN EVERY TICK
            alarmClock()
            brokenHypeNotif()
            matchoAlert.alert()
            getArea()
            longupdate = false
        }
    }

    @SubscribeEvent
    fun onKey(event: KeyInputEvent) {
        if (keyBinds[0].isPressed) display = config.gui()
        if (keyBinds[1].isPressed) {
            Config.noReverse3rdPerson = !Config.noReverse3rdPerson
            mc.thePlayer.addChatMessage(ChatComponentText("§7Toggling No Selfie Camera Setting... now: §6${Config.noReverse3rdPerson}"))
        }
    }

    companion object {
        const val MOD_ID = "dulkirmod"
        const val MOD_NAME = "Dulkir Mod"
        const val MOD_VERSION = "1.1.5"
        const val CHAT_PREFIX = "§f<§3DulkirMod§f>"

        val mc: Minecraft = Minecraft.getMinecraft()
        var config = Config
        var display: GuiScreen? = null
        val scope = CoroutineScope(EmptyCoroutineContext)
        val titleUtils = TitleUtils()
        val matchoAlert = MatchoAlert()

        val keyBinds = arrayOf(
            KeyBinding("Open Settings", Keyboard.KEY_RSHIFT, "Dulkir Mod"),
            KeyBinding("Toggle Selfie Setting", Keyboard.KEY_NONE, "Dulkir Mod"),
        )
    }

}
