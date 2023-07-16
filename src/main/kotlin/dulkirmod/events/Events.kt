package dulkirmod.events

import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.SoundManager
import net.minecraft.entity.Entity
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.fml.common.eventhandler.Event

/**
 * Fired when an entity is removed from the world.
 */
class EntityRemovedEvent(val entity: Entity) : Event()

/**
 * Always fired when a sound is played, as opposed to [PlaySoundEvent], which does not get fired when the master volume is 0.
 */
data class AlwaysPlaySoundEvent(val sound: ISound, val soundManager: SoundManager) : Event() {
    val name = sound.soundLocation.resourcePath
}

class SlayerTypeChangeEvent : Event()