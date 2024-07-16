import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.isNull;

public class Horse {

    private final String name;
    private final double speed;
    private double distance;

    public static final Logger log = LoggerFactory.getLogger(Horse.class);
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SS");

    public Horse(String name, double speed, double distance) {
        if (isNull(name)) {
            log.error("{} ERROR Horse: Name is null", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Name cannot be null.");
        } else if (name.isBlank()) {
            log.error("{} ERROR Horse: Name is blank", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (speed < 0) {
            log.error("{} ERROR Horse: Speed is negative", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
        if (distance < 0) {
            log.error("{} ERROR Horse: Distance is negative", DATE_FORMATTER.format(new Date()));
            throw new IllegalArgumentException("Distance cannot be negative.");
        }

        this.name = name;
        this.speed = speed;
        this.distance = distance;
        log.debug("{} DEBUG Horse: Создание Horse, имя {}, скорость {}",
                DATE_FORMATTER.format(new Date()), name, speed);
    }

    public Horse(String name, double speed) {
        this(name, speed, 0);
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void move() {
        distance += speed * getRandomDouble(0.2, 0.9);
    }

    public static double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
