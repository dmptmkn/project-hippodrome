import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Mock
    Horse horse;

    private final String testName = "Horsie";
    private final double testSpeed = 20.0;
    private final double testDistance = 40.0;

    //naming patter: test{Method}_Should{Do}_When{Condition}

    @Test
    void testConstructor_ShouldThrowException_WhenArgNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(null, testSpeed);
        });
    }

    @Test
    void testConstructor_ShouldThrowExceptionWithCertainMessage_WhenArgNameIsNull() {
        try {
            Horse horse = new Horse(null, testSpeed);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\f"})
    void testConstructor_ShouldThrowException_WhenArgNameIsBlank(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(name, testSpeed);
        });

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\f"})
    void testConstructor_ShouldThrowExceptionWithCertainMessage_WhenArgNameIsBlank(String name) {
        try {
            Horse horse = new Horse(name, testSpeed);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    void testConstructor_ShouldThrowException_WhenArgSpeedIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(testName, -testSpeed);
        });
    }

    @Test
    void testConstructor_ShouldThrowExceptionWithCertainMessage_WhenArgSpeedIsNegative() {
        try {
            Horse horse = new Horse(testName, -testSpeed);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    void testConstructor_ShouldThrowException_WhenArgDistanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(testName, testSpeed, -testDistance);
        });
    }

    @Test
    void testConstructor_ShouldThrowExceptionWithCertainMessage_WhenArgDistanceIsNegative() {
        try {
            Horse horse = new Horse(testName, testSpeed, -testDistance);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void testGetName_ShouldReturnExpectedValue_WhenCalled() {
        assertEquals("Horsie", new Horse(testName, testSpeed).getName());
    }

    @Test
    @SneakyThrows
    void estGetName_ShouldReturnExpectedValue_WhenCalledViaReflection() {
        String expectedName = "Horsie";

        Horse horse = new Horse(testName, testSpeed);
        Field field = horse.getClass().getDeclaredField("name");
        field.setAccessible(true);
        String actualName = (String) field.get(horse);

        assertEquals(expectedName, actualName);
    }

    @Test
    void testGetSpeed_ShouldReturnExpectedValue_WhenCalled() {
        assertEquals(20.0, new Horse(testName, testSpeed).getSpeed());
    }

    @Test
    @SneakyThrows
    void testGetSpeed_ShouldReturnExpectedValue_WhenCalledViaReflection() {
        double expectedSpeed = 20.0;

        Horse horse = new Horse(testName, testSpeed);
        Field field = horse.getClass().getDeclaredField("speed");
        field.setAccessible(true);
        double actualSpeed = (double) field.get(horse);

        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void testGetDistance_ShouldReturnExpectedValue_WhenCalled() {
        assertEquals(testDistance, new Horse(testName, testSpeed, testDistance).getDistance());
    }

    @Test
    @SneakyThrows
    void testGetDistance_ShouldReturnExpectedValue_WhenCalledViaReflection() {
        double expectedDistance = 40.0;

        Horse horse = new Horse(testName, testSpeed, testDistance);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double actualDistance = (double) field.get(horse);

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void testGetDistance_ShouldReturnZeroValue_WhenObjectWasCreatedViaTwoArgsConstructor() {
        assertEquals(0, new Horse(testName, testSpeed).getDistance());
    }

    @Test
    void testMove_ShouldCallAnotherMethodWithCertainArguments_WhenCalled() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horsie = new Horse(testName, testSpeed);
            horsie.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.2, 0.5, 0.9, 1, 10, 100, 1000})
    void testMove_ShouldComputeDistanceUsingCertainFormula_WhenCalled(double fakeValue) {
        Horse horsie = new Horse(testName, testSpeed, testDistance);
        double expectedDistance = horsie.getDistance() + horsie.getSpeed() * fakeValue;

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeValue);
            horsie.move();
            double actualDistance = horsie.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }
}