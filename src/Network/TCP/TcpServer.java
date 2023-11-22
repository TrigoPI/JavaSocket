package Network.TCP;

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

public class TcpServer {
    private Consumer<NetworkStartEvent> m_onStartCb;
    private Consumer<NetworkConnectEvent> m_onConnectCb;

    private final ServerSocket m_socket;
    private final int m_port;

    public TcpServer(int port) throws IOException {
        m_socket = new ServerSocket(port);
        m_port = port;
        m_onStartCb = null;
        m_onConnectCb = null;
    }

    public int GetListeningPort() {
        return m_port;
    }

    public <T extends NetworkEvent>void AddEventListener(NetworkEventType type, Consumer<T> cb) {
        if (type == NetworkEventType.START_EVENT) m_onStartCb = (Consumer<NetworkStartEvent>)cb;
        if (type == NetworkEventType.CONNECT_EVENT) m_onConnectCb = (Consumer<NetworkConnectEvent>)cb;
    }

    public void Listen() {
        StartListenerThread();
        OnStart();
    }

    private void StartListenerThread() {
        final TcpListenerThread listener = new TcpListenerThread(m_socket);
        final Thread thread = new Thread(listener);

        listener.onConnect(this::OnThreadConnect);
        listener.onError(this::OnThreadError);

        thread.start();
    }

    public void OnStart() {
        if (m_onStartCb == null) return;
        final NetworkStartEvent event = new NetworkStartEvent(NetworkProtocol.TCP, m_port);
        m_onStartCb.accept(event);
    }

    private void OnThreadConnect(Socket socket) {
        if (m_onConnectCb == null) return;
        final NetworkConnectEvent event = new NetworkConnectEvent();
        m_onConnectCb.accept(event);
    }

    private void OnThreadError(IOException e) {
        System.out.println(e.toString());
    }
}
