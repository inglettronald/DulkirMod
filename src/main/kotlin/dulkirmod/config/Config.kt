package dulkirmod.config

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Category
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import gg.essential.vigilance.data.SortingBehavior
import dulkirmod.DulkirMod.Companion.display
import java.awt.Color
import java.io.File
import java.util.function.Consumer

object Config : Vigilant(File("./config/dulkirmod/config.toml"), "DulkirMod") {

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Enchant Rune Particles",
        description = "ugly ass",
        category = "General"
    )
    var hideEnchantRune = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Healer fairy",
        description = "Probably disable when not in dungeons for now. Will fix later.",
        category = "General"
    )
    var hideHealerFairy = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Heart Particles",
        description = "Useful for hyperion and healer bullshit",
        category = "General"
    )
    var hideHeartParticles = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Throttle Notifier",
        description = "Making features out of bugs wow",
        category = "General"
    )
    var throttleNotifier = false

    @Property(
        type = PropertyType.TEXT,
        name = "Throttle Notifier String",
        description = "How do you want to tell people you are throttled?",
        category = "General",
        placeholder = "i am being throttled zzz",
        protectedText = false
    )
    var customMessage: String = "i am being throttled zzz"
    fun init() {
        initialize()
        addDependency("customMessage", "throttleNotifier")
    }

}
