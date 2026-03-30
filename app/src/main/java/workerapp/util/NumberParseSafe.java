package workerapp.util;

import java.util.Optional;
import java.util.function.Function;

public class NumberParseSafe {

    public static <N> Optional<N>  parse(String toNumber, Function<String, N> parse) { 
        try {
            return Optional.of(parse.apply(toNumber)); 
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    } 
    
}
