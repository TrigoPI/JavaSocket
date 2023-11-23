import Network.Enums.NetworkEventType;
import Network.Event.NetworkConnectEvent;
import Network.Event.NetworkPacketEvent;
import Network.Event.NetworkStartEvent;
import Network.Server.TcpServer;
import Network.Server.UdpServer;

import java.io.IOException;

public class Main {
    public void OnStart(NetworkStartEvent e) {
        System.out.println("Started " + e.protocol + " on port : " + e.port);
    }

    public void OnConnect(NetworkConnectEvent e) {
        System.out.println("NEW CONNECTION");
    }

    public void OnNetworkPacket(NetworkPacketEvent e) {
        System.out.println(e.packet.data);
    }

    public static void main(String[] args) throws IOException {
        final Main app = new Main();

        final TcpServer tcpServer = new TcpServer(5000);
        final UdpServer udpServer = new UdpServer(4000);

        tcpServer.AddEventListener(NetworkEventType.CONNECT_EVENT, app::OnConnect);
        tcpServer.AddEventListener(NetworkEventType.START_EVENT, app::OnStart);

        udpServer.AddEventListener(NetworkEventType.START_EVENT, app::OnStart);
        udpServer.AddEventListener(NetworkEventType.NETWORK_PACKET_EVENT, app::OnNetworkPacket);

        tcpServer.Listen();
        udpServer.Listen();
    }
}
