package Network.Event;

import Network.Enums.NetworkEventType;
import Network.Enums.NetworkProtocol;

public class NetworkStartEvent extends NetworkEvent {
    public final NetworkProtocol protocol;
    public final int port;

    public NetworkStartEvent(NetworkProtocol protocol, int port) {
        super(NetworkEventType.START_EVENT);
        this.protocol = protocol;
        this.port = port;
    }
}
