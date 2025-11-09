package com.gtnewhorizons.angelica.mixins.late.client.galacticraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.GL11;

@Mixin(targets = "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore", remap = false)
public class MixinClientProxyCore {

    @Redirect(
        method = "renderSpaceship(FLnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V"
        )
    )
    private static void angelica$noOpGlRotatef(float angle, float x, float y, float z) {
        // Disabled Galacticraft's gravity turn rotation.
    }
}
