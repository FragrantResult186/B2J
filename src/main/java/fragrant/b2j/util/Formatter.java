package fragrant.b2j.util;

import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.worldfeature.carver.overworld.Ravine;
import fragrant.b2j.worldfeature.feature.nether.NetherFossil;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.worldfeature.feature.end.EndIsland;
import fragrant.b2j.worldfeature.feature.overworld.surface.DesertWell;
import fragrant.b2j.worldfeature.feature.overworld.surface.LavaLake;
import fragrant.b2j.worldfeature.feature.overworld.underground.AmethystGeode;
import fragrant.b2j.worldfeature.feature.overworld.underground.OverworldFossil;
import fragrant.b2j.worldfeature.feature.overworld.vegetation.Pumpkin;
import fragrant.b2j.worldfeature.feature.overworld.vegetation.SweetBerry;
import fragrant.b2j.worldfeature.structure.RuinedPortal;
import fragrant.b2j.worldfeature.structure.end.EndCity;
import fragrant.b2j.worldfeature.structure.nether.Bastion;
import fragrant.b2j.worldfeature.structure.nether.Fortress;
import fragrant.b2j.worldfeature.structure.overworld.surface.*;
import fragrant.b2j.worldfeature.structure.overworld.underground.*;
import fragrant.b2j.worldfeature.structure.overworld.underwater.OceanMonument;
import fragrant.b2j.worldfeature.structure.overworld.underwater.OceanRuins;
import fragrant.b2j.worldfeature.structure.overworld.underwater.Shipwreck;

public class Formatter {

    public static String format(FeaturePos pos) {
        return pos.defaultFormat();
    }

    public static String formatType(FeaturePos pos, int structureType) {
        return switch (structureType) {
            // Overworld structures
            case BedrockFeatureType.ANCIENT_CITY       -> AncientCity.format(pos);
            case BedrockFeatureType.BURIED_TREASURE    -> BuriedTreasure.format(pos);
            case BedrockFeatureType.DESERT_PYRAMID     -> DesertPyramid.format(pos);
            case BedrockFeatureType.IGLOO              -> Igloo.format(pos);
            case BedrockFeatureType.JUNGLE_TEMPLE      -> JungleTemple.format(pos);
            case BedrockFeatureType.MINESHAFT          -> Mineshaft.format(pos);
            case BedrockFeatureType.OCEAN_MONUMENT     -> OceanMonument.format(pos);
            case BedrockFeatureType.OCEAN_RUINS        -> OceanRuins.format(pos);
            case BedrockFeatureType.PILLAGER_OUTPOST   -> PillagerOutpost.format(pos);
            case BedrockFeatureType.RUINED_PORTAL_O    -> RuinedPortal.format(pos);
            case BedrockFeatureType.SHIPWRECK          -> Shipwreck.format(pos);
            case BedrockFeatureType.STATIC_STRONGHOLD  -> Stronghold.format(pos);
            case BedrockFeatureType.SWAMP_HUT          -> SwampHut.format(pos);
            case BedrockFeatureType.TRAIL_RUINS        -> TrailRuins.format(pos);
            case BedrockFeatureType.TRIAL_CHAMBERS     -> TrialChambers.format(pos);
            case BedrockFeatureType.VILLAGE            -> Village.format(pos);
            case BedrockFeatureType.VILLAGE_STRONGHOLD -> Stronghold.format(pos);
            case BedrockFeatureType.WOODLAND_MANSION   -> WoodlandMansion.format(pos);

            // Nether structures
            case BedrockFeatureType.BASTION_REMNANT    -> Bastion.format(pos);
            case BedrockFeatureType.NETHER_FORTRESS    -> Fortress.format(pos);
            case BedrockFeatureType.RUINED_PORTAL_N    -> RuinedPortal.format(pos);

            // End structures
            case BedrockFeatureType.END_CITY           -> EndCity.format(pos);

            // Features
            case BedrockFeatureType.AMETHYST_GEODE     -> AmethystGeode.format(pos);
            case BedrockFeatureType.DESERT_WELL        -> DesertWell.format(pos);
            case BedrockFeatureType.END_ISLAND         -> EndIsland.format(pos);
            case BedrockFeatureType.FOSSIL_N           -> NetherFossil.format(pos);
            case BedrockFeatureType.FOSSIL_O           -> OverworldFossil.format(pos);
            case BedrockFeatureType.LAVA_LAKE          -> LavaLake.format(pos);
            case BedrockFeatureType.PUMPKIN            -> Pumpkin.format(pos);
            case BedrockFeatureType.RAVINE             -> Ravine.format(pos);
            case BedrockFeatureType.SWEET_BERRY        -> SweetBerry.format(pos);

            default -> format(pos);
        };
    }

}
