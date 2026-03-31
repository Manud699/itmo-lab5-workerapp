package workerapp.util;

import java.util.Optional;
import java.util.function.Function;


/**
 * Class NumberParseSafe
 * Provides a safe way to parse numbers from strings without throwing exceptions.
 */
public class NumberParseSafe {


    /**
     * Attempts to parse a string into a number using the provided parsing function.
     * @param <N> the type of the number to parse
     * @param toNumber the string to parse
     * @param parse the function to use for parsing
     * @return an Optional containing the parsed number, or empty if parsing fails
     */
    public static <N> Optional<N>  parse(String toNumber, Function<String, N> parse) { 
        try {
            return Optional.of(parse.apply(toNumber)); 
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    } 
    
}
