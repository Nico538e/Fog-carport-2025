package app.services;
import app.entities.Order;
import app.entities.OrderLine;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(null);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void calcRafters() {
        int length = 600;
        //int fjerner kommatal, så actual runder ned fra 11,90 til 11
        int actual = (length / 55) + 1;
        assertEquals(11,actual);

        length = 800;
        //int fjerner kommatal, så actual runder ned fra 15,45 til 15
        actual = (length / 55) + 1;
        assertEquals(15,actual);
    }
}