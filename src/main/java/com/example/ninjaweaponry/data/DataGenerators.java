package com.example.ninjaweaponry.data;

import com.example.ninjaweaponry.NinjaWeaponry;
import com.example.ninjaweaponry.registry.ModEntities;
import com.example.ninjaweaponry.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Forge data generation for Ninja Weaponry. Run with {@code gradlew runData}; output lands in
 * {@code src/generated/resources} (added to the main resource set in build.gradle).
 *
 * <p>Generates the en_us language file. Crafting recipes and item models remain hand-authored:
 * the models use the {@code forge:separate_transforms} loader (2D inventory + 3D held), and in
 * 26.2 the vanilla recipe-datagen entry point ({@code RecipeProvider.Runner}) is not accessible to
 * mods, so the recipe JSONs under {@code data/ninjaweaponry/recipes} are maintained directly.</p>
 */
@Mod.EventBusSubscriber(modid = NinjaWeaponry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new ModLanguage(output));
    }

    /** en_us language entries. */
    static final class ModLanguage extends LanguageProvider {

        ModLanguage(PackOutput output) {
            super(output, NinjaWeaponry.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            add("itemGroup." + NinjaWeaponry.MODID + ".ninja_weaponry", "Ninja Weaponry");
            add(ModItems.KATANA.get(), "Katana");
            add(ModItems.SHURIKEN.get(), "Shuriken");
            add(ModItems.NUNCHUCKS.get(), "Nunchucks");
            add(ModEntities.SHURIKEN.get(), "Shuriken");
        }
    }
}
