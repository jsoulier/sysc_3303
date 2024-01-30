/**
 * TODO
 */
public enum Request {
    /** TODO */
    INVALID ((byte) 0),

    /** TODO */
    READ    ((byte) 1),

    /** TODO */
    WRITE   ((byte) 2);

    /** TODO */
    private final byte value;

    /**
     * TODO
     * @param value
     */
    Request(byte value) {
        this.value = value;
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
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
