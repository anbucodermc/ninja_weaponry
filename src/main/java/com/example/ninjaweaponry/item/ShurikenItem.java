package com.example.ninjaweaponry.item;

import com.example.ninjaweaponry.entity.ShurikenEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Shuriken - a throwing star that is both throwable and usable as a light melee weapon.
 *
 * <p>Right-click launches a {@link ShurikenEntity}; the stack shrinks by one (unless the player
 * is in creative). Left-click melee is handled by {@link #hurtEnemy} for a small bleed of extra
 * effect on top of the base item damage.</p>
 */
public class ShurikenItem extends Item {

    public ShurikenItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.6f, 1.4f);

        if (!level.isClientSide) {
            ShurikenEntity shuriken = new ShurikenEntity(level, player);
            shuriken.setItem(stack);
            // shoot from the player's look vector; velocity 1.6, tight spread
            shuriken.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.6f, 1.0f);
            level.addFreshEntity(shuriken);
        }

        player.getCooldowns().addCooldown(this, 8);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // light melee: mark the target so knockback/damage apply and add a quick slash sound
        attacker.level().playSound(null, target.blockPosition(),
                SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS, 0.7f, 1.6f);
        return true;
    }
}
