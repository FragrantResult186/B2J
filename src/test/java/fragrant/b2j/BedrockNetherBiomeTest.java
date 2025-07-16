package fragrant.b2j;

import fragrant.b2j.terrain.nether.NetherUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BedrockNetherBiomeTest {

    @org.junit.jupiter.api.Test
    public void testBiome() {
        assertEquals("basalt_deltas", NetherUtil.getBiomeName(1234567890L, -96, 1040));
    }

}
