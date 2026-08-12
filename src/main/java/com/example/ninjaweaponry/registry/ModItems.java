package com.example.ninjaweaponry.registry;

import com.example.ninjaweaponry.NinjaWeaponry;
import com.example.ninjaweaponry.item.KatanaItem;
import com.example.ninjaweaponry.item.NunchuckItem;
import com.example.ninjaweaponry.item.ShurikenItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Item registry for Ninja Weaponry.
 *
 * Forge 26.2 removed {@code SwordItem}/{@code Tier}; melee weapons are now built by applying
 * {@code Item.Properties.sword(ToolMaterial, attackDamage, attackSpeed)} to a plain item.
 * Each weapon still exposes a 2D inventory model and a 3D held model via the
 * {@code forge:separate_transforms} model loader (see assets/.../models/item).
 */
public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NinjaWeaponry.MODID);

    // Katana: balanced damage, faster than a vanilla sword (vanilla sword speed = -2.4f).
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new KatanaItem(new Item.Properties()
                    .sword(ToolMaterial.IRON, 3.0f, -2.0f)
                    .durability(720)));

    // Shuriken: throwable ninja star, also usable as a light melee weapon. Stackable.
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken",
            () -> new ShurikenItem(new Item.Properties().stacksTo(16)));

    // Nunchucks: fast, high-knockback melee weapon.
    public static final RegistryObject<Item> NUNCHUCKS = ITEMS.register("nunchucks",
            () -> new NunchuckItem(new Item.Properties()
                    .sword(ToolMaterial.IRON, 2.0f, -1.6f)
                    .durability(480)));

    private ModItems() {
    }

    public static void register(BusGroup bus) {
        ITEMS.register(bus);
    }
}
