package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.utils.MinecraftExecutor
import dulkirmod.utils.TextUtils
import moe.nea.libautoupdate.*
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import java.util.concurrent.CompletableFuture

object UpdateNotificationFeature {
    val context = UpdateContext(
        UpdateSource.githubUpdateSource("inglettronald", "DulkirMod"),
        UpdateTarget.deleteAndSaveInTheSameFolder(UpdateNotificationFeature::class.java),
        CurrentVersion.ofTag("v${DulkirMod.MOD_VERSION}"),
        DulkirMod.MOD_ID,
    )

    init {
        context.cleanup()
    }

    val updates = mutableMapOf<String, PotentialUpdate>()

    var hasCheckedOnStartup = false

    @SubscribeEvent
    fun updateOnStartup(event: ClientTickEvent) {
        if (!hasCheckedOnStartup && DulkirMod.mc.thePlayer != null) {
            hasCheckedOnStartup = true
            checkUpdate(false)
        }
    }

    fun checkUpdate(alwaysNotify: Boolean) {
        context.checkUpdate("full")
            .thenAcceptAsync({
                if (it.isUpdateAvailable) {
                    updates[it.updateUUID.toString()] = it
                    DulkirMod.mc.thePlayer?.addChatMessage(
                        ChatComponentText("${DulkirMod.CHAT_PREFIX} A DulkirMod update is available: ${DulkirMod.MOD_VERSION} » ${it.update.versionName}. Click here to download it.").setChatStyle(
                            ChatStyle().setChatClickEvent(
                                ClickEvent(
                                    ClickEvent.Action.RUN_COMMAND,
                                    "/updatedulkir ${it.updateUUID}"
                                )
                            ).setChatHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    ChatComponentText("Click to download ${it.update.versionName}")
                                )
                            )
                        )
                    )
                } else if (alwaysNotify) {
                    TextUtils.info("No DulkirMod update found! You are on the latest version.")
                }
            }, MinecraftExecutor)
    }

    fun downloadUpdate(uuid: String) {
        val update = updates[uuid]
        if (update == null) {
            TextUtils.info("Unknown update.")
            return
        }
        CompletableFuture.runAsync {
            update.prepareUpdate()
        }.thenRunAsync({
            TextUtils.info("Update ${update.update.versionName} downloaded. It will be applied after your next restart.")
            update.executePreparedUpdate()
        }, MinecraftExecutor)
    }

}