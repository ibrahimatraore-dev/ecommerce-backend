package alten.core.utils;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class MapperUtils {

    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY = "";

    @Named("concatFullName")
    public static String concatFullName(String firstName, String lastName) {
        return String.format("%s%s%s",
                Objects.toString(firstName, EMPTY).trim(),
                EMPTY_SPACE,
                Objects.toString(lastName, EMPTY).trim());
    }

    @Named("average")
    public static double average(double... numbers) {
        if (numbers == null || numbers.length == 0) return 0.0;

        var avg = Arrays.stream(numbers)
                .average()
                .orElse(0.0);

        return Math.round(avg * 100.0) / 100.0;
    }
}
