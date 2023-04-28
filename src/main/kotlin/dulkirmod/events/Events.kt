package dulkirmod.events

import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.eventhandler.Event

/**
 * Fired when an entity is removed from the world.
 */
class EntityRemovedEvent(val entity: Entity) : Event()