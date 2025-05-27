package org.example;

import java.util.Scanner;

public class ParserLL1 {

    static String input;
    static int index;
    static char lookahead;

    static void printHeader() {
        System.out.println("┌────────────┬─────────────┐");
        System.out.println("│ FUNCIÓN    │ CARÁCTER    │");
        System.out.println("├────────────┼─────────────┤");
    }

    static void printFooter() {
        System.out.println("└────────────┴─────────────┘");
    }

    static void printStep(String function) {
        String displayChar = lookahead == '\0' ? "␣" : "'" + lookahead + "'";
        System.out.printf("│ %-10s │ %-11s │%n", function, displayChar);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una cadena: ");
        input = scanner.nextLine();
        scanner.close();

        index = 0;
        lookahead = input.length() > 0 ? input.charAt(index) : '\0';

        printHeader();
        try {
            E(); // símbolo inicial
            if (lookahead == '\0') {
                printFooter();
                System.out.println("Cadena válida.");
            } else {
                printFooter();
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            printFooter();
            System.out.println("Error de sintaxis.");
        }
    }

    static void match(char expected) {
        printStep("match('" + expected + "')");
        if (lookahead == expected) {
            index++;
            lookahead = index < input.length() ? input.charAt(index) : '\0';
        } else {
            throw new RuntimeException();
        }
    }

    static void E() {
        printStep("E()");
        T();
        EPrime();
    }

    static void EPrime() {
        printStep("E'()");
        if (lookahead == '|') {
            match('|');
            T();
            EPrime();
        }
    }

    static void T() {
        printStep("T()");
        F();
        TPrime();
    }

    static void TPrime() {
        printStep("T'()");
        if (lookahead == '.') {
            match('.');
            F();
            TPrime();
        }
    }

    static void F() {
        printStep("F()");
        P();
        FPrime();
    }

    static void FPrime() {
        printStep("F'()");
        if (lookahead == '*') {
            match('*');
        }
    }

    static void P() {
        printStep("P()");
        if (lookahead == '(') {
            match('(');
            E();
            match(')');
        } else {
            L();
        }
    }

    static void L() {
        printStep("L()");
        if (lookahead == 'a' || lookahead == 'b' || lookahead == 'c') {
            match(lookahead);
        } else {
            throw new RuntimeException();
        }
    }
}
