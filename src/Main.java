import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] map;
    public static int mapSize = 5;
    public static int lineToWin = 4;
    public static char playerSign = 'X';
    public static char computerSign = 'O';

    public static void main(String[] args) {
        playTheGame();
    }

    static void playTheGame() {
        initMap();
        printMap();

        while (true) {
            playerMove();
            printMap();
            if (checkWin(playerSign)) {
                System.out.println("Вы выиграли!!!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            computerMove();
            printMap();
            if (checkWin(computerSign)) {
                System.out.println("Выиграл компьютер.");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
    }

    static boolean checkWin(char sign) {
        for (int offsetX = 0; offsetX < mapSize - lineToWin + 1; offsetX++) {
            for (int offsetY = 0; offsetY < mapSize - lineToWin + 1; offsetY++) {
                if (checkLines(sign, offsetX, offsetY) || checkDiagonals(sign, offsetX, offsetY)) return true;
            }
        }
        return false;
    }

    static boolean isMapFull() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == '-') return false;
            }
        }
        return true;
    }

    //2 Переделать проверку победы, чтобы она не была реализована просто набором условий,
    //например, с использованием циклов.
    //3 Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и
    //количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
    //возможных ситуаций
    static boolean checkLines(char sign, int offsetX, int ofssetY) {
        boolean horizontal, vertical;
        for (int i = 0; i < lineToWin + offsetX; i++) {
            horizontal = true;
            vertical = true;
            for (int j = 0; j < lineToWin + ofssetY; j++) {
                horizontal &= (map[i][j] == sign);
                vertical &= (map[j][i] == sign);
            }
            if (horizontal || vertical) return true;
        }
        return false;
    }

    //2 Переделать проверку победы, чтобы она не была реализована просто набором условий,
    //например, с использованием циклов.
    //3 Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и
    //количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
    //возможных ситуаций
    static boolean checkDiagonals(char sign, int x, int y) {
        boolean fromLeftUpToRightDown = true;
        boolean fromLeftDownToRightUp = true;
        for (int i = 0; i < lineToWin; i++) {
            fromLeftDownToRightUp &= (map[i + x][i + y] == sign);
            fromLeftUpToRightDown &= (map[lineToWin - i - 1 + x][i + y] == sign);
        }
        return fromLeftDownToRightUp || fromLeftUpToRightDown;
    }

    static void computerMove() {
        int x, y;
        Random random = new Random();

        do {
            x = random.nextInt(5);
            y = random.nextInt(5);
        } while (!isCellEmpty(map, x, y));
        System.out.println("Ход компьютера...");
        System.out.println(String.format("Компьютер выбрал ячейку [%s, %s]", x + 1, y + 1));
        map[x][y] = computerSign;
    }

    static void playerMove() {
        int x, y;
        boolean isEmptyCell;
        do {
            boolean isCorrectCoordinate;
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите координату X [1, 2, 3, 4, 5]");
                x = scanner.nextInt() - 1;
                System.out.println("Введите координату Y [1, 2, 3, 4, 5]");
                y = scanner.nextInt() - 1;
                isCorrectCoordinate = checkCorrectCoordinate(x, y);
                notifyIncorrectCoordinate(isCorrectCoordinate);
            } while (!isCorrectCoordinate);
            isEmptyCell = isCellEmpty(map, x, y);
            notifyOccupiedCell(isEmptyCell, x, y);
        } while (!isCellEmpty(map, x, y));

        map[x][y] = playerSign;
    }

    static boolean isCellEmpty(char[][] map, int x, int y) {
        return map[x][y] == '-';
    }

    static void notifyOccupiedCell(boolean isOccupied, int x, int y) {
        if (!isOccupied) {
            System.out.println(String.format("Ячейка с координатами [%s, %s] занята.", x + 1, y + 1));
        }
    }

    static boolean checkCorrectCoordinate(int x, int y) {
        return (x >= 0 && x <= 4) && (y >= 0 && y <= 4);
    }

    static void notifyIncorrectCoordinate(boolean isCorrect) {
        if (!isCorrect) {
            System.out.println("Выбранные координаты некорректны. Доступный диапазон [1, 2, 3, 4, 5]");
        }
    }

    static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void initMap() {
        map = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = '-';
            }
        }
    }
}