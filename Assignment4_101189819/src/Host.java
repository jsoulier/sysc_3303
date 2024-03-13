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

public class Host extends UnicastRemoteObject implements Runnable, HostInterface {

    private DatagramSocket clientSocket;
    private DatagramSocket serverSocket;
    private DatagramPacket clientDatagram;
    private DatagramPacket serverDatagram;

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

    @Override
    public int getClientPort() throws RemoteException {
        return clientSocket.getLocalPort();
    }

    @Override
    public int getServerPort() throws RemoteException {
        return serverSocket.getLocalPort();
    }

    @Override
    public InetAddress getAddress() throws RemoteException {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Packet rpc(Packet sendPacket) throws RemoteException {
        System.out.println("Received[RPC]: " + sendPacket);
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

    @Override
    public void run() {
        while (true) {
            handleClient();
            handleServer();
            handleServer();
            handleClient();
        }
    }

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
