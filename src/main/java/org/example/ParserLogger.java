package org.example;

/**
 * Clase responsable del logging y visualización del proceso de parsing
 */
public class ParserLogger {

    /**
     * Imprime el encabezado de la tabla de seguimiento
     */
    public static void printHeader() {
        System.out.println("┌────────────┬─────────────┐");
        System.out.println("│ FUNCIÓN    │ CARÁCTER    │");
        System.out.println("├────────────┼─────────────┤");
    }

    /**
     * Imprime el pie de la tabla de seguimiento
     */
    public static void printFooter() {
        System.out.println("└────────────┴─────────────┘");
    }

    /**
     * Imprime un paso del análisis sintáctico
     */
    public static void printStep(String function, char lookahead) {
        String displayChar = lookahead == '\0' ? "␣" : "'" + lookahead + "'";
        System.out.printf("│ %-10s │ %-11s │%n", function, displayChar);
    }

    /**
     * Imprime mensaje de éxito
     */
    public static void printSuccess() {
        System.out.println("Cadena válida.");
    }

    /**
     * Imprime mensaje de error
     */
    public static void printError() {
        System.out.println("Error de sintaxis.");
    }

    /**
     * Imprime mensaje de error con detalles
     */
    public static void printError(String message) {
        System.out.println("Error de sintaxis: " + message);
    }
}