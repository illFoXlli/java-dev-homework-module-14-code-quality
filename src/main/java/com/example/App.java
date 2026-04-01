package com.example;

import java.util.Scanner;
import java.util.logging.Logger;

public class App {

    private static final byte PLAYER_WIN = 1;
    private static final byte COMPUTER_WIN = 2;
    private static final byte DRAW = 3;

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        System.setErr(System.out); // записал что что логги будет выводиться как System.out
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n"); // убераю мусор с логгера своего
        Scanner scan = new Scanner(System.in);
        byte winner = 0;

        char[] box = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        logger.info("Enter box number to select. Enjoy!");

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

        if (winner == PLAYER_WIN) {
            logger.info("You won the game! Created by Shreyas Saha. Thanks for playing!");
        } else if (winner == COMPUTER_WIN) {
            logger.info("You lost the game! Created by Shreyas Saha. Thanks for playing!");
        } else {
            logger.info("It's a draw! Created by Shreyas Saha. Thanks for playing!");
        }

        scan.close();
    }

    private static void printBoard(char[] box) {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            String board = String.format(
                    "%n%n %s | %s | %s %n-----------%n %s | %s | %s %n-----------%n %s | %s | %s %n",
                    colorize(box[0]), colorize(box[1]), colorize(box[2]),
                    colorize(box[3]), colorize(box[4]), colorize(box[5]),
                    colorize(box[6]), colorize(box[7]), colorize(box[8])
            );
            logger.info(board);
        }
    }

    private static void handlePlayerMove(Scanner scan, char[] box) {
        byte input;

        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O') {
                    logger.warning("That one is already in use. Enter another.");
                } else {
                    box[input - 1] = 'X';
                    break;
                }
            } else {
                logger.warning("Invalid input. Enter again.");
            }
        }
    }

    private static boolean checkWinner(char[] box, char symbol) {
        return (box[0] == symbol && box[1] == symbol && box[2] == symbol) ||
                (box[3] == symbol && box[4] == symbol && box[5] == symbol) ||
                (box[6] == symbol && box[7] == symbol && box[8] == symbol) ||

                (box[0] == symbol && box[3] == symbol && box[6] == symbol) ||
                (box[1] == symbol && box[4] == symbol && box[7] == symbol) ||
                (box[2] == symbol && box[5] == symbol && box[8] == symbol) ||

                (box[0] == symbol && box[4] == symbol && box[8] == symbol) ||
                (box[2] == symbol && box[4] == symbol && box[6] == symbol);
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