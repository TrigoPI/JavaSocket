package Network.Exception;

import Network.Enums.SocketStates;

public class ClientSocketException extends Exception {
    public final SocketStates state;

    public ClientSocketException(SocketStates state) {
        super();
        this.state = state;
    }
}
