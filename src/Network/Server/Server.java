package Network.Server;

import Network.Enums.NetworkEventType;
import Network.Event.NetworkEvent;
import Network.Event.NetworkStartEvent;

import java.io.IOException;
import java.util.function.Consumer;

public abstract class Server {
    protected Consumer<NetworkStartEvent> m_onStartCb;
    protected final int m_port;

    protected Server(int port) {
        m_port = port;
        m_onStartCb = null;
    }

    public int GetListeningPort() {
        return m_port;
    }

    public void Listen() {
        StartListenerThread();
        OnStart();
    }

    public abstract <T extends NetworkEvent> void AddEventListener(NetworkEventType type, Consumer<T> cb);
    protected abstract void StartListenerThread();
    protected abstract void OnStart();
    protected abstract void OnThreadError(IOException e);
}
