package dulkirmod.events

import dulkirmod.features.chat.*
import dulkirmod.utils.Utils.stripColorCodes
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ChatEvent {

    /**
     * This is mostly the way it is to avoid having to run strip color codes a bunch of times
     * for each message. Not sure if it even matters but whatever
     */
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type == 2.toByte()) {
            return
        }
        val unformatted = stripColorCodes(event.message.unformattedText)

        // THROTTLE NOTIFIER
        ThrottleNotif.handle(event, unformatted)

        // BRIDGE BOT STUFF - CLICKABLE LINKS!
        Bridge.handle(event)

        // DO NOT DISTURB FOR ABIPHONE
        AbiphoneDND.handle(event, unformatted)

        // FAKE MESSAGE SENDER (DULKIR ONLY)
        FakeMsg.handle(event, unformatted)

        // Quick vanquisher thing
        VanquisherTrigger.handle(unformatted)

        // Key Hud Element
        DungeonKeyDisplay.handle(unformatted)

        DoubleHookDing.handle(event, unformatted)
    }
}