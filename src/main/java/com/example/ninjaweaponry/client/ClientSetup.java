package com.example.ninjaweaponry.client;

import com.example.ninjaweaponry.client.renderer.ShurikenRenderer;
import com.example.ninjaweaponry.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

/**
 * Client-only setup: binds the shuriken entity to its renderer.
 *
 * <p>EventBus 7 gives {@code EntityRenderersEvent.RegisterRenderers} its own static {@code BUS}
 * (it is not an {@code IModBusEvent}), so the listener is added directly to that bus rather than
 * through {@code @Mod.EventBusSubscriber}. {@link #init()} is called only on the client (guarded in
 * the main mod class), so this class is never loaded on a dedicated server.</p>
 */
public final class ClientSetup {

    private ClientSetup() {
    }

    public static void init() {
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(ClientSetup::onRegisterRenderers);
    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SHURIKEN.get(), ShurikenRenderer::new);
    }
}
