package Network.Event;

import Network.Enums.NetworkEventType;

public class NetworkEvent {
    public final NetworkEventType type;

    public NetworkEvent(NetworkEventType type) {
        this.type = type;
    }
}
