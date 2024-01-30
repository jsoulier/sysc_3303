import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * TODO
 */
public class Host {

    /** TODO */
    private DatagramSocket receiveSocket;

    /** TODO */
    private DatagramSocket sendReceiveSocket;

    /** TODO */
    private DatagramPacket clientDatagram;

    /** TODO */
    private DatagramPacket serverDatagram;

    /** TODO */
    private ClientPacket packet;

    /**
     * 
     */
    public Host() {
        try {
            receiveSocket = new DatagramSocket(Config.HOST_PORT);
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * TODO
     */
    public void sendClient() {

    }

    /**
     * TODO
     */
    public void sendServer() {
        serverDatagram = ClientPacket.create(packet, Config.SERVER_IP, Config.SERVER_PORT);
        System.out.print("Sending(Server): ");
        System.out.print(StringHelper.toString(serverDatagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();
        try {
            sendReceiveSocket.send(serverDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent(Server)");
    }

    /**
     * TODO
     */
    public void receiveClient() {
        System.out.println("Receiving(Client)");
        byte[] bytes = new byte[Config.PACKET_LENGTH];
        clientDatagram = new DatagramPacket(bytes, bytes.length);
        try {
            receiveSocket.receive(clientDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        packet = ClientPacket.create(clientDatagram);
        System.out.print("Received(Client): ");
        System.out.print(StringHelper.toString(clientDatagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();
    }

    /**
     * TODO
     */
    public void receiveServer() {

    }

    /**
     * TODO
     * @param args
     */
    public static void main(String[] args) {

        Host host = new Host();

        host.receiveClient();
        host.sendServer();
        host.receiveServer();
        host.sendClient();
    }
}
