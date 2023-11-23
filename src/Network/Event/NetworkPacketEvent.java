package Network.Event;

import Network.Client.NetworkPacket;
import Network.Enums.NetworkEventType;

public class NetworkPacketEvent extends NetworkEvent {
    public final NetworkPacket packet;
    public NetworkPacketEvent(NetworkPacket packet) {
        super(NetworkEventType.NETWORK_PACKET_EVENT);
        this.packet = packet;
    }
}
