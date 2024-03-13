import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HostInterface extends Remote {
    public int getClientPort() throws RemoteException;
    public int getServerPort() throws RemoteException;
    public InetAddress getAddress() throws RemoteException;
    public Packet rpc(Packet sendPacket) throws RemoteException;
}
