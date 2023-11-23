package Network.Thread;

import Network.Client.NetworkPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.function.Consumer;

public class UpdListenerThread extends ListenerThread {
    private Consumer<NetworkPacket> m_onDataCb;
    private final DatagramSocket m_socket;
    private final int m_bufferSize;

    public UpdListenerThread(DatagramSocket socket, int bufferSize)  {
        m_socket = socket;
        m_bufferSize = bufferSize;
    }

    public void OnData(Consumer<NetworkPacket> cb) {
        m_onDataCb = cb;
    }

    public void StartThread() {
        final byte[] buf = new byte[m_bufferSize];

        while (true) {
            try {
                final DatagramPacket packet = new DatagramPacket(buf, m_bufferSize);
                m_socket.receive(packet);

                final NetworkPacket netPacket = new NetworkPacket(packet.toString());

                if (m_onDataCb != null) m_onDataCb.accept(netPacket);
            } catch (IOException e) {
                if (m_onErrorCb != null) m_onErrorCb.accept(e);
            }
        }
    }
}