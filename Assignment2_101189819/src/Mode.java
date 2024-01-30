/**
 * TODO
 */
public enum Mode {
    /** TODO */
    NETASCII,

    /** TODO */
    OCTET;

    /**
     * TODO
     * @return
     */
    public byte[] getBytes() {
        return toString().getBytes();
    }

    /**
     * TODO
     * @param value
     * @return
     */
    public static Mode fromString(String value) {
        for (Mode mode : Mode.values()) {
            if (value.equals(mode.toString())) {
                return mode;
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
