package fragrant.feature.structure.generator.piece.stronghold;

import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.ChunkRngTracker;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.math.Vec3i;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;

import java.util.ArrayList;
import java.util.List;

public class PortalRoom extends Stronghold.Piece {

    private List<EndPortalFrameData> frames = null;

    public PortalRoom(int pieceId, BlockBox boundingBox, BlockDirection facing) {
        super(pieceId);
        this.setOrientation(facing);
        this.boundingBox = boundingBox;
    }

    @Override
    public void addChildren(StrongholdGenerator gen, Start start, List<Stronghold.Piece> pieces, ChunkRand rand) {
        if(start != null) {
            start.portalRoom = this;
        }
        generateEyeStates(gen.worldSeed, gen.chunkRngTracker, gen.version);
    }

    public static PortalRoom createPiece(List<Stronghold.Piece> pieces, int x, int y, int z, BlockDirection facing, int pieceId) {
        BlockBox box = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 8, 16, facing.getRotation());
        return Stronghold.Piece.isHighEnough(box) && Stronghold.Piece.getNextIntersectingPiece(pieces, box) == null ? new PortalRoom(pieceId, box, facing) : null;
    }

    public BlockBox getEndFrameBB() {
        Vec3i mins = this.applyVecTransform(new Vec3i(3,3,8));
        Vec3i maxes = this.applyVecTransform(new Vec3i(7,3,12));
        return new BlockBox(mins,maxes);
    }

    private List<BPos> calculateFramePositions() {
        List<BPos> positions = new ArrayList<>(12);

        switch (this.getFacing()) {
            case NORTH:
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
            case SOUTH:
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
            case WEST:
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                break;
            case EAST:
                positions.add(transformPosition(4, 8));
                positions.add(transformPosition(5, 8));
                positions.add(transformPosition(6, 8));
                positions.add(transformPosition(4, 12));
                positions.add(transformPosition(5, 12));
                positions.add(transformPosition(6, 12));
                positions.add(transformPosition(3, 9));
                positions.add(transformPosition(3, 10));
                positions.add(transformPosition(3, 11));
                positions.add(transformPosition(7, 9));
                positions.add(transformPosition(7, 10));
                positions.add(transformPosition(7, 11));
                break;
        }

        return positions;
    }

    private BPos transformPosition(int x, int z) {
        int worldX = this.applyXTransform(x, z);
        int worldY = this.applyYTransform(3);
        int worldZ = this.applyZTransform(x, z);
        return new BPos(worldX, worldY, worldZ);
    }

    private void generateEyeStates(long worldSeed, ChunkRngTracker tracker, MCVersion version) {
        if (frames != null) return;
        frames = new ArrayList<>(12);
        ChunkRand rand = new ChunkRand();

        List<BPos> framePositions = calculateFramePositions();

        for (int frameId = 0; frameId < 12; frameId++) {
            BPos framePos = framePositions.get(frameId);
            CPos chunkPos = framePos.toChunkPos();

            int rngCount = tracker.getRngCount(chunkPos);

            rand.setPopulationSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), version);
            rand.skip(rngCount + frameId);

            boolean hasEye = rand.nextFloat() > 0.9F;
            frames.add(new EndPortalFrameData(framePos, frameId, hasEye));
        }
    }

    public void updateFramePositions() {
        if (frames == null) return;

        List<BPos> newPositions = calculateFramePositions();

        for (int i = 0; i < frames.size() && i < newPositions.size(); i++) {
            EndPortalFrameData oldFrame = frames.get(i);
            BPos newPos = newPositions.get(i);
            frames.set(i, new EndPortalFrameData(newPos, oldFrame.getFrameId(), oldFrame.hasEye()));
        }
    }

    public List<EndPortalFrameData> getFrames() {
        return frames;
    }

    public int getEyeCount() {
        if (frames == null) return 0;
        return (int) frames.stream().filter(EndPortalFrameData::hasEye).count();
    }
}