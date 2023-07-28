package dulkirmod.mixins;

import dulkirmod.DulkirMod;
import dulkirmod.config.DulkirConfig;
import dulkirmod.features.ImpactDisplay;
import dulkirmod.features.ReaperDisplay;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "shouldCauseReequipAnimation", at = @At("HEAD"), cancellable = true, remap = false)
    public void overrideReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged, CallbackInfoReturnable<Boolean> ci) {
        if (DulkirMod.Companion.getConfig().getCancelReequip()) {
            if (slotChanged && DulkirMod.Companion.getConfig().getShowReEquipAnimationWhenChangingSlots()) {
                return;
            }
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "showDurabilityBar(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"),
            cancellable = true, remap = false)
    public void shouldShowDur(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (DulkirConfig.INSTANCE.getDisplayReaperCD())
            ReaperDisplay.INSTANCE.shouldDisplay(stack, cir);
        if (DulkirConfig.INSTANCE.getDisplayImpactCD())
            ImpactDisplay.INSTANCE.shouldDisplay(stack, cir);
    }
    @Inject(method = "getDurabilityForDisplay(Lnet/minecraft/item/ItemStack;)D", at = @At("HEAD"),
            cancellable = true, remap = false)
    public void getItemHealthDisplayVal(ItemStack stack, CallbackInfoReturnable<Double> cir) {
        if (DulkirConfig.INSTANCE.getDisplayReaperCD())
            ReaperDisplay.INSTANCE.calcDurability(stack, cir);
        if (DulkirConfig.INSTANCE.getDisplayImpactCD())
            ImpactDisplay.INSTANCE.calcDurability(stack, cir);
    }
}
