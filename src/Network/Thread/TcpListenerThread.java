package Network.Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TcpListenerThread extends ListenerThread {
    private Consumer<Socket> m_onConnectCb;

    private final ServerSocket m_socket;

    public TcpListenerThread(ServerSocket socket) {
        m_socket = socket;
        m_onConnectCb = null;
    }

    public void OnConnect(Consumer<Socket> cb) {
        m_onConnectCb = cb;
    }

    public void StartThread() {
        while (true) {
            try {
                Socket socket = m_socket.accept();
                if (m_onConnectCb != null) m_onConnectCb.accept(socket);
            } catch (IOException e) {
                if (m_onErrorCb != null) m_onErrorCb.accept(e);
            }
        }
    }
}
