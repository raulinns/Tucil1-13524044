package queens;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

public class Solver {
    public record Koordinat(int x, int y) {}

    public static String[][] board;
    public static int totalKasus;

    public static boolean solve(Koordinat[] queenLocation, Consumer<Koordinat[]> onStep) {
        int n = board.length;

        while (true) {
            totalKasus++;
            int print = (n < 9) ? 1000 : 1000000;
            if (totalKasus % print == 0) {
                if (onStep != null) {
                    onStep.accept(queenLocation);
                }
                printBoard(queenLocation);
            }

            if (validSolution(queenLocation)) {
                return true;
            }

            int baris = 0;
            while (baris < n) {
                int kolom = queenLocation[baris].y;
                if (kolom < n - 1) {
                    queenLocation[baris] = new Koordinat(baris, kolom + 1);
                    break;
                } else {
                    queenLocation[baris] = new Koordinat(baris, 0);
                    baris++;
                }
            }

            if (baris == n) {
                break;
            }
        }
        return false;
    }

    // Valid Queen Checker
    public static boolean validSolution(Koordinat[] queenLocation) {
        for (int i = 0; i < queenLocation.length; i++) {
            for (int j = i + 1; j < queenLocation.length; j++) {
                if (queenLocation[i].x == queenLocation[j].x) {
                    return false;
                } else if (queenLocation[i].y == queenLocation[j].y) {
                    return false;
                } else if (checkColor(queenLocation[i]) == checkColor(queenLocation[j])) {
                    return false;
                } else if ((Math.abs(queenLocation[i].x - queenLocation[j].x) <= 1)
                        && Math.abs(queenLocation[i].y - queenLocation[j].y) <= 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int colorCount() {
        Set<String> uniqueColor = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                String color = board[i][j].trim();
                if (!color.isEmpty()) {
                    uniqueColor.add(color);
                }
            }
        }

        int colorCount = uniqueColor.size();
        return colorCount;
    }

    public static String checkColor(Koordinat queen) {
        String color = Solver.board[queen.x][queen.y];
        return color;
    }

    public static boolean loadBoard(String filePath) {
        try {
            File inputFile = new File(filePath);
            if (!inputFile.exists()) return false;

            int n = 0;
            Scanner sc = new Scanner(inputFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) n++;
            }
            sc.close();

            board = new String[n][n];

            sc = new Scanner(inputFile);
            int r = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    if (line.length() != n) {
                        sc.close();
                        return false;
                    }

                    String[] chars = line.split("");
                    board[r] = chars;
                    r++;

                }
            }
            sc.close();

            return board.length == colorCount();

        } catch (Exception e) {
            return false;
        }
    }

    public static void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println();
    }

    public static void printBoard(Koordinat[] queenLocation) {
        System.out.println("Banyak Kasus Ditinjau: " + totalKasus);
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean isQueen = false;
                for (int k = 0; k < queenLocation.length; k++) {
                    if (queenLocation[k] != null
                            && queenLocation[k].x == i
                            && queenLocation[k].y == j) {
                        isQueen = true;
                        break;
                    }
                }

                if (isQueen) {
                    System.out.print("#");
                } else {
                    System.out.print("" + board[i][j] + "");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");
        String file = input.nextLine();
        input.close();

        file = "src/main/input/" + file;

        boolean b = loadBoard(file);
        if (b) {
            int n = board.length;
            Koordinat[] queenLocation = new Koordinat[n];
            for (int i = 0; i < queenLocation.length; i++) {
                queenLocation[i] = new Koordinat(i, 0);
            }

            long startTime = System.currentTimeMillis();
            boolean solution = solve(queenLocation, null);
            long endTime = System.currentTimeMillis();

            if (solution) {
                System.out.println("\nSolusi: ");
                printBoard(queenLocation);
                System.out.println("Waktu Pencarian      : " + (endTime - startTime) + " ms");
                System.out.println("Banyak Kasus Ditinjau: " + totalKasus + " kasus");
            } else {
                System.out.println("Tidak ada solusi!");
            }
        } else {
            System.out.println("Test Case tidak valid!");
            return;
        }
    }
}
