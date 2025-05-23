package fragrant.b2j.util;

import fragrant.b2j.structure.BedrockStructureType;
import fragrant.b2j.structure.end.EndCity;
import fragrant.b2j.structure.nether.Bastion;
import fragrant.b2j.structure.nether.Fortress;
import fragrant.b2j.structure.nether.NetherRuinedPortal;
import fragrant.b2j.structure.overworld.*;
import fragrant.b2j.util.position.StructurePos;

public class Formatter {

    public static String format(StructurePos pos) {
        return pos.defaultFormat();
    }

    public static String formatType(StructurePos pos, int structureType) {
        return switch (structureType) {
            case BedrockStructureType.ANCIENT_CITY       -> AncientCity.format(pos);
            case BedrockStructureType.DESERT_PYRAMID     -> DesertPyramid.format(pos);
            case BedrockStructureType.IGLOO              -> Igloo.format(pos);
            case BedrockStructureType.JUNGLE_TEMPLE      -> JungleTemple.format(pos);
            case BedrockStructureType.WOODLAND_MANSION   -> WoodlandMansion.format(pos);
            case BedrockStructureType.MINESHAFT          -> Mineshaft.format(pos);
            case BedrockStructureType.OCEAN_MONUMENT     -> OceanMonument.format(pos);
            case BedrockStructureType.OCEAN_RUINS        -> OceanRuins.format(pos);
            case BedrockStructureType.PILLAGER_OUTPOST   -> PillagerOutpost.format(pos);
            case BedrockStructureType.RUINED_PORTAL_O    -> OverworldRuinedPortal.format(pos);
            case BedrockStructureType.SHIPWRECK          -> Shipwreck.format(pos);
            case BedrockStructureType.SWAMP_HUT          -> SwampHut.format(pos);
            case BedrockStructureType.TRAIL_RUINS        -> TrailRuins.format(pos);
            case BedrockStructureType.BURIED_TREASURE    -> BuriedTreasure.format(pos);
            case BedrockStructureType.TRIAL_CHAMBERS     -> TrialChambers.format(pos);
            case BedrockStructureType.VILLAGE            -> Village.format(pos);
            case BedrockStructureType.BASTION_REMNANT    -> Bastion.format(pos);
            case BedrockStructureType.NETHER_FORTRESS    -> Fortress.format(pos);
            case BedrockStructureType.RUINED_PORTAL_N    -> NetherRuinedPortal.format(pos);
            case BedrockStructureType.END_CITY           -> EndCity.format(pos);
            case BedrockStructureType.DESERT_WELL        -> DesertWell.format(pos);
            case BedrockStructureType.FOSSIL_O           -> OverworldFossil.format(pos);
            case BedrockStructureType.AMETHYST_GEODE     -> AmethystGeode.format(pos);
            case BedrockStructureType.RAVINE             -> Ravine.format(pos);
            case BedrockStructureType.VILLAGE_STRONGHOLD -> Stronghold.format(pos);
            case BedrockStructureType.STATIC_STRONGHOLD  -> Stronghold.format(pos);
            default -> format(pos);
        };
    }

}
