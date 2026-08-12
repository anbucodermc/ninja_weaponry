package com.example.ninjaweaponry;

import com.example.ninjaweaponry.client.ClientSetup;
import com.example.ninjaweaponry.registry.ModCreativeTabs;
import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

/**
 * Ninja Weaponry - main mod entry point (Minecraft Forge 26.2).
 *
 * Forge 26.2 moved to EventBus 7, so the mod's bus is now a {@link BusGroup}. The loading context
 * is injected into the mod constructor (the modern, non-deprecated path) rather than fetched via
 * the deprecated static {@code FMLJavaModLoadingContext.get()}.
 */
@Mod(NinjaWeaponry.MODID)
public class NinjaWeaponry {

    public static final String MODID = "ninjaweaponry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NinjaWeaponry(FMLJavaModLoadingContext context) {
        final BusGroup modBus = context.getModBusGroup();

        ModItems.register(modBus);
        ModEntities.register(modBus);
        ModCreativeTabs.register(modBus);

        // Client-only wiring (renderer registration). Guarded so client classes are never loaded
        // on a dedicated server.
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientSetup.init();
        }

        LOGGER.info("[{}] Ninja Weaponry loaded: katana, shuriken, nunchucks registered.", MODID);
    }
}
