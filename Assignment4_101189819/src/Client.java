import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client implements Runnable {

    private HostInterface host;

    public Client(HostInterface host) {
        this.host = host;
    }

    public void send(Request request, String fileName, String mode) {
        try {
            Packet sendPacket = new Packet(request, fileName, mode, host.getAddress(), host.getClientPort());
            System.out.println("Sending: " + sendPacket);
            host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent");
    }

    public void receive() {
        Packet receivePacket;
        System.out.println("Receiving");
        try {
            Packet sendPacket = new Packet(host.getAddress(), host.getClientPort());
            receivePacket = host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Received: " + receivePacket);
    }

    @Override
    public void run() {
        send(Request.READ, "file1.txt", "netascii");
        receive();
    }

    public static void main(String[] args) {
        try {
            HostInterface host = (HostInterface) Naming.lookup("rmi://localhost/host");
            Client client = new Client(host);
            Thread thread = new Thread(client);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
