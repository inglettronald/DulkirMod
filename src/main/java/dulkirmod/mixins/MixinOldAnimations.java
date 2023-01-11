package dulkirmod.mixins;

import dulkirmod.DulkirMod;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This method will basically just turn off all the oldanimations code that is breaking dulkirmod.
 * <p>
 * It will only run if you have the global settings of Custom Animations turned on, so you can basically
 * pick which one you want to have. Either custom animations or old (for conflicting features). This is not a great fix,
 * but to make them work together seamlessly I would *basically* be recoding the entirety of Old Animations into
 * this mod, which I don't really want to do.
 */

@Pseudo
@Mixin(targets = "club.sk1er.oldanimations.AnimationHandler", remap = false)
public class MixinOldAnimations {

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "HEAD"), cancellable = true)
    public void disableOldAnimStuff(ItemRenderer renderer, ItemStack stack, float equipProgress, float partialTicks, CallbackInfoReturnable<Boolean> cir) {
        if (DulkirMod.Companion.getConfig().getCustomAnimations())
            cir.setReturnValue(false);
    }
}
