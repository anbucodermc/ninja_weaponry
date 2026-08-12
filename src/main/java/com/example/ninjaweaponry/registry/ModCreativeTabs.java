package com.example.ninjaweaponry.registry;

import com.example.ninjaweaponry.NinjaWeaponry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Creative-mode tab holding every Ninja Weaponry item.
 */
public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NinjaWeaponry.MODID);

    public static final RegistryObject<CreativeModeTab> NINJA_TAB = TABS.register("ninja_weaponry",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + NinjaWeaponry.MODID + ".ninja_weaponry"))
                    .icon(() -> new ItemStack(ModItems.KATANA.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.KATANA.get());
                        output.accept(ModItems.SHURIKEN.get());
                        output.accept(ModItems.NUNCHUCKS.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
