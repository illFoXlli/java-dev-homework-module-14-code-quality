package com.example;

import java.util.Scanner;

public class App {

    private static final byte PLAYER_WIN = 1;
    private static final byte COMPUTER_WIN = 2;
    private static final byte DRAW = 3;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        byte winner = 0;

        char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        System.out.println("Enter box number to select. Enjoy!");

        while (winner == 0) {
            printBoard(box);

            handlePlayerMove(scan, box);

            if (checkWinner(box, 'X')) {
                winner = PLAYER_WIN;
            } else if (!isBoxAvailable(box)) {
                winner = DRAW;
            } else {
                makeComputerMove(box);

                if (checkWinner(box, 'O')) {
                    winner = COMPUTER_WIN;
                }
            }
        }

        switch (winner) {
            case PLAYER_WIN -> System.out.println("You won the game! Created by Shreyas Saha. Thanks for playing!");
            case COMPUTER_WIN -> System.out.println("You lost the game! Created by Shreyas Saha. Thanks for playing!");
            case DRAW -> System.out.println("It's a draw! Created by Shreyas Saha. Thanks for playing!");
            default -> throw new IllegalStateException("Unexpected value: " + winner);
        }

        scan.close();
    }

    private static void printBoard(char[] box) {
        String board = String.format(
                "%n%n %s | %s | %s %n-----------%n %s | %s | %s %n-----------%n %s | %s | %s %n",
                colorize(box[0]), colorize(box[1]), colorize(box[2]),
                colorize(box[3]), colorize(box[4]), colorize(box[5]),
                colorize(box[6]), colorize(box[7]), colorize(box[8])
        );
        System.out.println(board);
    }

    private static void handlePlayerMove(Scanner scan, char[] box) {
        byte input;

        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O') {
                    System.out.println("That one is already in use. Enter another.");
                } else {
                    box[input - 1] = 'X';
                    break;
                }
            } else {
                System.out.println("Invalid input. Enter again.");
            }
        }
    }

//    private static boolean checkWinner(char[] box, char symbol) {
//        return (box[0] == symbol && box[1] == symbol && box[2] == symbol)
//               || (box[3] == symbol && box[4] == symbol && box[5] == symbol)
//               || (box[6] == symbol && box[7] == symbol && box[8] == symbol)
//
//               || (box[0] == symbol && box[3] == symbol && box[6] == symbol)
//               || (box[1] == symbol && box[4] == symbol && box[7] == symbol)
//               || (box[2] == symbol && box[5] == symbol && box[8] == symbol)
//
//               || (box[0] == symbol && box[4] == symbol && box[8] == symbol)
//               || (box[2] == symbol && box[4] == symbol && box[6] == symbol);
//    }

    private static final int[][] WIN_COMBINATIONS = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    private static boolean checkWinner(char[] box, char symbol) {
        for (int[] combo : WIN_COMBINATIONS) {
            if (box[combo[0]] == symbol &&
                box[combo[1]] == symbol &&
                box[combo[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBoxAvailable(char[] box) {
        for (char c : box) {
            if (c != 'X' && c != 'O') {
                return true;
            }
        }
        return false;
    }

    private static void makeComputerMove(char[] box) {
        byte rand;

        while (true) {
            rand = (byte) (Math.random() * 9 + 1);
            if (box[rand - 1] != 'X' && box[rand - 1] != 'O') {
                box[rand - 1] = 'O';
                break;
            }
        }
    }

    private static String colorize(char c) {
        if (c == 'X') {
            return RED + c + RESET;
        } else if (c == 'O') {
            return BLUE + c + RESET;
        }
        return String.valueOf(c);
    }
}