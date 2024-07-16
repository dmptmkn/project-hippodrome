import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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
        //переписать через стрим
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(Instancio.create(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> horsesFromHippodrome = hippodrome.getHorses();

        assertEquals(horses, horsesFromHippodrome);
    }


    @Test
    void testMove_ShouldCallTheMethodOnEveryElementOnTheList_WhenCalled() {
        //переписать через стрим
        Horse mockHorse = Mockito.mock(Horse.class);
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mockHorse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        Mockito.verify(mockHorse, times(50)).move();
    }

    @Test
    void testGetWinner_ShouldReturnAnObjectWithMaxExpectedValue_whenCalled() {
        double maxDistance = 40;

        List<Horse> horses = new ArrayList<>();
        Horse horse1 = new Horse("Horsie1", 20, 20);
        Horse horse2 = new Horse("Horsie2", 20, 30);
        Horse horse3 = new Horse("Horsie3", 20, maxDistance);
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertSame(horse3, hippodrome.getWinner());
    }
}