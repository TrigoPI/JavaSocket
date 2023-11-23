package Network.Server;

import Network.Enums.NetworkEventType;
import Network.Enums.NetworkProtocol;
import Network.Event.NetworkConnectEvent;
import Network.Event.NetworkEvent;
import Network.Event.NetworkStartEvent;
import Network.Thread.TcpListenerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TcpServer extends Server {
    private Consumer<NetworkStartEvent> m_onStartCb;
    private Consumer<NetworkConnectEvent> m_onConnectCb;

    private final ServerSocket m_socket;

    public TcpServer(int port) throws IOException {
        super(port);

        m_socket = new ServerSocket(port);
        m_onStartCb = null;
        m_onConnectCb = null;
    }

    public <T extends NetworkEvent>void AddEventListener(NetworkEventType type, Consumer<T> cb) {
        if (type == NetworkEventType.START_EVENT) m_onStartCb = (Consumer<NetworkStartEvent>)cb;
        if (type == NetworkEventType.CONNECT_EVENT) m_onConnectCb = (Consumer<NetworkConnectEvent>)cb;
    }

    public void Listen() {
        StartListenerThread();
        OnStart();
    }

    protected void StartListenerThread() {
        final TcpListenerThread listener = new TcpListenerThread(m_socket);
        final Thread thread = new Thread(listener);

        listener.OnConnect(this::OnThreadConnect);
        listener.OnError(this::OnThreadError);

        thread.start();
    }

    protected void OnStart() {
        if (m_onStartCb == null) return;
        final NetworkStartEvent event = new NetworkStartEvent(NetworkProtocol.TCP, m_port);
        m_onStartCb.accept(event);
    }

    private void OnThreadConnect(Socket socket) {
        if (m_onConnectCb == null) return;
        final NetworkConnectEvent event = new NetworkConnectEvent();
        m_onConnectCb.accept(event);
    }

    protected void OnThreadError(IOException e) {
        System.out.println(e.toString());
    }
}
