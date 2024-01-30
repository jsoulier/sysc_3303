/**
 * TODO
 */
public enum Request {
    /** TODO */
    INVALID ((byte) 0, null),

    /** TODO */
    READ    ((byte) 1, new byte[] { 0, 3, 0, 1 }),

    /** TODO */
    WRITE   ((byte) 2, new byte[] { 0, 4, 0, 0 });

    /** TODO */
    private final byte value;

    /** TODO */
    private final byte[] response;

    /**
     * TODO
     * @param value
     * @param response
     */
    Request(byte value, byte[] response) {
        this.value = value;
        this.response = response;
    }

    /**
     * TODO
     * @return
     */
    public byte toByte() {
        return value;
    }

    /**
     * TODO
     * @param value
     * @return
     */
    public static Request fromByte(byte value) {
        for (Request request : Request.values()) {
            if (request.toByte() == value) {
                return request;
            }
        }
        throw new RuntimeException();
    }

    /**
     * TODO
     * @return
     */
    public byte[] getResponse() {
        return this.response;
    }

    /**
     * TODO
     * @return
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
