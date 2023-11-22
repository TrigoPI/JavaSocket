package Network.Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TcpListenerThread implements Runnable {
    private final ServerSocket m_socket;

    private Consumer<Socket> m_onConnectCb;
    private Consumer<IOException> m_onErrorCb;

    public TcpListenerThread(ServerSocket socket) {
        m_socket = socket;
        m_onConnectCb = null;
    }

    public void onConnect(Consumer<Socket> cb) {
        m_onConnectCb = cb;
    }

    public void onError(Consumer<IOException> cb) {
        m_onErrorCb = cb;
    }

    @Override
    public void run() {
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
