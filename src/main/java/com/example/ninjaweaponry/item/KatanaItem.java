package com.example.ninjaweaponry.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Katana - a ninja sword with balanced damage and a faster swing than a vanilla sword.
 *
 * Sword attributes are supplied at registration via {@code Item.Properties.sword(...)}; this class
 * only layers on a swing sound. Forge 26.2's {@code Item.hurtEnemy} returns {@code void}.
 */
public class KatanaItem extends Item {

    public KatanaItem(Properties properties) {
        super(properties);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.8f, 1.3f);
        }
    }
}
