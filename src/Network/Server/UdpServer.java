package Network.Server;

import Network.Client.NetworkPacket;
import Network.Enums.NetworkEventType;
import Network.Enums.NetworkProtocol;
import Network.Event.NetworkEvent;
import Network.Event.NetworkPacketEvent;
import Network.Event.NetworkStartEvent;
import Network.Thread.UpdListenerThread;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.function.Consumer;

public class UdpServer extends Server {
    private Consumer<NetworkPacketEvent> m_onNetworkPacketCb;
    private final DatagramSocket m_socket;
    private final int m_bufferSize;

    public UdpServer(int port) throws SocketException {
        super(port);

        m_socket = new DatagramSocket(port);
        m_bufferSize = 256;
        m_onNetworkPacketCb = null;
    }

    public UdpServer(int port, int bufferSize) throws SocketException {
        super(port);

        m_socket = new DatagramSocket(port);
        m_bufferSize = bufferSize;
        m_onNetworkPacketCb = null;
    }

    @Override
    public <T extends NetworkEvent> void AddEventListener(NetworkEventType type, Consumer<T> cb) {
        if (type == NetworkEventType.START_EVENT) m_onStartCb = (Consumer<NetworkStartEvent>)cb;
        if (type == NetworkEventType.START_EVENT) m_onNetworkPacketCb = (Consumer<NetworkPacketEvent>)cb;
    }

    @Override
    protected void StartListenerThread() {
        final UpdListenerThread listener = new UpdListenerThread(m_socket, m_bufferSize);
        final Thread thread = new Thread(listener);

        listener.OnError(this::OnThreadError);
        listener.OnData(this::OnThreadData);

        thread.start();
    }

    @Override
    protected void OnStart() {
        if (m_onStartCb == null) return;
        final NetworkStartEvent event = new NetworkStartEvent(NetworkProtocol.TCP, m_port);
        m_onStartCb.accept(event);
    }

    @Override
    protected void OnThreadError(IOException e) {
        System.out.println(e.toString());
    }

    private void OnThreadData(NetworkPacket packet) {
        if (m_onNetworkPacketCb != null) return;
        final NetworkPacketEvent event = new NetworkPacketEvent(packet);
        m_onNetworkPacketCb.accept(event);
    }
}
