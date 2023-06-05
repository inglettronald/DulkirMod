package dulkirmod.features.dungeons

import com.google.common.eventbus.Subscribe
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.ScoreBoardUtils
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.WorldRenderUtils
import ibxm.Player
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object ArcherHighlight {

    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Post<*>) {
        if (!DulkirConfig.archerBox) return
        if (TabListUtils.area != "Dungeon") return
        if (!ScoreBoardUtils.isInM7 && !DulkirConfig.archerBoxEverywhere) return
        if (event.entity !is EntityPlayer) return
        val name = event.entity.name ?: return
        if (name != TabListUtils.archerName) return
        if (mc.thePlayer.positionVector.yCoord > 45 && !DulkirConfig.archerBoxEverywhere) return
        if (mc.thePlayer.name == name) return
        val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
        WorldRenderUtils.drawCustomBox(
            x - .5,
            1.0,
            y,
            event.entity.height.toDouble(),
            z - .5,
            1.0,
            DulkirConfig.archBoxColor.toJavaColor(),
            3f,
            phase = false
        )
    }
}