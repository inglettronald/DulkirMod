package dulkirmod.mixins;

import dulkirmod.events.AlwaysPlaySoundEvent;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class MixinSoundManager {
    @Inject(at = @At("HEAD"), method = "playSound")
    public void onSound(ISound p_sound, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new AlwaysPlaySoundEvent(p_sound, (SoundManager) (Object) this));
    }
}
