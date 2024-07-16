import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

public class Hippodrome {

    private final List<Horse> horses;

    public static final Logger log = LoggerFactory.getLogger(Hippodrome.class);
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SS");

    public Hippodrome(List<Horse> horses) {
        if (isNull(horses)) {
            log.error("{} ERROR Hippodrome: Horses list is null", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Horses cannot be null.");
        } else if (horses.isEmpty()) {
            log.error("{} ERROR Hippodrome: Horses list is empty", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Horses cannot be empty.");
        }

        this.horses = horses;
        log.debug("{} DEBUG Hippodrome: Создание Hippodrome, лошадей {}",
                DATE_FORMATTER.format(new Date()), horses.size());
    }

    public List<Horse> getHorses() {
        return Collections.unmodifiableList(horses);
    }

    public void move() {
        horses.forEach(Horse::move);
    }

    public Horse getWinner() {
        return horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();
    }
}
