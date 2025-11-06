package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;

public class EndPortalFrameData {
    private final BPos position;
    private final int frameId;
    private final boolean hasEye;

    public EndPortalFrameData(BPos position, int frameId, boolean hasEye) {
        this.position = position;
        this.frameId = frameId;
        this.hasEye = hasEye;
    }

    public BPos getPosition() {
        return position;
    }

    public CPos getChunkPos() {
        return position.toChunkPos();
    }

    public int getFrameId() {
        return frameId;
    }

    public boolean hasEye() {
        return hasEye;
    }

    @Override
    public String toString() {
        return String.format("frame{id=%d, pos=%s, hasEye=%b}", frameId, position, hasEye);
    }
}