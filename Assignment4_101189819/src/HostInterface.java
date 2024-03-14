import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface for the client and server.
 */
public interface HostInterface extends Remote {

    /**
     * Get the client port.
     * @return The client port.
     * @throws RemoteException On RMI error.
     */
    public int getClientPort() throws RemoteException;

    /**
     * Get the server port.
     * @return The server port.
     * @throws RemoteException On RMI error.
     */
    public int getServerPort() throws RemoteException;

    /**
     * Get the host address.
     * @return The host address.
     * @throws RemoteException On RMI error.
     */
    public InetAddress getAddress() throws RemoteException;

    /**
     * Perform an RPC.
     * @param sendPacket The packet to send.
     * @return The packet received.
     * @throws RemoteException On RMI error.
     */
    public Packet rpc(Packet sendPacket) throws RemoteException;
}
