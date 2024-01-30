import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Wrapper for client to server datagram packets.
 */
public class ClientPacket {

    /** The server request. */
    private Request request;

    /** The file name. */
    private String fileName;

    /** The mode. */
    private Mode mode;

    /**
     * Create a new client packet.
     * @param request The server request.
     * @param fileName The file name.
     * @param mode The mode.
     */
    public ClientPacket(Request request, String fileName, Mode mode) {
        this.request = request;
        this.fileName = fileName;
        this.mode = mode;
    }

    /**
     * Read a string from a byte array.
     * @param bytes The byte array.
     * @param start The starting index.
     * @return The string.
     */
    public static String readString(byte[] bytes, int start) {
        int i = 0;
        for (; start + i < bytes.length; i++) {
            if (bytes[start + i] == 0) {
                break;
            }
        }
        return new String(bytes, start, i);
    }

    /**
     * Create a datagram packet from a client packet, address, and port.
     * @param packet The client packet.
     * @param address The address.
     * @param port The port.
     * @return The datagram packet.
     */
    public static DatagramPacket create(ClientPacket packet, InetAddress address, int port) {
        ByteBuffer buffer = ByteBuffer.allocate(packet.getLength());
        buffer.put((byte) 0);
        buffer.put(packet.getRequest().toByte());
        buffer.put(packet.getFileName().getBytes());
        buffer.put((byte) 0);
        buffer.put(packet.getMode().getBytes());
        buffer.put((byte) 0);
        return new DatagramPacket(buffer.array(), buffer.capacity(), address, port);
    }

    /**
     * Create a client packet from a datagram packet.
     * @param packet The datagram packet.
     * @return The client packet.
     */
    public static ClientPacket create(DatagramPacket datagram) {
        Request request = Request.fromByte(datagram.getData()[1]);
        String fileName = readString(datagram.getData(), 2);
        Mode mode = Mode.fromString(readString(datagram.getData(), fileName.getBytes().length + 3));
        return new ClientPacket(request, fileName, mode);
    }

    /**
     * Get the server request.
     * @return The server request.
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Get the file name.
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the mode.
     * @return The mode.
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Get the packet length.
     * @return The packet length.
     */
    public int getLength() {
        int fileNameSize = fileName.getBytes().length;
        int modeSize = mode.getBytes().length;
        return fileNameSize + modeSize + 4;
    }

    /**
     * Get the client packet as a string.
     * @return The client packet as a string.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Request=");
        string.append(request.toString());
        string.append(", ");
        string.append("FileName=");
        string.append(fileName);
        string.append(", ");
        string.append("Mode=");
        string.append(mode.toString());
        return string.toString();
    }
}
