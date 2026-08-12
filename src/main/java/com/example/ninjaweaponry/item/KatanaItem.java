package com.example.ninjaweaponry.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

/**
 * Katana - a ninja sword with balanced damage and a faster swing than a vanilla sword.
 *
 * Attack speed is supplied at registration ({@code -2.0f} vs the vanilla sword's {@code -2.4f}),
 * giving the katana a noticeably quicker recovery.
 */
public class KatanaItem extends SwordItem {

    public KatanaItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(net.minecraft.world.item.ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (!level.isClientSide) {
            level.playSound(null, attacker.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.8f, 1.3f);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
