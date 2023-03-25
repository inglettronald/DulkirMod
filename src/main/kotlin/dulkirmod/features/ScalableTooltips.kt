package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.GuiChat
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiUtils
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse

object ScalableTooltips {
    var scrollY: Int = 0
    var scrollX: Int = 0
    // Checks to see if large tooltips should be snapped (for larger than can fit on screen code)
    var snapFlag: Boolean = true
    var scaleScale: Float = 0f
    var previousStack: ItemStack? = null

    fun drawScaledHoveringText(
        textLines: List<String>,
        mouseX: Int,
        mouseY: Int,
        screenWidth: Int,
        screenHeight: Int,
        font: FontRenderer,
    ): Boolean {
        if(!DulkirConfig.scaledTooltips) return false
        if(textLines.isEmpty()) return true

        // Calculate the amount of translation that should be applied based on how much the user has scrolled
        // Only allows the user to scroll if you are NOT inside a chat GUI.
        val eventDWheel = Mouse.getDWheel()
        if (mc.currentScreen !is GuiChat) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (eventDWheel < 0) {
                    scrollX += Minecraft.getMinecraft().displayWidth / 192
                } else if (eventDWheel > 0) {
                    scrollX -= Minecraft.getMinecraft().displayWidth / 192
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                if (eventDWheel < 0) {
                    scaleScale -= .1f
                } else if (eventDWheel > 0) {
                    scaleScale += .1f
                }
            } else {
                if (eventDWheel < 0) {
                    scrollY -= Minecraft.getMinecraft().displayHeight / 108
                } else if (eventDWheel > 0) {
                    scrollY += Minecraft.getMinecraft().displayHeight / 108
                }
            }
            if (Mouse.isButtonDown(2)) {
                scaleScale = 0f
            }
        }

        val scale = (DulkirConfig.tooltipSize + scaleScale).coerceAtLeast(0f)

        // Calculate the width and height of the tooltip box
        var width = 0
        for (textLine in textLines) {
            val textWidth = font.getStringWidth(textLine)
            if (textWidth > width) {
                width = textWidth
            }
        }
        val height = (textLines.size) * font.FONT_HEIGHT

        // Save the matrix state and scale it
        GlStateManager.pushMatrix()
        GlStateManager.scale(scale, scale, 1f)
        GlStateManager.disableRescaleNormal()
        RenderHelper.disableStandardItemLighting()
        GlStateManager.disableLighting()
        GlStateManager.disableDepth()


        // calculates where it wants to put the tooltip based on user input
        var x = ((mouseX + 12 + scrollX) / scale).toInt()
        var y = ((mouseY - 12 + scrollY) / scale).toInt()

        /**
         * Extra code to account for larger tooltips:
         * Tooltips should not initially render off the screen if they can fit, if too wide/too long for screen
         * it will just default to normal.
         */
        if ((x + width + 4 > screenWidth / scale) && (width + 4 <= screenWidth / scale)) {
            scrollX = (screenWidth - mouseX - 12 - (width + 4)* scale).toInt()
        }

        if ((y + height + 4 > screenHeight / scale) && (height + 4 <= screenHeight / scale)) {
            scrollY = (screenHeight - mouseY + 12 - (height + 4)* scale).toInt()
        }

        /**
         * HAVE: default x and y
         * NEED: modify scrollx and scroll why such that updates x and y to 0 later
         */
        if (x < 0 && (width + 4 <= screenWidth / scale))
            scrollX = -mouseX - 12 + 4
        if (y < 0 && (height + 4 <= screenHeight / scale))
            scrollY = -mouseY + 12 + 4


        // if too large, then snap to top (if first time rendering tooltip)
        if (snapFlag) {
            if (width + 4 > screenWidth / scale) {
                scrollX = -mouseX - 12 + 4
            }
            if (height + 4 > screenHeight / scale) {
                scrollY = -mouseY + 12 + 4
            }
            snapFlag = false
        }

        // updates the position of x and y if it has been modified.
        x = ((mouseX + 12 + scrollX) / scale).toInt()
        y = ((mouseY - 12 + scrollY) / scale).toInt()

        // Draw the background rectangle
        val backgroundColor = -0xfeffff0
        val zLevel = 300

        GuiUtils.drawGradientRect(zLevel, x - 3, y - 4, x + width + 3, y - 3, backgroundColor, backgroundColor)
        GuiUtils.drawGradientRect(zLevel, x - 3, y + height + 3, x + width + 3, y + height + 4, backgroundColor, backgroundColor)
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3, x + width + 3, y + height + 3, backgroundColor, backgroundColor)
        GuiUtils.drawGradientRect(zLevel, x - 4, y - 3, x - 3, y + height + 3, backgroundColor, backgroundColor)
        GuiUtils.drawGradientRect(zLevel, x + width + 3, y - 3, x + width + 4, y + height + 3, backgroundColor, backgroundColor)
        val borderColorStart = 0x505000FF
        val borderColorEnd = borderColorStart and 0xFEFEFE shr 1 or (borderColorStart and -0x1000000)
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, borderColorStart, borderColorEnd)
        GuiUtils.drawGradientRect(zLevel, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, borderColorStart, borderColorEnd)
        GuiUtils.drawGradientRect(zLevel, x - 3, y - 3, x + width + 3, y - 3 + 1, borderColorStart, borderColorStart)
        GuiUtils.drawGradientRect(zLevel, x - 3, y + height + 2, x + width + 3, y + height + 3, borderColorEnd, borderColorEnd)

        // Render the tooltip text
        var yStart = y
        for (textLine in textLines) {
            font.drawStringWithShadow(textLine, x.toFloat(), yStart.toFloat(), -1)
            yStart += font.FONT_HEIGHT
        }
        // Reset matrix state
        GlStateManager.enableLighting()
        GlStateManager.enableDepth()
        RenderHelper.enableStandardItemLighting()
        GlStateManager.enableRescaleNormal()
        GlStateManager.popMatrix()
        return true
    }

    fun resetPos() {
        scrollX = 0
        scrollY = 0
        scaleScale = 0f
        snapFlag = true
    }
}