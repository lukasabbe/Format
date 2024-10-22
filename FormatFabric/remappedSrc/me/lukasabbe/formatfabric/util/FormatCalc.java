package me.lukasabbe.formatfabric.util;

import me.lukasabbe.formatfabric.objects.BoxValue;

public class FormatCalc {
    public static String getStringNbt(BoxValue value) {
        int x = value.getX();
        int y = value.getY();
        int z = value.getZ();
        int dz = value.getDz();
        int dx = value.getDx();
        int dy = value.getDy();
        int tempDx = x - dx;
        int tempDy = y - dy;
        int tempDz = z - dz;
        return "[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + tempDx * -1 + ",dy=" + tempDy * -1 + ",dz=" + tempDz * -1 + "]";
    }
    public static String getStringJson(BoxValue value) {
        int x = value.getX();
        int y = value.getY();
        int z = value.getZ();
        int dz = value.getDz();
        int dx = value.getDx();
        int dy = value.getDy();
        int tempDx = x - dx;
        int tempDy = y - dy;
        int tempDz = z - dz;
        return "\"position\":{\"x\": {\"min\":"+Math.min(x, tempDx)+",\"max\":"+Math.max(x, tempDx)+"},\"y\":{\"min\":"+Math.min(y, tempDy)+",\"max\":"+Math.max(y, tempDy)+"},\"z\":{\"min\":"+Math.min(z, tempDz)+",\"max\":"+Math.max(z, tempDz)+"}}";
    }
}
