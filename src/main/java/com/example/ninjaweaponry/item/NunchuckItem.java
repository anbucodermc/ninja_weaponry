package com.example.ninjaweaponry.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Nunchucks - a fast melee weapon with high knockback.
 *
 * Fast attack speed comes from {@code Item.Properties.sword(...)} at registration; on hit this
 * applies an extra outward knockback impulse.
 */
public class NunchuckItem extends Item {

    public NunchuckItem(Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        if (!level.isClientSide()) {
            Vec3 push = target.position().subtract(attacker.position()).normalize().scale(0.6);
            target.push(push.x, 0.25, push.z);
            target.hurtMarked = true;
            level.playSound(null, target.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.0f, 1.5f);
        }
    }
}
