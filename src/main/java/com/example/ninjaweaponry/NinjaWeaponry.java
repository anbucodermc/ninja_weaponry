package com.example.ninjaweaponry;

import com.example.ninjaweaponry.registry.ModCreativeTabs;
import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Ninja Weaponry - main mod entry point.
 *
 * Targets Minecraft Forge 26.2 (new naming system). The registration flow below uses the
 * long-stable Forge {@code DeferredRegister} pattern; if 26.2 renames any of the referenced
 * classes, the adjustments are localised to the registry.* package and this constructor.
 */
@Mod(NinjaWeaponry.MODID)
public class NinjaWeaponry {

    public static final String MODID = "ninjaweaponry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NinjaWeaponry() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        LOGGER.info("[{}] Ninja Weaponry loaded: katana, shuriken, nunchucks registered.", MODID);
    }
}
