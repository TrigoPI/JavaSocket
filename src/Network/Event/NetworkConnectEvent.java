package Network.Event;

import Network.Enums.NetworkEventType;

public class NetworkConnectEvent extends NetworkEvent {
    public NetworkConnectEvent() {
        super(NetworkEventType.CONNECT_EVENT);
    }
}
