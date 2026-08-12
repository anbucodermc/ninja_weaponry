package com.example.ninjaweaponry.entity;

import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * The flying shuriken projectile. Damages the first entity it strikes, then drops itself so it can
 * be picked back up. Movement/spin is animated client-side by the renderer.
 *
 * In 26.2 {@code ThrowableItemProjectile} moved to the {@code ...projectile.throwableitemprojectile}
 * package, its thrower constructor takes the launch {@link ItemStack}, {@code Entity#hurt} became
 * {@code hurtOrSimulate}, and {@code spawnAtLocation} requires a {@link ServerLevel}.
 */
public class ShurikenEntity extends ThrowableItemProjectile {

    private static final float DAMAGE = 5.0f;

    public ShurikenEntity(EntityType<? extends ShurikenEntity> type, Level level) {
        super(type, level);
    }

    public ShurikenEntity(Level level, LivingEntity thrower, ItemStack stack) {
        super(ModEntities.SHURIKEN.get(), thrower, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SHURIKEN.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide()) {
            result.getEntity().hurtOrSimulate(damageSources().thrown(this, getOwner()), DAMAGE);
            level().playSound(null, blockPosition(),
                    SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.8f, 1.6f);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (level() instanceof ServerLevel server) {
            server.playSound(null, blockPosition(),
                    SoundEvents.TRIDENT_HIT_GROUND, SoundSource.PLAYERS, 0.6f, 1.4f);
            // drop the shuriken so it can be retrieved
            spawnAtLocation(server, getDefaultItem());
            discard();
        }
    }
}
