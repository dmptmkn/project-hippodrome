import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;


class HippodromeTest {

    @Test
    void testConstructor_ShouldThrowException_WhenArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void testConstructor_ShouldThrowExceptionWithExpectedMessage_WhenArgIsNull() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    void testConstructor_ShouldThrowException_WhenArgIsEmpty() {
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    void testConstructor_ShouldThrowExceptionWithExpectedMessage_WhenArgIsEmpty() {
        List<Horse> horses = new ArrayList<>();
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    void testGetHorses_ShouldReturnTheSameListAsWasPassedIntoConstructor_WhenCalled() {
        List<Horse> horses = Stream.generate(() -> Instancio.create(Horse.class))
                .limit(30)
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> horsesFromHippodrome = hippodrome.getHorses();

        assertEquals(horses, horsesFromHippodrome);
    }


    @Test
    void testMove_ShouldCallTheMethodOnEveryElementOnTheList_WhenCalled() {
        List<Horse> horses = Stream.generate(() -> Mockito.mock(Horse.class))
                .limit(50)
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        horses.forEach(h -> Mockito.verify(h, times(1)).move());
    }

    @Test
    void testGetWinner_ShouldReturnAnObjectWithMaxExpectedValue_whenCalled() {
        List<Horse> horses = new ArrayList<>();
        Horse horse1 = new Horse("Horsie1", 20, 20);
        Horse horse2 = new Horse("Horsie2", 20, 30);
        Horse horse3 = new Horse("Horsie3", 20, 40);
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertSame(horse3, hippodrome.getWinner());
    }
}