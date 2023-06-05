package dulkirmod.features.chat

import dulkirmod.DulkirMod
import dulkirmod.config.DulkirConfig
import net.minecraft.client.audio.SoundCategory
import net.minecraftforge.client.event.ClientChatReceivedEvent

object DoubleHookDing {
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted.startsWith("It's a Double Hook!")) {
            if (DulkirConfig.doubleHookDing) {
                val prevNote = DulkirMod.mc.gameSettings.getSoundLevel(SoundCategory.RECORDS)
                DulkirMod.mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, 1f)
                DulkirMod.mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.secretSoundVolume, 1f)
                DulkirMod.mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, prevNote)
            }
            if (DulkirConfig.removeHookMessage) event.isCanceled = true
        }
    }
}