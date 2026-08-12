package com.example.ninjaweaponry.registry;

import com.example.ninjaweaponry.NinjaWeaponry;
import com.example.ninjaweaponry.entity.ShurikenEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Entity registry. In 26.2, {@code EntityType.Builder.build(...)} takes a {@link ResourceKey}
 * (built from an {@link Identifier} — the renamed {@code ResourceLocation}) instead of a String.
 */
public final class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NinjaWeaponry.MODID);

    public static final RegistryObject<EntityType<ShurikenEntity>> SHURIKEN =
            ENTITY_TYPES.register("shuriken",
                    () -> EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(NinjaWeaponry.MODID, "shuriken"))));

    private ModEntities() {
    }

    public static void register(BusGroup bus) {
        ENTITY_TYPES.register(bus);
    }
}
