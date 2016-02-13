package service.fizzbuzz;

import service.ListConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FizzBuzzConverter implements ListConverter<Integer, String> {

    public List<String> convert(List<Integer> numbers) {
        List<String> results = new ArrayList<>();
        for (Integer number : numbers) {
            results.add(applyFizzBuzz(number));
        }
        return results;
    }

    private String applyFizzBuzz(Integer number) {
        String fizzBuzz = getFizzBuzzOrEmptyString(number);
        return fizzBuzz.isEmpty() ? Integer.toString(number) : fizzBuzz;
    }

    private String getFizzBuzzOrEmptyString(Integer number) {
        StringBuilder sb = new StringBuilder();
        for (FizzBuzzDivisionChecker checker : FizzBuzzDivisionChecker.values()) {
            if (checker.check(number)) {
                sb.append(checker.name().toLowerCase()).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
