package iuhoay.chap1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilteringApples {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(10, "red"), new Apple(20, "green"), new Apple(30, "green"));

        System.out.println("Print Green Apples");
        List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
        System.out.println(greenApples);

        System.out.println("Print Heavy Apples");
        List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
        System.out.println(heavyApples);

        System.out.println("Print Red Apples");
        List<Apple> redApples = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(redApples);

        System.out.println("Print Grren and Heavy Apples");
        List<Apple> greenAndHeavyApples = filterApples(inventory, (Apple apple) -> isGreenApple(apple) && isHeavyApple(apple));
        System.out.println(greenAndHeavyApples);

        System.out.println("Print light Apples");
        List<Apple> lightApples = inventory.stream()
            .filter(FilteringApples::isLightApple)
            .collect(Collectors.toList());

        Long startTime = System.currentTimeMillis();
        System.out.println("Stream: Group Apples by color");
        Map<String, List<Apple>> appleMap = inventory.stream()
            .collect(Collectors.groupingBy(Apple::getColor));
        System.out.println(appleMap);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        System.out.println("Paralll Stream: Group Apples by weight");
        Map<String, List<Apple>> appleMap2 = inventory.parallelStream()
            .collect(Collectors.groupingBy(Apple::getColor));
        System.out.println(appleMap2);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() >= 20;
    }

    public static boolean isLightApple(Apple apple) {
        return apple.getWeight() < 20;
    }

    public interface Predicate<T> {
        boolean test(T t);
    }

    static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

	public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public String getColor() {
            return color;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setColor(String color) {
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
}
