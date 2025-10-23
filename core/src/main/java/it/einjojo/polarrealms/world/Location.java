package it.einjojo.polarrealms.world;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * <p>A generic location when realmId is null.</p>
 * <p>Used for spawn locations or corners</p>
 *
 */
@Getter
@Setter
public final class Location implements Cloneable {
    private final float x;
    private final float y;
    private final float z;
    private final float yaw;
    private final float pitch;
    private @Nullable UUID realmId;

    /**
     * @param x       x
     * @param y       y
     * @param z       z
     * @param yaw     yaw
     * @param pitch   pitch
     * @param realmId optional id of the realm
     */
    public Location(float x, float y, float z, float yaw, float pitch, @Nullable UUID realmId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.realmId = realmId;
    }


    /**
     * Rounds location coordinates to integers.
     *
     * @return a new BlockLocation with rounded coordinates.
     */
    public BlockLocation toBlock() {
        return new BlockLocation(Math.round(x), Math.round(y), Math.round(z), realmId);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public @Nullable UUID realmId() {
        return realmId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Location) obj;
        return Float.floatToIntBits(this.x) == Float.floatToIntBits(that.x) &&
                Float.floatToIntBits(this.y) == Float.floatToIntBits(that.y) &&
                Float.floatToIntBits(this.z) == Float.floatToIntBits(that.z) &&
                Float.floatToIntBits(this.yaw) == Float.floatToIntBits(that.yaw) &&
                Float.floatToIntBits(this.pitch) == Float.floatToIntBits(that.pitch) &&
                Objects.equals(this.realmId, that.realmId);
    }

    @Override
    protected Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Location(x, y, z, yaw, pitch, realmId);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, yaw, pitch, realmId);
    }

    @Override
    public String toString() {
        return "Location[" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "z=" + z + ", " +
                "yaw=" + yaw + ", " +
                "pitch=" + pitch + ", " +
                "realmId=" + realmId + ']';
    }


}
