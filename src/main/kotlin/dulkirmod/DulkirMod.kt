package dulkirmod

import dulkirmod.command.*
import dulkirmod.config.DulkirConfig
import dulkirmod.events.ChatEvent
import dulkirmod.features.*
import dulkirmod.features.chat.AbiphoneDND
import dulkirmod.features.dungeons.*
import dulkirmod.features.rift.IchorHighlight
import dulkirmod.features.rift.SteakDisplay
import dulkirmod.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.KeyBinding
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
import java.lang.management.ManagementFactory
import kotlin.coroutines.EmptyCoroutineContext

@Mod(
    modid = DulkirMod.MOD_ID,
    name = DulkirMod.MOD_NAME,
    version = DulkirMod.MOD_VERSION,
    clientSideOnly = true
)
class DulkirMod {

    var lastLongUpdate: Long = 0
    var lastLongerUpdate: Long = 0

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        val directory = File(event.modConfigurationDirectory, "dulkirmod")
        directory.mkdirs()
        val cch = ClientCommandHandler.instance

        // REGISTER COMMANDS HERE        // Help Commands
        cch.registerCommand(HelpCommand())

        // General
        cch.registerCommand(EnchantRuneCommand())
        cch.registerCommand(FairyCommand())
        cch.registerCommand(SettingsCommand())
        cch.registerCommand(JoinDungeonCommand())
        cch.registerCommand(LeapNameCommand())
        cch.registerCommand(HurtCamCommand())
        cch.registerCommand(FarmingControlSchemeCommand())
        cch.registerCommand(DynamicKeyCommand())
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        config.init()
        // REGISTER Classes and such HERE
        val mcBus = MinecraftForge.EVENT_BUS
        mcBus.register(this)
        mcBus.register(MemoryLeakFix)
        mcBus.register(ChatEvent)
        mcBus.register(NametagCleaner)
        mcBus.register(TitleUtils)
        mcBus.register(ArachneTimer)
        mcBus.register(MatchoAlert)
        mcBus.register(Croesus)
        mcBus.register(ContainerNameUtil)
        mcBus.register(DungeonLeap)
        mcBus.register(AbiphoneDND)
        mcBus.register(KeeperWaypoints)
        mcBus.register(ScalableTooltips)
        mcBus.register(GardenVisitorAlert)
        mcBus.register(DragonFeatures)
        mcBus.register(HideHealerFairy)
        mcBus.register(SecretSounds)
        mcBus.register(BlazeSlayerFeatures)
        mcBus.register(WorldRenderUtils)
        mcBus.register(IchorHighlight)
        mcBus.register(SteakDisplay)
        mcBus.register(ArcherHighlight)

        keyBinds.forEach(ClientRegistry::registerKeyBinding)
    }

    @Mod.EventHandler
    fun postInit(event: FMLLoadCompleteEvent) = scope.launch(Dispatchers.IO) {

    }

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (DulkirConfig.noReverse3rdPerson && mc.gameSettings.thirdPersonView == 2)
            mc.gameSettings.thirdPersonView = 0

        if (event.phase == TickEvent.Phase.START && display != null) {
            mc.displayGuiScreen(display)
            display = null
        }

        val currTime = System.currentTimeMillis()
        if (currTime - lastLongUpdate > 1000) { // long update
            alarmClock()
            brokenHypeNotif()
            GardenVisitorAlert.alert()
            MatchoAlert.alert()
            // Now I don't have to fetch the entries for multiple things, this just updates and caches
            // the data structure on 1s cooldown
            TabListUtils.parseTabEntries()
            DragonFeatures.updateDragonDead()
            lastLongUpdate = currTime
        }

        if (currTime - lastLongerUpdate > 5000) { // longer update
            MemoryLeakFix.clearBlankStands()
            lastLongerUpdate = currTime
        }
    }

    @SubscribeEvent
    fun onKey(event: KeyInputEvent) {
        if (keyBinds[0].isPressed) config.openGui()
        if (keyBinds[1].isPressed) {
            DulkirConfig.noReverse3rdPerson = !DulkirConfig.noReverse3rdPerson
            TextUtils.toggledMessage("No Selfie Camera", DulkirConfig.noReverse3rdPerson)
        }
        if (keyBinds[2].isPressed) {
            FarmingControlSchemeCommand.toggleControls();
        }
        if (keyBinds[3].isPressed) {
            TextUtils.sendMessage("/${DulkirConfig.dynamicCommandString}")
        }
    }

    companion object {
        const val MOD_ID = "dulkirmod"
        const val MOD_NAME = "Dulkir Mod"
        const val MOD_VERSION = "1.2.2"
        const val CHAT_PREFIX = "§f<§3DulkirMod§f>§r"

        val mc: Minecraft = Minecraft.getMinecraft()
        var config = DulkirConfig
        var display: GuiScreen? = null
        val scope = CoroutineScope(EmptyCoroutineContext)

        val keyBinds = arrayOf(
            KeyBinding("Open Settings", Keyboard.KEY_RSHIFT, "Dulkir Mod"),
            KeyBinding("Toggle Selfie Setting", Keyboard.KEY_NONE, "Dulkir Mod"),
            KeyBinding("Toggle Farming Controls", Keyboard.KEY_NONE, "Dulkir Mod"),
            KeyBinding("Dynamic Key", Keyboard.KEY_NONE, "Dulkir Mod")
        )
    }

}
