package com.gtnewhorizons.angelica.mixins.late.client.galacticraft;

import com.gtnewhorizons.angelica.config.CompatConfig;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore", remap = false)
public class MixinClientProxyCore {

    @Redirect(
        method = "renderPlayer(Lnet/minecraft/entity/player/EntityPlayer;F)V",  // Corrected method name and signature
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V",
            ordinal = 0  // Targets the first glRotatef in the method; adjust if there are multiples
        ),
        require = 1  // Fails loudly if no match, for debugging
    )
    private static void angelica$conditionalGlRotatef(float angle, float x, float y, float z) {
        if (!CompatConfig.fixGalacticraftGravityTurn) {
            // Only apply the rotation if the config is disabled (normal Galacticraft behavior)
            GL11.glRotatef(angle, x, y, z);
        }
        // If config is enabled, skip the rotation (no-op for Angelica compatibility)
    }
}
