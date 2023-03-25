package dulkirmod.utils

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.min

object TitleUtils {
    var curString = ""
    var endTime: Long = 0

    @SubscribeEvent
    fun onRender(event: RenderGameOverlayEvent.Text) {
        if (System.currentTimeMillis() > endTime) return
        val width = mc.fontRendererObj.getStringWidth(curString)
        val screenWidth = ScaledResolution(mc).scaledWidth_double
        val screenHeight = ScaledResolution(mc).scaledHeight_double
        var scale = ((screenWidth - 100) * DulkirConfig.bestiaryNotifSize) / width
        scale = min(scale, 10.0)
        GlStateManager.pushMatrix()
        GlStateManager.translate((screenWidth / 2 - width * scale / 2), screenHeight / 2 - (4.5 * scale), 0.0)
        GlStateManager.scale(scale, scale, scale)
        mc.fontRendererObj.drawString(curString, 0f, 0f, 0, DulkirConfig.bestiaryTextShadow)
        GlStateManager.popMatrix()
    }

    fun drawStringForTime(string: String, time: Int) {
        this.curString = string
        this.endTime = time.toLong() + System.currentTimeMillis()
    }

}