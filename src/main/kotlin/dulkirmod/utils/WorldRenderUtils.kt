package dulkirmod.utils

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.mixins.AccessorRenderManager
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import java.awt.Color


object WorldRenderUtils {
    private val tessellator: Tessellator
        get() = Tessellator.getInstance()
    private val worldRenderer: WorldRenderer
        get() = tessellator.worldRenderer
    private val renderManager: RenderManager
        get() = mc.renderManager
    private val fontRenderer: FontRenderer
        get() = mc.fontRendererObj

    fun renderString(
        location: Vec3,
        text: String,
        depthTest: Boolean = true,
        scale: Float = 1f,
        showDistance: Boolean = false,
        shadow: Boolean = false,
        renderBlackBox: Boolean = true,
    ) {
        if (!depthTest) {
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            GL11.glDepthMask(false)
        }
        GlStateManager.pushMatrix()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.translate(
            location.xCoord - renderManager.viewerPosX,
            location.yCoord - renderManager.viewerPosY,
            location.zCoord - renderManager.viewerPosZ
        )
        GlStateManager.color(1f, 1f, 1f, 0.5f)
        GlStateManager.rotate(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
        GlStateManager.scale(-scale / 25, -scale / 25, scale / 25)

        if (renderBlackBox) {
            val j = fontRenderer.getStringWidth(text) / 2
            GlStateManager.disableTexture2D()
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
            worldRenderer.pos(-j - 1.0, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
            worldRenderer.pos(-j - 1.0, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
            worldRenderer.pos(j + 1.0, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
            worldRenderer.pos(j + 1.0, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
            tessellator.draw()
            GlStateManager.enableTexture2D()
        }

        if (shadow) {
            fontRenderer.drawStringWithShadow(text, -fontRenderer.getStringWidth(text) / 2f, 0f, 0)
        } else {
            fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 0)
        }

        // for waypoints
        if (showDistance) {
            val distance = "§e${mc.thePlayer.positionVector.distanceTo(location).toInt()}m"
            GlStateManager.translate(0.0, 8.66, 0.0)
            GlStateManager.scale(0.66, 0.66, 0.66)

            if (renderBlackBox) {
                val j = fontRenderer.getStringWidth(distance) / 2
                GlStateManager.disableTexture2D()
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
                worldRenderer.pos(-j - 1.0, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos(-j - 1.0, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos(j + 1.0, 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos(j + 1.0, -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                tessellator.draw()
                GlStateManager.enableTexture2D()
            }

            if (shadow) {
                fontRenderer.drawStringWithShadow(distance, -fontRenderer.getStringWidth(distance) / 2f, 0f, 0)
            } else {
                fontRenderer.drawString(distance, -fontRenderer.getStringWidth(distance) / 2, 0, 0)
            }
        }

        GlStateManager.color(1f, 1f, 1f)
        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
        if (!depthTest) {
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            GL11.glDepthMask(true)
        }
    }


    /**
     * Courtesy of Odin, I could not be bothered to deal with rendering code.
     */
    fun drawCustomBox(
        x: Double,
        xWidth: Double,
        y: Double,
        yWidth: Double,
        z: Double,
        zWidth: Double,
        color: Color,
        thickness: Float = 3f,
        phase: Boolean
    ) {
        GlStateManager.pushMatrix()

        GlStateManager.color(color.red / 255f, color.green / 255f, color.blue / 255f, 1f)
        GlStateManager.translate(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ)
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        if (phase) GlStateManager.disableDepth()
        GlStateManager.disableTexture2D()
        GlStateManager.disableLighting()
        GlStateManager.enableBlend()

        GL11.glLineWidth(thickness)

        val x1 = x + xWidth
        val y1 = y + yWidth
        val z1 = z + zWidth

        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION)
        worldRenderer.pos(x1, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z).endVertex()
        worldRenderer.pos(x, y1, z).endVertex()
        worldRenderer.pos(x, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z1).endVertex()
        worldRenderer.pos(x1, y, z1).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x, y, z1).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x, y1, z).endVertex()
        worldRenderer.pos(x, y, z).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x1, y1, z).endVertex()
        worldRenderer.pos(x1, y, z).endVertex()
        worldRenderer.pos(x1, y, z1).endVertex()
        worldRenderer.pos(x, y, z1).endVertex()
        worldRenderer.pos(x, y1, z1).endVertex()
        worldRenderer.pos(x1, y1, z1).endVertex()

        tessellator.draw()

        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()
        if (phase) GlStateManager.enableDepth()
        GlStateManager.popMatrix()
    }

    private fun getRenderX(): Double {
        return (mc.renderManager as AccessorRenderManager).renderX
    }

    private fun getRenderY(): Double {
        return (mc.renderManager as AccessorRenderManager).renderY
    }

    private fun getRenderZ(): Double {
        return (mc.renderManager as AccessorRenderManager).renderZ
    }

    fun fixRenderPos(x: Double, y: Double, z: Double): Triple<Double, Double, Double> {
        return Triple(x + getRenderX(), y + getRenderY(), z + getRenderZ())
    }
}