package dulkirmod.mixins;

import dulkirmod.features.ScalableTooltips;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    private static int previousSlotId = -1;

    @Inject(method = "renderToolTip", at = @At("HEAD"))
    public void onTooltip(ItemStack stack, int x, int y, CallbackInfo ci) {
        GuiScreen screen = (GuiScreen) (Object) this;
        if (screen instanceof GuiContainer) {
            GuiContainer containerScreen = (GuiContainer) screen;
            Slot slot = containerScreen.getSlotUnderMouse();
            if (slot != null && slot.slotNumber != previousSlotId) {
                ScalableTooltips.INSTANCE.resetPos();
                previousSlotId = slot.slotNumber;
            }
        }
    }


}
