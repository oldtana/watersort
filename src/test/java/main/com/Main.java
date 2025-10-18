package main.com;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите количество цветов ");
        Scanner scanner= new Scanner(System.in);
        int colors=scanner.nextInt();
        System.out.println("Введите объем ");
        int volume=scanner.nextInt();
        System.out.println("Введите количество пробирок ");
        int tubeCount=scanner.nextInt();
        int[][] begin = generateFirstState(colors, volume, tubeCount);
        Solve solver = new Solve();
        List<Move> solution = solver.solve(begin);
        if (solution != null) {
            System.out.println("Решение найдено! Ходы:");
            for (int i = 0; i < solution.size(); i++) {
                Move move = solution.get(i);
                System.out.println((i + 1) + ". Из " + move.fromTube + " в " + move.toTube);
            }
            System.out.println(" Всего ходов: " + solution.size());
        } else {
            System.out.println(" Решение не найдено");
        }
    }
    public static int[][] generateFirstState(int colors, int volume, int tubeCount) {
        int[][] state = new int[tubeCount][volume];
        List<Integer> drops = new ArrayList<>();

        for (int number = 1; number <= colors; number++) {
            for (int count = 0; count < volume; count++) {
                drops.add(number);
            }
        }

        Collections.shuffle(drops);

        int dropIndex = 0;
        for (int i = 0; i < tubeCount; i++) {
            for (int j = 0; j < volume; j++) {
                if (dropIndex < drops.size()) {
                    state[i][j] = drops.get(dropIndex);
                    dropIndex++;
                } else {
                    state[i][j] = 0;
                }
            }
        }

        System.out.println("Начальное состояние:");
        printState(state);
        return state;
    }

    public static void printState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            System.out.print("Пробирка " + i + ": [");
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(state[i][j]);
                if (j < state[i].length - 1) System.out.print(" ");
            }
            System.out.println("]");
        }
    }
}