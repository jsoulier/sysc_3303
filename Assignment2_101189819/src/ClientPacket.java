import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 */
public class ClientPacket {

    /** TODO */
    private Request request;

    /** TODO */
    private String fileName;

    /** TODO */
    private Mode mode;

    /**
     * TODO
     * @param request
     * @param fileName
     * @param mode
     */
    public ClientPacket(Request request, String fileName, Mode mode) {
        this.request = request;
        this.fileName = fileName;
        this.mode = mode;
    }

    /**
     * TODO
     * @param bytes
     * @param start
     * @return
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
     * TODO
     * @param packet
     * @param address
     * @param port
     * @return
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
     * TODO
     * @param packet
     * @return
     */
    public static ClientPacket create(DatagramPacket datagram) {
        Request request = Request.fromByte(datagram.getData()[1]);
        String fileName = readString(datagram.getData(), 2);
        Mode mode = Mode.fromString(readString(datagram.getData(), fileName.getBytes().length + 3));
        return new ClientPacket(request, fileName, mode);
    }

    /**
     * TODO
     * @return
     */
    public Request getRequest() {
        return request;
    }

    /**
     * TODO
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * TODO
     * @return
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * TODO
     * @return
     */
    public int getLength() {
        int fileNameSize = fileName.getBytes().length;
        int modeSize = mode.getBytes().length;
        return fileNameSize + modeSize + 4;
    }

    /**
     * TODO
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
