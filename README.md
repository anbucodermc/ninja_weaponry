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

- **JDK 21** (Forge 26.2 targets Java 21). A newer JDK is fine as an installed toolchain, but the
  Gradle Java toolchain is pinned to 21 in `build.gradle`.
- **Gradle 8.8** via the wrapper (see the one-time setup below).
- Internet access on the first build (ForgeGradle downloads and deobfuscates Minecraft).

## ⚠️ One-time setup: generate the Gradle wrapper JAR

This repo ships `gradlew`, `gradlew.bat`, and `gradle/wrapper/gradle-wrapper.properties`, but the
binary `gradle/wrapper/gradle-wrapper.jar` is **not** committed (it was not generated in the
environment that scaffolded the project). Produce it once using **any** of these:

- **IntelliJ IDEA** — `File ▸ Open` this folder. IntelliJ imports the Gradle project and creates the
  wrapper JAR automatically. (This is the recommended Forge workflow.)
- **A local Gradle install** — install Gradle 8.8, then run:
  ```bash
  gradle wrapper --gradle-version 8.8
  ```
- After that, `./gradlew` (or `gradlew.bat` on Windows) works normally.

## Build & run

```bash
# Windows
gradlew.bat build          # produces build/libs/ninjaweaponry-<mcver>-1.0.0.jar
gradlew.bat runClient      # launch a dev client with the mod loaded
gradlew.bat runData        # run data generation into src/generated/resources

# macOS / Linux
./gradlew build
./gradlew runClient
```

The finished mod JAR lands in `build/libs/`. Drop it into a Forge 26.2 instance's `mods/` folder.

## Pinning exact versions

`gradle.properties` carries placeholder version values for the 26.2 line:

```properties
minecraft_version=1.21.x
forge_version=26.2-65.1.0
mappings_version=1.21.x
```

Replace these with the exact Minecraft/Forge build you are targeting (from
<https://files.minecraftforge.net/>) before your first build.

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

### How "2D inventory + 3D held" works

Each item's top-level model (e.g. `models/item/katana.json`) uses Forge's
`forge:separate_transforms` loader. The `base`/`fixed`/`gui` perspective points at a flat
`item/generated` sprite (`*_2d.json`), while the first-person, third-person, and ground
perspectives point at a cuboid `*_3d.json` model. Result: a clean flat icon in the inventory and a
real 3D object in hand and on the ground.

## Client / server separation

- `ShurikenItem#use` only spawns the projectile on the logical server; the projectile entity is
  registered on the common bus and synced to clients by vanilla's entity tracker.
- Renderer registration lives in `client/ClientSetup`, guarded by `@Mod.EventBusSubscriber(..., Dist.CLIENT)`
  so it is never classloaded on a dedicated server.

## Textures

The shipped textures under `assets/ninjaweaponry/textures/` are simple 16×16 **placeholders**.
Replace them with real art (same file names) whenever you like.

## Notes on Forge 26.2

The code follows established Forge conventions (`DeferredRegister`, `ThrowableItemProjectile`,
`CreativeModeTab.builder`, the `separate_transforms` model loader). If the exact 26.2 build renames
a class or changes a constructor signature (e.g. `SwordItem`/attribute wiring), the change is
localised to the `item/` and `registry/` classes.

## License

MIT — see the mod metadata in `mods.toml`.
