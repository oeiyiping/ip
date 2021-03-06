import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    public void testInitialization() {
        Deadline deadline = new Deadline("description", LocalDateTime.parse("2021-01-01T00:00"));
        Assertions.assertEquals("[D][ ] description (by: 01 Jan 2021, 00:00)", deadline.toString());

        Deadline deadline2 = new Deadline("description with space", LocalDateTime.parse("2021-01-01T00:00"));
        Assertions.assertEquals("[D][ ] description with space (by: 01 Jan 2021, 00:00)", deadline2.toString());
    }

    @Test
    public void testDone() {
        Deadline deadline = new Deadline("description", LocalDateTime.parse("2021-01-01T00:00"));
        deadline.setDone();
        Assertions.assertEquals("[D][X] description (by: 01 Jan 2021, 00:00)", deadline.toString());
    }
}
