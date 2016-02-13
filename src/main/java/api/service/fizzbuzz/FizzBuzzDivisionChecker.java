package api.service.fizzbuzz;

enum FizzBuzzDivisionChecker {
    FIZZ(3),
    BUZZ(5);

    private int divisor;

    FizzBuzzDivisionChecker(int divisor) {
        this.divisor = divisor;
    }

    boolean check(int number) {
        return number % divisor == 0;
    }
}
