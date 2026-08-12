package com.example.ninjaweaponry.client.renderer;

import com.example.ninjaweaponry.entity.ShurikenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

/**
 * Renders the thrown shuriken using the item's model. The spinning-while-flying look is produced
 * by the base {@link ThrownItemRenderer} together with the item's 3D held model.
 */
public class ShurikenRenderer extends ThrownItemRenderer<ShurikenEntity> {

    public ShurikenRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0f, false);
    }
}
