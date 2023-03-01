package dulkirmod.config

import dulkirmod.DulkirMod
import dulkirmod.utils.Utils
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Category
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import gg.essential.vigilance.data.SortingBehavior
import java.io.File

object Config : Vigilant(File("./config/dulkirmod/config.toml"), "DulkirMod", sortingBehavior = ConfigSorting) {

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Enchant Rune Particles",
        description = "ugly go bye-bye",
        category = "General"
    )
    var hideEnchantRune = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Abiphone Do-Not-Disturb",
        description = "Detects incoming calls and mutes ring audio for like 5 seconds. \nWorks as long as u don't lag particularly hard at the same time you're being called.",
        category = "General"
    )
    var abiDND = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Abiphone Caller ID",
        description = "If DND is on, still give the player a quick 1 liner to tell them who is calling.",
        category = "General"
    )
    var abiCallerID = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Hurt Cam Slider",
        description = "more or less ouchie",
        category = "General"
    )
    var hurtCamSlider = false

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Hurt Cam Intensity",
        description = "1 is default, make sure other mods noHurtCam stuff is off",
        category = "General",
        minF = 0f,
        maxF = 2f,
        decimalPlaces = 1
    )
    var hurtCamIntensity = 1f

    @Property(
        type = PropertyType.SWITCH,
        name = "Tooltip Features",
        description = "Turns on scrollable and (optional) scalable tooltips.",
        category = "General"
    )
    var scaledTooltips = false

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Tooltip Scale",
        description = "1 is default",
        category = "General",
        minF = 0f,
        maxF = 2f,
        decimalPlaces = 1
    )
    var tooltipSize = 1f

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Healer fairy",
        description = "Probably disable when not in dungeons for now. Will fix later.",
        category = "Dungeons"
    )
    var hideHealerFairy = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Heart Particles",
        description = "Useful for hyperion and healer bullshit",
        category = "Dungeons"
    )
    var hideHeartParticles = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Throttle Notifier",
        description = "Making features out of bugs wow",
        category = "Dungeons"
    )
    var throttleNotifier = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Vanquisher Broadcaster",
        description = "sends patcher sendcoords msg when you spawn a vanquisher. might make this put a waypoint later",
        category = "Random Beta Features"
    )
    var vanqBroadcast = false

    @Property(
        type = PropertyType.TEXT,
        name = "Throttle Notifier String",
        description = "How do you want to tell people you are throttled?",
        category = "Dungeons",
        placeholder = "i am being throttled zzz",
        protectedText = false
    )
    var customMessage: String = "i am being throttled zzz"

    @Property(
        type = PropertyType.SWITCH,
        name = "Throttle Notifier Spam",
        description = "LET EM KNOW!",
        category = "Dungeons"
    )
    var throttleNotifierSpam = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Extra Nametags",
        description = "Prevents some nametags not covered by skytils \"Hide non-starred nametags\" from rendering.",
        category = "General"
    )
    var hideTags = true

    // CUSTOM ANIMATIONS
    @Property(
        type = PropertyType.SWITCH,
        name = "Global Toggle",
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
        description = "Vanilla Look! Closes Settings GUI.",
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
        DulkirMod.mc.displayGuiScreen(null)
    }

    @Property(
        type = PropertyType.BUTTON,
        name = "Export Preset as String",
        description = "Base64 representation of your current config - will copy to clipboard when pressed.",
        category = "Animations",
        subcategory = "Presets"
    )
    fun presetString() {
        Utils.animationConfigToString()
    }

    @Property(
        type = PropertyType.BUTTON,
        name = "Import Preset from Clipboard",
        description = "Base64 representation of your config accepted from clipboard. Closes gui.",
        category = "Animations",
        subcategory = "Presets"
    )
    fun stringToConfig() {
        Utils.animationStringtoConfig()
    }

    @Property(
        type = PropertyType.SWITCH,
        name = "JoinDungeon Command Confirmation",
        description = "Chat notification when you push the button. Useful if you suck at navigating a numpad.",
        category = "Dungeons"
    )
    var dungeonCommandConfirm = true

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Chests that are already opened at Croesus",
        description = "Just doesn't render the item if it has the chest opened string",
        category = "Dungeons"
    )
    var hideOpenedChests = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Highlight custom player for leap in menu",
        description = "Changes texture to green wool! Use the \"/hl\" command for convenient assignment.",
        category = "Dungeons"
    )
    var highlightLeap = false

    @Property(
        type = PropertyType.TEXT,
        name = "Highlighted player name",
        description = "case-sensitive",
        category = "Dungeons",
        placeholder = "Dilkur",
        protectedText = false
    )
    var highlightLeapName: String = "Dilkur"

    @Property(
        type = PropertyType.SWITCH,
        name = "Remove Selfie Camera",
        description = "Get rid of pesky reverse third person!",
        category = "General"
    )
    var noReverse3rdPerson = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Bridge Bot Formatter",
        description = "Global Toggle",
        category = "Bridge"
    )
    var bridgeBot = false

    @Property(
        type = PropertyType.TEXT,
        name = "Bridge Bot Name",
        description = "Not case-sensitive",
        category = "Bridge",
        placeholder = "Bweefing",
        protectedText = false
    )
    var botName: String = "Bweefing"

    @Property(
        type = PropertyType.SELECTOR,
        name = "Bridge Chatter Name Color",
        description = "Pick how the player name looks.",
        category = "Bridge",
        options = ["§0Black",
            "§1Dark Blue",
            "§2Dark Green",
            "§3Dark Aqua",
            "§4Dark Red",
            "§5Dark Purple",
            "§6Gold",
            "§7Gray",
            "§8Dark Gray",
            "§9Blue",
            "§aGreen",
            "§bAqua",
            "§cRed",
            "§dLight Purple",
            "§eYellow",
            "§fWhite",
            "§zSBA Chroma"
        ]
    )
    var bridgeColor = 6

    @Property(
        type = PropertyType.SWITCH,
        name = "Ghast Notification",
        description = "Shows a title at 9:00pm for bestiary",
        category = "Bestiary",
        subcategory = "Notifications"
    )
    var notifyGhast = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Zombie Villager Notification",
        description = "Shows a title at 8:00pm for bestiary",
        category = "Bestiary",
        subcategory = "Notifications"
    )
    var notifyZombieVillager = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Broken Hype Notification",
        description = "Tells you if you are no longer getting bestiary! Requires champion and book of stats on your item. LEAVE OFF IF FISHING.",
        category = "Bestiary",
        subcategory = "Notifications"
    )
    var notifyHype = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Matcho Spawn Alert!",
        description = "Alerts you if your lobby becomes EXPLOSIVE!",
        category = "Bestiary",
        subcategory = "Notifications"
    )
    var notifyMatcho = false

    @Property(
        type = PropertyType.SELECTOR,
        name = "Bestiary Notification Color",
        description = "Changes color some bestiary features.",
        category = "Bestiary",
        subcategory = "Notifications",
        options = ["§0Black",
            "§1Dark Blue",
            "§2Dark Green",
            "§3Dark Aqua",
            "§4Dark Red",
            "§5Dark Purple",
            "§6Gold",
            "§7Gray",
            "§8Dark Gray",
            "§9Blue",
            "§aGreen",
            "§bAqua",
            "§cRed",
            "§dLight Purple",
            "§eYellow",
            "§fWhite",
            "§zSBA Chroma"
        ]
    )
    var bestiaryNotifColor = 15

    @Property(
        type = PropertyType.SWITCH,
        name = "Text Shadow",
        description = "Shows text shadow for notification",
        category = "Bestiary",
        subcategory = "Notifications"
    )
    var bestiaryTextShadow = false

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Scale",
        description = "Size of notification!",
        category = "Bestiary",
        subcategory = "Notifications",
        minF = 0f,
        maxF = 1f,
        decimalPlaces = 1
    )
    var bestiaryNotifSize = .7f


    @Property(
        type = PropertyType.SWITCH,
        name = "Alert Noises",
        description = "Uses relevant mob sounds, doesn't override audio/patcher settings",
        category = "Bestiary",
        subcategory = "Audio"
    )
    var bestiaryAlertSounds = false

    @Property(
        type = PropertyType.DECIMAL_SLIDER,
        name = "Alert Volume",
        description = "Volume of notification!",
        category = "Bestiary",
        subcategory = "Audio",
        minF = 0f,
        maxF = 1f,
        decimalPlaces = 1
    )
    var bestiaryNotifVol = .7f

    @Property(
        type = PropertyType.BUTTON,
        name = "Demo Volume Selection",
        description = "Plays the Ghast Noise as Reference, Might add individual sliders later but this seems like enough",
        category = "Bestiary",
        subcategory = "Audio"
    )
    fun demoVolume() {
        DulkirMod.mc.thePlayer.playSound("mob.ghast.scream", 1f * bestiaryNotifVol, 1f)
    }

    @Property(
        type = PropertyType.SWITCH,
        name = "Hide Arachne Loot Nametags",
        description = "Useful when killing a lot of them",
        category = "Bestiary",
        subcategory = "Arachne"
    )
    var hideArachneTags = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Arachne kill timer",
        description = "Shows in chat.",
        category = "Bestiary",
        subcategory = "Arachne"
    )
    var arachneKillTimer = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Arachne spawn countdown",
        description = "Shows how long it takes for the arachne to spawn (in world).",
        category = "Bestiary",
        subcategory = "Arachne"
    )
    var arachneSpawnTimer = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Show Arachne Keeper Spawn Locations",
        description = "simple text waypoints",
        category = "Bestiary",
        subcategory = "Arachne"
    )
    var keeperWaypoints = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Focus Mode",
        description = "only show the nametags of keepers. make sure to turn off when you're done",
        category = "Bestiary",
        subcategory = "Arachne"
    )
    var keeperFocus = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Garden Visitor Alert",
        description = "Notifies you if you have max garden visitors in queue",
        category = "Farming"
    )
    var notifyMaxVisitors = false

    @Property(
        type = PropertyType.SWITCH,
        name = "Persistent alert",
        description = "If turned on, the alert will continue to flash until dealt with.",
        category = "Farming"
    )
    var persistentAlert = true

    fun init() {
        initialize()
        addDependency("customMessage", "throttleNotifier")
        addDependency("throttleNotifierSpam", "throttleNotifier")
        addDependency("bestiaryNotifVol", "bestiaryAlertSounds")
        addDependency("demoVolume", "bestiaryAlertSounds")
        addDependency("highlightLeapName", "highlightLeap")
        addDependency("abiCallerID", "abiDND")
        addDependency("hurtCamIntensity", "hurtCamSlider")
        addDependency("tooltipSize", "scaledTooltips")
        addDependency("persistentAlert", "notifyMaxVisitors")

        setCategoryDescription(
            "Custom Animations",
            "All settings that are related to custom animations. Mostly help from Aton."
        )
        setCategoryDescription(
            "Bridge",
            "Dm me on discord with formatting issues."
        )
    }

    private object ConfigSorting : SortingBehavior() {
        override fun getCategoryComparator(): Comparator<in Category> = Comparator { o1, o2 ->
            if (o1.name == "General") return@Comparator -1
            if (o2.name == "General") return@Comparator 1
            else compareValuesBy(o1, o2) {
                it.name
            }
        }
    }
}
