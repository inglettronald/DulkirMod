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

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Extra Nametags",
        description = "Prevents some nametags not covered by skytils \"Hide non-starred nametags\" from rendering.",
        category = "General"
    )
    var hideTags = false

    // CUSTOM ANIMATIONS
    @Property(
        type = PropertyType.SWITCH,
        name = "Custom Animations",
        description = "Change the look of your held item",
        category = "Animations"
    )
    var customAnimations = false

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Size",
        description = "Scales the size of your currently held item. Default: 0",
        category = "Animations",
        minF = -1.5f,
        maxF = 1.5f,
        decimalPlaces = 2
    )
    var customSize = 0f

    @Property(
        type = PropertyType.CHECKBOX,
        name = "Scale Swing",
        description = "Also scale the size of the swing animation.",
        category = "Animations"
    )
    var doesScaleSwing = true

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "X",
        description = "Moves the held item. Default: 0",
        category = "Animations",
        minF = -1.5f,
        maxF = 1.5f,
        decimalPlaces = 2
    )
    var customX = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Y",
        description = "Moves the held item. Default: 0",
        category = "Animations",
        minF = -1.5f,
        maxF = 1.5f,
        decimalPlaces = 2
    )
    var customY = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Z",
        description = "Moves the held item. Default: 0",
        category = "Animations",
        minF = -1.5f,
        maxF = 1.5f,
        decimalPlaces = 2
    )
    var customZ = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Yaw",
        description = "Rotates your held item. Default: 0",
        category = "Animations",
        minF = -180f,
        maxF = 180f,
        decimalPlaces = 0
    )
    var customYaw = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Pitch",
        description = "Rotates your held item. Default: 0",
        category = "Animations",
        minF = -180f,
        maxF = 180f,
        decimalPlaces = 0
    )
    var customPitch = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Roll",
        description = "Rotates your held item. Default: 0",
        category = "Animations",
        minF = -180f,
        maxF = 180f,
        decimalPlaces = 0
    )
    var customRoll = 0f

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Speed",
        description = "Speed of the swing animation.",
        category = "Animations",
        minF = -2f,
        maxF = 1f,
        decimalPlaces = 2
    )
    var customSpeed = 0f

    @Property(
        type = PropertyType.CHECKBOX,
        name = "Ignore Haste",
        description = "Makes the chosen speed override haste modifiers.",
        category = "Animations"
    )
    var ignoreHaste = true

    @Property(
        type = PropertyType.SELECTOR,
        name = "Drinking Fix",
        description = "Pick how to handle drinking animations.",
        category = "Animations",
        options = ["No fix", "Rotationless", "Fixed"]
    )
    var drinkingSelector = 2

    @Property(
        type = PropertyType.BUTTON,
        name = "Reset Item Values",
        description = "Will not visually update gui, but reopening settings menu will show default values",
        category = "Animations"
    )
    fun demoButton() {
        customSize = 0f
        customX = 0f
        customY = 0f
        customZ = 0f
        customRoll = 0f
        customPitch = 0f
        customYaw = 0f
        doesScaleSwing = true
        ignoreHaste = true
        customSpeed = 0f
    }


    fun init() {
        initialize()
        addDependency("customMessage", "throttleNotifier")

        setCategoryDescription(
            "Custom Animations",
            "All settings that are related to custom animations. Mostly help from Aton."
        )
    }

}
