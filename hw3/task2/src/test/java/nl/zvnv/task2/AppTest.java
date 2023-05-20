package nl.zvnv.task2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void testRunSucceeds() {
        RingProcessor processor = new RingProcessor(10, 81, "ring.test.log");
        Assertions.assertThatNoException().isThrownBy(processor::startProcessing);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThatNoException().isThrownBy(processor::logAvgNetworkDelay);
        Assertions.assertThatNoException().isThrownBy(processor::logAvgBufferDelay);
    }
}