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
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object ArcherHighlight {

//    @SubscribeEvent
//    fun onRenderLiving(event: RenderLivingEvent.Pre<*>) {
//        if (!DulkirConfig.archerBox) return
//        if (TabListUtils.area != "Dungeon") return
//        if (!ScoreBoardUtils.isInM7 && !DulkirConfig.archerBoxEverywhere) return
//        if (event.entity !is EntityPlayer) return
//        val name = event.entity.name ?: return
//        if (name != TabListUtils.archerName) return
//        if (mc.thePlayer.positionVector.yCoord > 45 && !DulkirConfig.archerBoxEverywhere) return
//        if (mc.thePlayer.name == name) return
//        val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
//        WorldRenderUtils.drawCustomBox(
//            x - .5,
//            1.0,
//            y,
//            2.0,
//            z - .5,
//            1.0,
//            DulkirConfig.archBoxColor.toJavaColor(),
//            3f,
//            phase = false
//        )
//    }

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (!DulkirConfig.archerBox) return
        if (TabListUtils.area != "Dungeon") return
        if (!ScoreBoardUtils.isInM7 && !DulkirConfig.archerBoxEverywhere) return
        val players = mc.theWorld.playerEntities.filterNotNull()
        players.forEach {
            val name = it.name ?: return@forEach
            if (name != TabListUtils.archerName) return
            if (mc.thePlayer.positionVector.yCoord > 45 && !DulkirConfig.archerBoxEverywhere) return
            if (mc.thePlayer.name == name) return
            val x = it.posX - ((it.lastTickPosX - it.posX) * event.partialTicks)
            val y = it.posY - ((it.lastTickPosY - it.posY) * event.partialTicks)
            val z = it.posZ - ((it.lastTickPosZ - it.posZ) * event.partialTicks)
            WorldRenderUtils.drawCustomBox(
                x - .5,
                1.0,
                y,
                2.0,
                z - .5,
                1.0,
                DulkirConfig.archBoxColor.toJavaColor(),
                3f,
                phase = false
            )
        }
    }
}