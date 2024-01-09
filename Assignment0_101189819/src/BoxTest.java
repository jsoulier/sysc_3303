import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    @Test
    void test() {
        Box box = new Box();
        String string = "Hello World";

        assertTrue(box.isEmpty());
        box.put(string);
        assertFalse(box.isEmpty());
        string = (String) box.get();
        assertTrue(box.isEmpty());

        System.out.println(string);
    }
}
