package com.example.ninjaweaponry;

import com.example.ninjaweaponry.registry.ModCreativeTabs;
import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Ninja Weaponry - main mod entry point (Minecraft Forge 26.2).
 *
 * Forge 26.2 moved to EventBus 7, so the mod's bus is now a {@link BusGroup} obtained from
 * {@code getModBusGroup()} rather than the old {@code IEventBus getModEventBus()}.
 */
@Mod(NinjaWeaponry.MODID)
public class NinjaWeaponry {

    public static final String MODID = "ninjaweaponry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NinjaWeaponry() {
        final BusGroup modBus = FMLJavaModLoadingContext.get().getModBusGroup();

        ModItems.register(modBus);
        ModEntities.register(modBus);
        ModCreativeTabs.register(modBus);

        LOGGER.info("[{}] Ninja Weaponry loaded: katana, shuriken, nunchucks registered.", MODID);
    }
}
