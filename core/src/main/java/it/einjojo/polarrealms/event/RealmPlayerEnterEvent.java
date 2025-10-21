package it.einjojo.polarrealms.event;

import com.google.common.io.ByteStreams;
import it.einjojo.polarrealms.event.dispatch.NetworkEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NetworkEvent(id = 0)
public class RealmPlayerEnterEvent extends Event {
    private final UUID playerId;
    private final UUID realmId;

    public static RealmPlayerEnterEvent deserialize(byte[] bytes) {
        var in = ByteStreams.newDataInput(bytes);
        return new RealmPlayerEnterEvent(
                new UUID(in.readLong(), in.readLong()),
                new UUID(in.readLong(), in.readLong())
        );
    }

    @Override
    public byte[] createPayload() {
        var out = ByteStreams.newDataOutput();
        out.writeLong(playerId.getMostSignificantBits());
        out.writeLong(playerId.getLeastSignificantBits());
        out.writeLong(realmId.getMostSignificantBits());
        out.writeLong(realmId.getLeastSignificantBits());
        return out.toByteArray();
    }
}
