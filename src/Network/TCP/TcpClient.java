package Network.TCP;

import Network.Enums.SocketStates;
import Network.Exception.ClientSocketException;

import java.net.Socket;

public class TcpClient {
    private Socket m_socket;
    private SocketStates m_state;

    public TcpClient(Socket socket) {
        m_socket = socket;
        m_state = SocketStates.CONNECTED;
    }

    public TcpClient() {
        m_socket = new Socket();
        m_state = SocketStates.CLOSED;
    }

    public SocketStates GetState() {
        return m_state;
    }

    public void Connect(String ip, int port) throws ClientSocketException {
        if (m_state == SocketStates.CONNECTED) throw new ClientSocketException(SocketStates.CONNECTED);
        if (m_state == SocketStates.CLOSING) throw new ClientSocketException(SocketStates.CLOSING);
        if (m_state == SocketStates.CONNECTING) throw new ClientSocketException(SocketStates.CONNECTING);
    }
}
