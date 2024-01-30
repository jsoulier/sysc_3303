import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 */
public class Server {

    /** TODO */
    private DatagramSocket socket;

    /** TODO */
    private DatagramPacket datagram;

    /** TODO */
    private ClientPacket packet;

    /**
     * TODO
     */
    public Server() {
        try {
            socket = new DatagramSocket(Config.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * TODO
     */
    public void send() {

        // Create a new socket for the response
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        byte[] bytes = packet.getRequest().getResponse();
        datagram = new DatagramPacket(bytes, bytes.length, datagram.getAddress(), datagram.getPort());
        System.out.print("Sending: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print("Data(String)=");
        System.out.print(StringHelper.toString(datagram.getData()));
        System.out.println();
        try {
            socket.send(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent");
        socket.close();
    }

    /**
     * TODO
     * @return
     */
    public boolean receive() {
        System.out.println("Receiving");
        byte[] bytes = new byte[Config.PACKET_LENGTH];
        datagram = new DatagramPacket(bytes, bytes.length);
        try {
            socket.receive(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        packet = ClientPacket.create(datagram);
        System.out.print("Received: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();
        return packet.getRequest() != Request.INVALID;
    }

    /**
     * TODO
     */
    public void close() {
        socket.close();
    }

    /**
     * TODO
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        while (server.receive()) {
            server.send();

        }
        server.close();
        throw new RuntimeException();
    }
}
