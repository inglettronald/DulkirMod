package dulkirmod.mixins;
import dulkirmod.features.ScalableTooltips;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    private static ItemStack previousStack;

    @Inject(method = "getTooltip", at = @At("HEAD"))
    public void onTooltip(EntityPlayer playerIn, boolean advanced, CallbackInfoReturnable<List<String>> cir) {

        ItemStack currentStack = (ItemStack)(Object)this;
        if (currentStack != previousStack) {
            // reset values here for scrollable tooltips
            ScalableTooltips.INSTANCE.resetPos();
            previousStack = currentStack;
        }
    }
}
