package com.example.ninjaweaponry.registry;

import com.example.ninjaweaponry.NinjaWeaponry;
import com.example.ninjaweaponry.item.KatanaItem;
import com.example.ninjaweaponry.item.NunchuckItem;
import com.example.ninjaweaponry.item.ShurikenItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Item registry for Ninja Weaponry.
 *
 * <p>26.2 items must carry their registry id on {@code Item.Properties} (via {@code setId}) before
 * construction — Forge's {@code register(name, supplier)} does not inject it because the properties
 * are built inside the supplier. {@link #props(String)} sets the id to keep the two in sync.</p>
 *
 * <p>{@code SwordItem}/{@code Tier} were removed; melee weapons are built by applying
 * {@code Item.Properties.sword(ToolMaterial, attackDamage, attackSpeed)} to a plain item.</p>
 */
public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NinjaWeaponry.MODID);

    // Katana: balanced damage, faster than a vanilla sword (vanilla sword speed = -2.4f).
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new KatanaItem(props("katana")
                    .sword(ToolMaterial.IRON, 3.0f, -2.0f)
                    .durability(720)));

    // Shuriken: throwable ninja star, also usable as a light melee weapon. Stackable.
    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken",
            () -> new ShurikenItem(props("shuriken").stacksTo(16)));

    // Nunchucks: fast, high-knockback melee weapon.
    public static final RegistryObject<Item> NUNCHUCKS = ITEMS.register("nunchucks",
            () -> new NunchuckItem(props("nunchucks")
                    .sword(ToolMaterial.IRON, 2.0f, -1.6f)
                    .durability(480)));

    private ModItems() {
    }

    /** Item.Properties pre-populated with the item's registry id (required in 26.2). */
    private static Item.Properties props(String name) {
        return new Item.Properties().setId(
                ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(NinjaWeaponry.MODID, name)));
    }

    public static void register(BusGroup bus) {
        ITEMS.register(bus);
    }
}
