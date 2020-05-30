
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.util.math.Vec3d;

class TestModUtils
{
    @Test
    void testLineCallback()
    {
	Vec3d[] expected = { new Vec3d(0, 0, 0), new Vec3d(0, 1.5, 0), new Vec3d(0, 3, 0) };
	Vec3d[] actual = new Vec3d[3];
	ModUtils.lineCallback(new Vec3d(0, 0, 0), new Vec3d(0, 3, 0), 3, (vec, i) -> {
	    actual[i] = vec;
	});
	assertArrayEquals(expected, actual);
    }
}
