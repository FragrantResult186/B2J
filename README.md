# B2J

B2J is a Java library for Bedrock Minecraft, inspired by [Chunkbase](https://www.chunkbase.com/).

### Currently Supported
#### Structures
- Ancient City
- Bastion Remnant
- Buried Treasure
- Desert Pyramid
- End City
- Igloo
- Jungle Temple
- Mineshaft
- Nether Fortress
- Ocean Monument
- Ocean Ruins
- Pillager Outpost
- Ruined Portal (overworld & nether)
- Shipwreck
- Stronghold (village & static)
- Swamp Hut
- Trail Ruins
- Trial Chamber
- Village
- Woodland Mansion
#### Features
- Amethyst Geode
- Desert Well
- Fossil (overworld & nether)
- Pumpkin
- Ravine
- Sweet Berry
#### Others
- Slime Chunk
---
### Details
- Ancient City: Implements initial rotation and supports three types of center configurations.
- Bastion Remnant: Implements initial rotation and distinguishes between the four bastion types.
- Buried Treasure: Includes a loot generator.
- Desert Pyramid: Includes a loot generator.
- End City: Implements initial rotation, the full city layout generator, and a loot generator for the ship chest.
- Igloo: Implements initial rotation, detection of basement presence, ladder length, and a loot generator.
- Mineshaft: Supports both generation methods: versions 1.1–1.10 and 1.11+.
- Ocean Ruins: Distinguishes between large and small variants, clustered vs. standalone generation, and counts the number of small ruins.
- Pillager Outpost: Implements initial rotation and a loot generator.
- Ruined Portal: Implements initial rotation, and supports giant, underground, variant, and mirror flags.
- Stronghold: Supports both types—village-based (three preset positions) and static (infinite generation). Includes layout generation and portal room eye count logic. (Note: behavior may be unreliable near chunk borders.)
- Trial Chamber: Implements initial rotation.
- Village: Implements initial rotation and meeting point type detection (currently supports plains only).
- Woodland Mansion: Implements initial rotation.
- Pumpkin / Sweet Berry: Only checks whether a chunk can generate them; does not determine the actual count.
- Fossil: May fail to generate depending on terrain (especially in the Nether).
 - Ravine: Supports both pre- and post-1.21.60 versions, and implements initial rotation and giant variant detection.

