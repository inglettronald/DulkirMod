package dulkirmod.features.chat

import dulkirmod.utils.TabListUtils

object DungeonKeyDisplay {

    private val keyPickupMessage = "^(\\[(.*)] )?([a-zA-Z]+) has obtained (Blood|Wither) Key!".toRegex()
    private val altPickupMessage = "A Wither Key was picked up!".toRegex()
    private val openMessage = "([a-zA-Z]+) opened a WITHER door!".toRegex()
    private val altOpenMessage = "The BLOOD DOOR has been opened!".toRegex()
    var hasKey = false;
    fun handle(stringUnformatted: String) {
        if (!TabListUtils.isInDungeons) {
            hasKey = false
            return
        }
        if (stringUnformatted matches keyPickupMessage || stringUnformatted matches altPickupMessage) {
            hasKey = true
            return
        }
        if (stringUnformatted matches openMessage || stringUnformatted matches altOpenMessage) {
            hasKey = false
            return
        }
    }
}