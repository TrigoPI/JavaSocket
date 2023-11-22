package Network.Thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.function.Consumer;

public class TcpConnectThread implements Runnable {
    private Consumer<Integer> m_onConnectionFailedCb;
    private final Socket m_socket;
    private final String m_ip;
    private final int m_port;

    public TcpConnectThread(Socket socket, String ip, int port) {
        m_socket = socket;
        m_ip = ip;
        m_port = port;
        m_onConnectionFailedCb = null;
    }

    @Override
    public void run() {
        Connect();
    }

    private void Connect() {
        try {
            InetSocketAddress addr = new InetSocketAddress(m_ip, m_port);
            m_socket.connect(addr);
        } catch (IOException e) {
            if (m_onConnectionFailedCb == null) return;
            m_onConnectionFailedCb.accept(-1);
        }
    }
}
