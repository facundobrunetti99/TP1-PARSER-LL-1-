package org.example;

import java.util.Scanner;

/**
 * Clase principal del Parser LL(1) con generación de árbol sintáctico
 *
 * Gramática implementada:
 * E  -> T E'
 * E' -> | T E'
 * E' -> λ
 * T  -> F T'
 * T' -> . F T'
 * T' -> λ
 * F  -> P F'
 * F' -> *
 * F' -> λ
 * P  -> ( E )
 * P  -> L
 * L  -> a
 * L  -> b
 * L  -> c
 */
public class ParserLL1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("════════════════════════════════════════");
        System.out.println("      PARSER LL(1) - ANALIZADOR        ");
        System.out.println("════════════════════════════════════════");
        System.out.println("Gramática soportada:");
        System.out.println("  Símbolos terminales: a, b, c, (, ), |, ., *");
        System.out.println("  Operadores: | (disyunción), . (concatenación), * (clausura)");
        System.out.println("════════════════════════════════════════");

        System.out.print("Ingrese una cadena: ");
        String input = scanner.nextLine().trim();
        scanner.close();

        if (input.isEmpty()) {
            System.out.println("Error: La cadena no puede estar vacía.");
            return;
        }

        try {
            // Crear el parser
            ParserCore parser = new ParserCore(input);

            // Mostrar proceso de análisis
            System.out.println("\nProceso de análisis:");
            System.out.println("━━━━━━━━━━━━━━━━━━━━");
            ParserLogger.printHeader();

            // Realizar el análisis
            TreeNode root = parser.parse();

            // Verificar que se haya consumido toda la entrada
            if (!parser.hasMoreInput()) {
                ParserLogger.printFooter();
                ParserLogger.printSuccess();

                // Mostrar el árbol de análisis
                displayParseTree(root, input);

            } else {
                ParserLogger.printFooter();
                throw new RuntimeException("Caracteres adicionales al final de la cadena");
            }

        } catch (RuntimeException e) {
            ParserLogger.printFooter();
            ParserLogger.printError(e.getMessage());
        }
    }

    /**
     * Muestra el árbol de análisis sintáctico en diferentes formatos
     */
    private static void displayParseTree(TreeNode root, String input) {
        // Mostrar árbol en consola
        TreeVisualizer.printTreeHeader();
        TreeVisualizer.printTree(root, "", true);

        // Generar archivo .dot
        String filename = "arbol_sintactico_" + sanitizeFilename(input) + ".dot";
        TreeVisualizer.saveTreeAsDot(root, filename);

        // Mostrar instrucciones adicionales
        System.out.println("\n" + "═".repeat(50));
        System.out.println("OPCIONES DE VISUALIZACIÓN:");
        System.out.println("═".repeat(50));
        System.out.println("1. Árbol mostrado arriba (formato texto)");
        System.out.println("2. Archivo .dot generado: " + filename);
        System.out.println("3. Para generar imagen PNG:");
        System.out.println("   dot -Tpng " + filename + " -o arbol.png");
        System.out.println("4. Para generar imagen SVG:");
        System.out.println("   dot -Tsvg " + filename + " -o arbol.svg");
        System.out.println("═".repeat(50));
    }

    /**
     * Limpia el nombre del archivo removiendo caracteres no válidos
     */
    private static String sanitizeFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
    }
}