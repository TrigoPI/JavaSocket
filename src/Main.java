import Network.Enums.NetworkEventType;
import Network.Event.NetworkConnectEvent;
import Network.Event.NetworkStartEvent;
import Network.TCP.TcpServer;

import java.io.IOException;

public class Main {
    public void OnStart(NetworkStartEvent e) {
        System.out.println("Started " + e.protocol + " on port : " + e.port);
    }

    public void OnConnect(NetworkConnectEvent e) {
        System.out.println("NEW CONNECTION");
    }

    public static void main(String[] args) throws IOException {
        final Main app = new Main();
        final TcpServer server = new TcpServer(5000);

        server.AddEventListener(NetworkEventType.CONNECT_EVENT, app::OnConnect);
        server.AddEventListener(NetworkEventType.START_EVENT, app::OnStart);

        server.Listen();
    }
}
