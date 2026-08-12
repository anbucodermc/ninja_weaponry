package com.example.ninjaweaponry.item;

import com.example.ninjaweaponry.entity.ShurikenEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Shuriken - a throwing star that is both throwable and usable as a light melee weapon.
 *
 * <p>Right-click launches a {@link ShurikenEntity}; the stack shrinks by one (unless the player
 * is in creative). In 26.2, {@code Item.use} returns an {@link InteractionResult}.</p>
 */
public class ShurikenItem extends Item {

    public ShurikenItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.6f, 1.4f);

        if (!level.isClientSide()) {
            ShurikenEntity shuriken = new ShurikenEntity(level, player, stack.copyWithCount(1));
            // shoot from the player's look vector; velocity 1.6, tight spread
            shuriken.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.6f, 1.0f);
            level.addFreshEntity(shuriken);
        }

        player.getCooldowns().addCooldown(stack, 8);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        // light melee: a quick slash sound on top of the base item damage
        attacker.level().playSound(null, target.blockPosition(),
                SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS, 0.7f, 1.6f);
    }
}
