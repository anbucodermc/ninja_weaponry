package com.example.ninjaweaponry.entity;

import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * The flying shuriken projectile. Damages the first entity it strikes, then drops itself so it can
 * be picked back up. Movement/spin is animated client-side by the renderer.
 */
public class ShurikenEntity extends ThrowableItemProjectile {

    private static final float DAMAGE = 5.0f;

    public ShurikenEntity(EntityType<? extends ShurikenEntity> type, Level level) {
        super(type, level);
    }

    public ShurikenEntity(Level level, LivingEntity thrower) {
        super(ModEntities.SHURIKEN.get(), thrower, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SHURIKEN.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide) {
            result.getEntity().hurt(damageSources().thrown(this, getOwner()), DAMAGE);
            level().playSound(null, blockPosition(),
                    SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.8f, 1.6f);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            level().playSound(null, blockPosition(),
                    SoundEvents.TRIDENT_HIT_GROUND, SoundSource.PLAYERS, 0.6f, 1.4f);
            // drop the shuriken so it can be retrieved
            spawnAtLocation(getDefaultItem());
            discard();
        }
    }
}
