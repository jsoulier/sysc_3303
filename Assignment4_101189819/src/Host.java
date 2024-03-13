import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The host application.
 */
public class Host extends UnicastRemoteObject implements Runnable, HostInterface {

    /** The client socket for sending/receiving. */
    private DatagramSocket clientSocket;

    /** The server socket for sending/receiving. */
    private DatagramSocket serverSocket;

    /** The datagram received from the client socket. */
    private DatagramPacket clientDatagram;

    /** The datagram received from the server socket. */
    private DatagramPacket serverDatagram;

    /**
     * Create a new host.
     * @throws RemoteException On RMI error.
     */
    public Host() throws RemoteException {
        super();
        try {
            clientSocket = new DatagramSocket();
            serverSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        clientDatagram = null;
        serverDatagram = null;
    }

    /**
     * Get the client port.
     * @returm The client port.
     * @throws RemoteException On RMI error.
     */
    @Override
    public int getClientPort() throws RemoteException {
        return clientSocket.getLocalPort();
    }

    /**
     * Get the server port.
     * @returm The server port.
     * @throws RemoteException On RMI error.
     */
    @Override
    public int getServerPort() throws RemoteException {
        return serverSocket.getLocalPort();
    }

    /**
     * Get the host address.
     * @return The host address.
     * @throws RemoteException On RMI error.
     */
    @Override
    public InetAddress getAddress() throws RemoteException {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Perform an RPC.
     * @param sendPacket The packet to send.
     * @return The packet received.
     * @throws RemoteException On RMI error.
     */
    @Override
    public Packet rpc(Packet sendPacket) throws RemoteException {
        DatagramSocket sendReceiveSocket;
        DatagramPacket receiveDatagram = new DatagramPacket(new byte[Config.PACKET_LENGTH], Config.PACKET_LENGTH);
        try {
            sendReceiveSocket = new DatagramSocket(0);
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RemoteException();
        }
        try {
            sendReceiveSocket.send(sendPacket.create());
            sendReceiveSocket.receive(receiveDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException();
        } finally {
            sendReceiveSocket.close();
        }
        return new Packet(receiveDatagram);
    }

    /**
     * Handle client RPC.
     */
    public void handleClient() {
        System.out.println("Receiving[Client]");
        clientDatagram = new DatagramPacket(new byte[Config.PACKET_LENGTH], Config.PACKET_LENGTH);
        try {
            clientSocket.receive(clientDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Packet clientPacket = new Packet(clientDatagram);
        System.out.println("Received[Client]: " + clientPacket);
        DatagramPacket sendDatagram;
        if (serverDatagram == null) {
            sendDatagram = new DatagramPacket(new byte[0], 0, clientDatagram.getAddress(), clientDatagram.getPort());
        } else {
            sendDatagram = new DatagramPacket(serverDatagram.getData(), serverDatagram.getLength(), clientDatagram.getAddress(), clientDatagram.getPort());
            serverDatagram = null;
        }
        System.out.println("Sending[Client]");
        try {
            clientSocket.send(sendDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent[Client]");
    }

    /**
     * Handle server RPC.
     */
    public void handleServer() {
        System.out.println("Receiving[Server]");
        serverDatagram = new DatagramPacket(new byte[Config.PACKET_LENGTH], Config.PACKET_LENGTH);
        try {
            serverSocket.receive(serverDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Packet serverPacket = new Packet(serverDatagram);
        System.out.println("Received[Server]: " + serverPacket);
        DatagramPacket sendDatagram;
        if (clientDatagram == null) {
            sendDatagram = new DatagramPacket(new byte[0], 0, serverDatagram.getAddress(), serverDatagram.getPort());
        } else {
            sendDatagram = new DatagramPacket(clientDatagram.getData(), clientDatagram.getLength(), serverDatagram.getAddress(), serverDatagram.getPort());
            clientDatagram = null;
        }
        System.out.println("Sending[Server]");
        try {
            serverSocket.send(sendDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent[Server]");
    }

    /**
     * The host thread entrypoint.
     */
    @Override
    public void run() {
        while (true) {
            handleClient();
            handleServer();
            handleServer();
            handleClient();
            System.out.println();
        }
    }

    /**
     * The host entrypoint.
     * @param args Unused.
     */
    public static void main(String[] args) {
        try {
            Host host = new Host();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("host", host);
            Thread thread = new Thread(host);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
