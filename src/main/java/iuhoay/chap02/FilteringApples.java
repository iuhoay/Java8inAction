package iuhoay.chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FilteringApples
 */
public class FilteringApples {
    public static void main(String[] args) {
        System.out.println("Apples that are red, green or yellow:");
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                new Apple(160, "yellow"),
                new Apple(200, "red"));

        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples);

        List<Apple> redApples = filterApplesByColor(inventory, "red");
        System.out.println(redApples);

        List<Apple> heavyApples = filterApplesByWeight(inventory, 150);
        System.out.println(heavyApples);

        System.out.println("--------------------------------------------------");

        List<Apple> greenApples2 = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println(greenApples2);

        List<Apple> redApples2 = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples2);

        List<Apple> heavyApples2 = filterApples(inventory, new AppleHeavyWeightPredicate());
        System.out.println(heavyApples2);

        System.out.println("## Lambda --------------------------------------------------");

        List<Apple> greenApples3 = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println(greenApples3);

        List<Apple> redApples3 = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(redApples3);

        System.out.println("--------------------------------------------------");

        List<Car> cars = Arrays.asList(
                new Car("Ford", "red"),
                new Car("Ford", "blue"),
                new Car("BMW", "red"));
        formatterPrint(cars, (car) -> "A " + car.getColor() + " " + car.getBrand());

        List<Car> redCars = filter(cars, (Car car) -> "red".equals(car.getColor()));
        formatterPrint(redCars, (car) -> "A " + car.getColor() + " " + car.getBrand());

        System.out.println("--------------------------------------------------");

        printApples(inventory, new AppleFancyFormatter());

        System.out.println("--------------------------------------------------");

        printApples(inventory, new AppleSimpleFormatter());
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void printApples(List<Apple> inventory, ApplePrintFormatter formatter) {
        for (Apple apple: inventory) {
            String out = formatter.accept(apple);
            System.out.println(out);
        }
    }

    public static class Apple {
        private Integer weight;
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getWeight() {
            return weight;
        }

        public Apple(Integer weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    static class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleGreenColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    public interface ApplePrintFormatter {
        String accept(Apple apple);
    }

    static class AppleFancyFormatter implements ApplePrintFormatter {
        @Override
        public String accept(Apple apple) {
            String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
            return "A " + characteristic + " " + apple.getColor() + " apple";
        }
    }

    static class AppleSimpleFormatter implements ApplePrintFormatter {
        @Override
        public String accept(Apple apple) {
            return "A " + apple.getColor() + " apple of " + apple.getWeight() + " g";
        }
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t: list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public interface SimpleFormatter<T> {
        String accept(T t);
    }

    static <T> void formatterPrint(List<T> inventory, SimpleFormatter<T> formatter) {
        for (T t: inventory) {
            String out = formatter.accept(t);
            System.out.println(out);
        }
    }

    public static class Car {
        private String brand;
        private String color;

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrand() {
            return brand;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        public Car(String brand, String color) {
            this.brand = brand;
            this.color = color;
        }
    }

}
