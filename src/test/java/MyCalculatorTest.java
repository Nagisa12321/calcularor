import org.junit.Test;

import static org.junit.Assert.*;

public class MyCalculatorTest {

    @Test
    public void calculate() {
        assertEquals("-5.0",MyCalculator.Calculate("5 - 5 - 5"));
    }
}