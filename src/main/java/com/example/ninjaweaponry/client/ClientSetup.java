package com.example.ninjaweaponry.client;

import com.example.ninjaweaponry.NinjaWeaponry;
import com.example.ninjaweaponry.client.renderer.ShurikenRenderer;
import com.example.ninjaweaponry.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only setup: binds the shuriken entity to its renderer. Kept on the mod event bus and
 * guarded by {@code Dist.CLIENT} so nothing here is classloaded on a dedicated server.
 */
@Mod.EventBusSubscriber(modid = NinjaWeaponry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {

    private ClientSetup() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SHURIKEN.get(), ShurikenRenderer::new);
    }
}
