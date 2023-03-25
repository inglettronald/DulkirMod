package dulkirmod.utils

import dulkirmod.config.DulkirConfig

data class ConfigData(
    val size: Float = DulkirConfig.customSize,
    val scaleSwing: Boolean = DulkirConfig.doesScaleSwing,
    val x: Float = DulkirConfig.customX,
    val y: Float = DulkirConfig.customY,
    val z: Float = DulkirConfig.customZ,
    val yaw: Float = DulkirConfig.customYaw,
    val pitch: Float = DulkirConfig.customPitch,
    val roll: Float = DulkirConfig.customRoll,
    val speed: Float = DulkirConfig.customSpeed,
    val ignoreHaste: Boolean = DulkirConfig.ignoreHaste,
    val drinkingFix: Int = DulkirConfig.drinkingSelector,
)
