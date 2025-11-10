package com.gtnewhorizons.angelica.mixins.late.client.galacticraft;

import com.gtnewhorizons.angelica.config.CompatConfig;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "micdoodle8.mods.galacticraft.core.client.render.entities.RenderPlayerGC", remap = false)
public class MixinClientProxyCore {

    @Redirect(
        method = "doRender",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V",
            ordinal = 0
        ),
        require = 1
    )
    private static void angelica$conditionalGlRotatef(float angle, float x, float y, float z) {
        // Debug logging to confirm mixin execution
        System.out.println("[DEBUG Angelica GC Fix] Mixin triggered! Config: " + CompatConfig.fixGalacticraftGravityTurn);
        
        // Force skip for testing (always skip the GL rotation)
        System.out.println("[DEBUG] GL rotation skipped!");
        // If config is enabled (true), skip the rotation entirely (no-op, like commenting it out)
        // This fixes spacestation rendering in Angelica by disabling the gravity turn GL rotation
    }
}
