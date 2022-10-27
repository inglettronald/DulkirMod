package dulkirmod.mixins;

import dulkirmod.features.Croesus;
import dulkirmod.features.DungeonLeap;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {
    @Inject(at=@At("HEAD"), method="drawSlot", cancellable = true)
    public void drawSlot(Slot slotIn, CallbackInfo ci) {
        if (Croesus.Companion.inCroesus() && Croesus.Companion.isChestOpened(slotIn)) {
            ci.cancel();
        }
        if (DungeonLeap.Companion.inLeapMenu() && DungeonLeap.Companion.isHighlightedLeapPlayer(slotIn)) {
            // TODO
        }
    }
}
