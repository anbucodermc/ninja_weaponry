# Ninja Weaponry

A Minecraft **Forge 26.2** mod (new Forge naming system) that adds a set of ninja weapons.
Every item ships **both** a 2D inventory icon and a 3D held/world model.

| Weapon | Type | Highlights |
| --- | --- | --- |
| **Katana** | Melee sword | Balanced damage, faster swing than a vanilla sword (`-2.0` vs `-2.4`) |
| **Shuriken** | Throwable **and** melee | Right-click to throw as a projectile; left-click for light melee; stackable; drops on impact so it can be retrieved |
| **Nunchucks** | Fast melee | Very fast attack speed with an extra knockback impulse on hit |

All three appear in a dedicated **Ninja Weaponry** creative tab.

---

## Requirements

- **JDK 25** — Forge 26.2 compiles and runs on Java 25. The Gradle toolchain is pinned to 25 in
  `build.gradle`, and Gradle 9.3 itself runs on Java 25.
- **Gradle 9.3+** — required by ForgeGradle 7. Provided by the committed wrapper; just run `gradlew`.
- Internet access on the first build (ForgeGradle downloads, remaps, and recompiles Minecraft).

The toolchain here is specific and worth stating plainly, because the versions are tightly coupled:

| Layer | Version | Why |
| --- | --- | --- |
| Minecraft / Forge | `26.2` / `26.2-65.1.0` | The target |
| ForgeGradle | `7.0.32` | The Forge 26.2 build plugin (the "mavenizer" rewrite, not FG6) |
| Gradle | `9.3.0` (wrapper) | Minimum required by ForgeGradle 7 |
| Java | `25` | Required by Forge 26.2; also runs Gradle 9.3 |

The Gradle wrapper (`gradlew`, `gradlew.bat`, `gradle/wrapper/`, including `gradle-wrapper.jar`) is
committed, so no wrapper setup is needed — clone and build.

## Build & run

On Windows PowerShell, prefix with `.\` (PowerShell will not run `gradlew.bat` from the current
directory otherwise):

```bash
.\gradlew.bat build
```

```bash
.\gradlew.bat runClient
```

```bash
.\gradlew.bat runData
```

(`build` produces the mod jar; `runClient` launches a dev client with the mod loaded; `runData`
runs data generation into `src/generated/resources`. On macOS/Linux use `./gradlew` instead.)

The finished mod JAR lands in `build/libs/` as `ninjaweaponry-26.2-1.0.0.jar`. Drop it into a
Forge 26.2 instance's `mods/` folder.

## Versions

The Minecraft/Forge versions are pinned in `gradle.properties`:

```properties
minecraft_version=26.2
forge_version=65.1.0
mappings_channel=official
mappings_version=26.2
```

To target a different 26.x build, change these (values come from
<https://files.minecraftforge.net/>).

---

## Project layout

```
ninja_weaponry/
├── build.gradle                     # ForgeGradle build
├── settings.gradle
├── gradle.properties                # mod + MC/Forge versions
├── gradlew / gradlew.bat            # wrapper scripts (JAR generated on first setup)
└── src/main/
    ├── java/com/example/ninjaweaponry/
    │   ├── NinjaWeaponry.java        # @Mod entry point
    │   ├── registry/                 # ModItems, ModEntities, ModCreativeTabs
    │   ├── item/                     # KatanaItem, ShurikenItem, NunchuckItem
    │   ├── entity/ShurikenEntity.java
    │   └── client/                   # ClientSetup + ShurikenRenderer (client-only)
    └── resources/
        ├── META-INF/mods.toml
        ├── assets/ninjaweaponry/
        │   ├── lang/en_us.json
        │   ├── models/item/          # *_2d (inventory) + *_3d (held) + separate_transforms
        │   └── textures/item, textures/entity
        └── data/ninjaweaponry/recipes/
```

### Item models (3D)

Each item (`models/item/katana.json`, etc.) is a cuboid `elements` model with per-perspective
`display` transforms — a real 3D object in hand, on the ground, and in the inventory (the `gui`
transform angles it for a readable icon).

> Note: Forge 26.2 **removed** the `forge:separate_transforms` model loader that older versions used
> to pair a flat 2D inventory sprite with a separate 3D held model. A strict flat-icon-plus-distinct-
> 3D-model split now requires a custom baked `ItemModel`; this mod uses one 3D model across all
> perspectives instead.

## Client / server separation

- `ShurikenItem#use` only spawns the projectile on the logical server; the projectile entity is
  registered and synced to clients by vanilla's entity tracker.
- Renderer registration lives in `client/ClientSetup#init`, invoked from the mod constructor only
  when `FMLEnvironment.dist == Dist.CLIENT`, so client classes are never loaded on a dedicated
  server. EventBus 7 gives `EntityRenderersEvent.RegisterRenderers` its own static `BUS`, so the
  listener is added there rather than through `@Mod.EventBusSubscriber`.

## Data generation

`gradlew runData` runs `data/DataGenerators` (wired via `GatherDataEvent`) and writes the en_us
language file to `src/generated/resources` (committed, and on the main resource path). Crafting
recipes are hand-authored under `data/ninjaweaponry/recipes` because 26.2's vanilla recipe-datagen
entry point (`RecipeProvider.Runner`) is not accessible to mods.

## Verified

`gradlew build`, `gradlew runData`, and `gradlew runClient` all succeed against Forge 26.2: the mod
constructs, registers its items/entity/creative tab, generates its language file, and loads into the
Minecraft client (main menu reached, all three item models load without error).

## Textures

The shipped textures under `assets/ninjaweaponry/textures/` are simple 16×16 **placeholders**.
Replace them with real art (same file names) whenever you like.

## Notes on the Forge 26.2 API

Forge 26.2 changed a number of APIs relative to the 1.20/1.21 era. This project is written against
the real 26.2 API and **compiles cleanly** (`gradlew build` → `BUILD SUCCESSFUL`). Notable changes
handled here:

- **EventBus 7** — the mod bus is a `BusGroup` from `getModBusGroup()` (not `IEventBus` /
  `getModEventBus()`); `@SubscribeEvent` moved to `net.minecraftforge.eventbus.api.listener`.
- **Component-based items** — `SwordItem`/`Tier` were removed; weapons are built with
  `Item.Properties.sword(ToolMaterial, damage, speed)`. `Item.hurtEnemy` now returns `void`, and
  `Item.use` returns `InteractionResult` (no more `InteractionResultHolder`).
- **Projectiles** — `ThrowableItemProjectile` moved to `...projectile.throwableitemprojectile`;
  entity damage uses `hurtOrSimulate`, and `spawnAtLocation` takes a `ServerLevel`.
- **Mappings renames** — `ResourceLocation` → `Identifier`; `EntityType.Builder.build` takes a
  `ResourceKey`; `Level#isClientSide` is now the method `isClientSide()`.

The one remaining compiler warning is that `FMLJavaModLoadingContext.get()` is deprecated for
removal; it still functions in 26.2.

## License

MIT — see the mod metadata in `mods.toml`.
