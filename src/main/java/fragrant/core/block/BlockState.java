package fragrant.core.block;

public class BlockState {

	private final Block block;

	public BlockState(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return this.block;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof BlockState)) return false;
		BlockState that = (BlockState) other;
		return this.getBlock() == that.getBlock();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getBlock().getName().toUpperCase());
		return sb.toString();
	}

}
