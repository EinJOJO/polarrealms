package it.einjojo.polarrealms.world;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Corner markings.
 *
 */
@Getter
@Setter
public final class BlockLocation implements Cloneable {
    private int x;
    private int y;
    private int z;
    private @Nullable UUID realmId;

    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     *
     */
    public BlockLocation(int x, int y, int z, @Nullable UUID realmId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.realmId = realmId;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    public @Nullable UUID realmId() {
        return realmId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BlockLocation) obj;
        return this.x == that.x &&
                this.y == that.y &&
                this.z == that.z &&
                Objects.equals(this.realmId, that.realmId);
    }

    @Override
    protected BlockLocation clone() {
        try {
            return (BlockLocation) super.clone();
        } catch (CloneNotSupportedException e) {
            return new BlockLocation(x, y, z, realmId);
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, realmId);
    }

    @Override
    public String toString() {
        return "BlockLocation[" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "z=" + z + ", " +
                "realmId=" + realmId + ']';
    }


}
