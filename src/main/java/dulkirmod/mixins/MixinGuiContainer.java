package dulkirmod.mixins;

import dulkirmod.features.ScalableTooltips;
import dulkirmod.features.dungeons.Croesus;
import dulkirmod.features.dungeons.DungeonLeap;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {
    @Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
    public void drawSlot(Slot slotIn, CallbackInfo ci) {
        if (Croesus.inCroesus() && Croesus.isChestOpened(slotIn)) {
            ci.cancel();
        }
        if (DungeonLeap.INSTANCE.getInLeapMenu() && DungeonLeap.INSTANCE.isHighlightedLeapPlayer(slotIn)) {
            ci.cancel();

            ItemStack stack = new ItemStack(Blocks.wool, 1, EnumDyeColor.GREEN.getMetadata());

            this.zLevel = 100.0F;
            this.itemRender.zLevel = 100.0F;

            GlStateManager.enableDepth();
            this.itemRender.renderItemAndEffectIntoGUI(
                    stack,
                    slotIn.xDisplayPosition,
                    slotIn.yDisplayPosition
            );
            this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, stack,
                    slotIn.xDisplayPosition, slotIn.yDisplayPosition, ""
            );

            this.itemRender.zLevel = 0.0F;
            this.zLevel = 0.0F;
        }
    }

    @Inject(method = "onGuiClosed", at = @At("HEAD"))
    private void onGuiClosed(CallbackInfo ci) {
        // reset values here
        ScalableTooltips.INSTANCE.resetPos();
    }
}
