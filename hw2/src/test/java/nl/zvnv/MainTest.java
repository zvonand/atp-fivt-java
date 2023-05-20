package nl.zvnv;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void helloTest() {
        Assertions.assertThat(2 + 2).isEqualTo(4);
    }
}
