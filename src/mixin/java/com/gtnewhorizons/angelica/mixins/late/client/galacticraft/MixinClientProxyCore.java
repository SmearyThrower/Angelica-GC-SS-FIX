package com.gtnewhorizons.angelica.mixins.late.client.galacticraft;

import com.gtnewhorizons.angelica.config.CompatConfig;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore", remap = false)
public class MixinClientProxyCore {

    @Redirect(
        method = "renderPlayer(Lnet/minecraft/entity/player/EntityPlayer;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V",
            ordinal = 0  // Targets the first (and only) glRotatef in the method
        ),
        require = 1  // Fails loudly if not found, for debugging
    )
    private static void angelica$conditionalGlRotatef(float angle, float x, float y, float z) {
        if (!CompatConfig.fixGalacticraftGravityTurn) {
            // Only apply the rotation if the config is disabled (normal Galacticraft behavior)
            GL11.glRotatef(angle, x, y, z);
        }
        // If config is enabled (true), skip the rotation entirely (no-op, like commenting it out)
        // This fixes spacestation rendering in Angelica by disabling the gravity turn GL rotation
    }
}
