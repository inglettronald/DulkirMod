package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.max

object KeeperWaypoints {
    val vec1 = Vec3(-208.5, 44.5, -259.5)
    val vec2 = Vec3(-311.5, 43.5, -232.5)
    val vec3 = Vec3(-230.5, 57.5, -307.5)
    val vec4 = Vec3(-269.5, 47.5, -166.5)
    val vec5 = Vec3(-292.5, 47.5, -167.5)
    val vec6 = Vec3(-291.5, 47.5, -183.5)
    val vec7 = Vec3(-282.5, 47.5, -195.5)
    val vec8 = Vec3(-262.5, 49.5, -191.5)
    val vec9 = Vec3(-269.5, 61.5, -159.5)
    @SubscribeEvent
    fun onWorldRenderLast(event: RenderWorldLastEvent) {
        if (!DulkirConfig.keeperWaypoints) return
        if (TabListUtils.area != "Spider's Den") return

        val playerVec = mc.thePlayer.positionVector

        val scale1 = max(1f, playerVec.distanceTo(vec1).toFloat()/10f)
        val scale2 = max(1f,playerVec.distanceTo(vec2).toFloat()/10f)
        val scale3 = max(1f, playerVec.distanceTo(vec3).toFloat()/10f)
        val scale4 = max(1f, playerVec.distanceTo(vec4).toFloat()/10f)
        val scale5 = max(1f, playerVec.distanceTo(vec5).toFloat()/10f)
        val scale6 = max(1f, playerVec.distanceTo(vec6).toFloat()/10f)
        val scale7 = max(1f, playerVec.distanceTo(vec7).toFloat()/10f)
        val scale8 = max(1f, playerVec.distanceTo(vec8).toFloat()/10f)
        val scale9 = max(1f, playerVec.distanceTo(vec9).toFloat()/10f)

        val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
        WorldRenderUtils.renderString(vec1, "${color}1", false, scale1, true)
        WorldRenderUtils.renderString(vec2, "${color}2", false, scale2, true)
        WorldRenderUtils.renderString(vec3, "${color}3", false, scale3, true)
        WorldRenderUtils.renderString(vec4, "${color}4", false, scale4, true)
        WorldRenderUtils.renderString(vec5, "${color}5", false, scale5, true)
        WorldRenderUtils.renderString(vec6, "${color}6", false, scale6, true)
        WorldRenderUtils.renderString(vec7, "${color}7", false, scale7, true)
        WorldRenderUtils.renderString(vec8, "${color}8", false, scale8, true)
        WorldRenderUtils.renderString(vec9, "${color}9", false, scale9, true)
    }
}

