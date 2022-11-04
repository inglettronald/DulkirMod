package dulkirmod.utils

import dulkirmod.config.Config

data class ConfigData(
    val size: Float = Config.customSize,
    val scaleSwing: Boolean = Config.doesScaleSwing,
    val x: Float = Config.customX,
    val y: Float = Config.customY,
    val z: Float = Config.customZ,
    val yaw: Float = Config.customYaw,
    val pitch: Float = Config.customPitch,
    val roll: Float = Config.customRoll,
    val speed: Float = Config.customSpeed,
    val ignoreHaste: Boolean = Config.ignoreHaste,
    val drinkingFix: Int = Config.drinkingSelector,
)
