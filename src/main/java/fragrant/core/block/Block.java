package fragrant.core.block;

import fragrant.core.util.data.Identifier;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

import java.util.function.BiPredicate;

public class Block {

	private final int id;
	private final Identifier name;
	private final int meta;
	private final MCVersion version;
	private final MCVersion implementedVersion;

	public Block(MCVersion version, int id, String name, MCVersion historic) {
		this(version, id, name, historic, 0);
	}

	public Block(MCVersion version, int id, String name, MCVersion historic, int meta) {
		this.version = version;
		this.id = id;
		this.name = new Identifier(name);
		this.implementedVersion = historic;
		this.meta = meta;
	}

	public MCVersion getImplementedVersion() {
		return implementedVersion;
	}

	public MCVersion getVersion() {
		return version;
	}

	public int getId() {
		return this.id;
	}

	public Identifier getIdentifier() {
		return this.name;
	}

	public String getRegistryName() {
		return this.name.toString();
	}

	public String getName() {
		return this.name.getPath();
	}

	public int getMeta() {
		return this.meta;
	}

	@Override
	public int hashCode() {
		return this.getRegistryName().hashCode() * 31 + this.meta;
	}

	@Override
	public String toString() {
		return this.getRegistryName();
	}

	public static final BiPredicate<MCVersion, Block> IS_AIR = (version, block) -> version.isNewerOrEqualTo(MCVersion.oldest()) && (block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR);
	public static final VersionMap<Block> RANDOM_TICKING = new VersionMap<Block>() {{
		add(MCVersion.v1_16_0, Blocks.GRASS_BLOCK);
		add(MCVersion.v1_16_0, Blocks.OAK_SAPLING);
		add(MCVersion.v1_16_0, Blocks.SPRUCE_SAPLING);
		add(MCVersion.v1_16_0, Blocks.BIRCH_SAPLING);
		add(MCVersion.v1_16_0, Blocks.JUNGLE_SAPLING);
		add(MCVersion.v1_16_0, Blocks.ACACIA_SAPLING);
		add(MCVersion.v1_16_0, Blocks.DARK_OAK_SAPLING);
		add(MCVersion.v1_16_0, Blocks.LAVA);
		add(MCVersion.v1_16_0, Blocks.BROWN_MUSHROOM);
		add(MCVersion.v1_16_0, Blocks.RED_MUSHROOM);
		add(MCVersion.v1_16_0, Blocks.WHEAT);
		add(MCVersion.v1_16_0, Blocks.FARMLAND);
		add(MCVersion.v1_16_0, Blocks.REDSTONE_ORE);
		add(MCVersion.v1_16_0, Blocks.SNOW);
		add(MCVersion.v1_16_0, Blocks.ICE);
		add(MCVersion.v1_16_0, Blocks.CACTUS);
		add(MCVersion.v1_16_0, Blocks.SUGAR_CANE);
		add(MCVersion.v1_16_0, Blocks.NETHER_PORTAL);
		add(MCVersion.v1_16_0, Blocks.PUMPKIN_STEM);
		add(MCVersion.v1_16_0, Blocks.MELON_STEM);
		add(MCVersion.v1_16_0, Blocks.VINE);
		add(MCVersion.v1_16_0, Blocks.MYCELIUM);
		add(MCVersion.v1_16_0, Blocks.NETHER_WART);
		add(MCVersion.v1_16_0, Blocks.COCOA);
		add(MCVersion.v1_16_0, Blocks.CARROTS);
		add(MCVersion.v1_16_0, Blocks.POTATOES);
		add(MCVersion.v1_16_0, Blocks.CHORUS_FLOWER);
		add(MCVersion.v1_16_0, Blocks.BEETROOTS);
		add(MCVersion.v1_16_0, Blocks.FROSTED_ICE);
		add(MCVersion.v1_16_0, Blocks.KELP);
		add(MCVersion.v1_16_0, Blocks.TURTLE_EGG);
		add(MCVersion.v1_16_0, Blocks.BAMBOO_SAPLING);
		add(MCVersion.v1_16_0, Blocks.BAMBOO);
		add(MCVersion.v1_16_0, Blocks.SWEET_BERRY_BUSH);
		add(MCVersion.v1_16_0, Blocks.WARPED_NYLIUM);
		add(MCVersion.v1_16_0, Blocks.CRIMSON_NYLIUM);
		add(MCVersion.v1_16_0, Blocks.WEEPING_VINES);
		add(MCVersion.v1_16_0, Blocks.TWISTING_VINES);
		add(MCVersion.v1_16_0, Blocks.MAGMA_BLOCK);
		add(MCVersion.v1_16_0, Blocks.OAK_LEAVES);
		add(MCVersion.v1_16_0, Blocks.SPRUCE_LEAVES);
		add(MCVersion.v1_16_0, Blocks.BIRCH_LEAVES);
		add(MCVersion.v1_16_0, Blocks.JUNGLE_LEAVES);
		add(MCVersion.v1_16_0, Blocks.ACACIA_LEAVES);
		add(MCVersion.v1_16_0, Blocks.DARK_OAK_LEAVES);
	}};
	public static final BiPredicate<MCVersion, Block> IS_RANDOM_TICKING = (version, block) -> RANDOM_TICKING.getMapUntil(version).containsKey(block);


}
