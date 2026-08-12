package com.example.ninjaweaponry.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Nunchucks - a fast melee weapon with high knockback.
 *
 * Extends {@link SwordItem} so it benefits from the sweep/attack pipeline, but is registered
 * with a very fast attack speed. On hit it applies an extra outward knockback impulse.
 */
public class NunchuckItem extends SwordItem {

    public NunchuckItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (!level.isClientSide) {
            Vec3 push = target.position().subtract(attacker.position()).normalize().scale(0.6);
            target.push(push.x, 0.25, push.z);
            target.hurtMarked = true;
            level.playSound(null, target.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.0f, 1.5f);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
