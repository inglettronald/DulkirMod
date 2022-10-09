package dulkirmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "gg.skytils.skytilsmod.core.Config", remap = false)
public class MixinSkytils {

    @Shadow
    private static boolean waterBoardSolver;

    @Inject(method = "getWaterBoardSolver", at = @At("HEAD"), cancellable = true)
    private static void changeValue(CallbackInfoReturnable<Boolean> cir) {
       cir.setReturnValue(false);
    }
}
