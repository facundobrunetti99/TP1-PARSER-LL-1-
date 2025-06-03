package org.example;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase responsable de la visualización del árbol de análisis sintáctico
 */
public class TreeVisualizer {

    /**
     * Imprime el árbol en consola de forma visual usando caracteres ASCII
     */
    public static void printTree(TreeNode node, String prefix, boolean isLast) {
        if (node == null) return;

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getLabel());

        for (int i = 0; i < node.getChildCount(); i++) {
            boolean isLastChild = (i == node.getChildCount() - 1);
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            printTree(node.getChildren()[i], newPrefix, isLastChild);
        }
    }

    /**
     * Genera un archivo .dot para visualización con Graphviz
     */
    public static void saveTreeAsDot(TreeNode root, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("digraph ParseTree {\n");
            writer.write("    node [shape=box, style=rounded];\n");
            writer.write("    rankdir=TB;\n");
            writer.write("    bgcolor=white;\n");
            writer.write("    edge [color=black];\n");

            if (root != null) {
                generateDotFile(root, writer);
            }

            writer.write("}\n");
            System.out.println("Archivo .dot generado: " + filename);
            System.out.println("Para visualizar ejecuta: dot -Tpng " + filename + " -o arbol.png");
        } catch (IOException e) {
            System.err.println("Error al generar archivo .dot: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar recursivo para generar contenido del archivo .dot
     */
    private static void generateDotFile(TreeNode node, FileWriter writer) throws IOException {
        if (node == null) return;

        // Escribir el nodo con formato especial para terminales y no-terminales
        String nodeStyle = isTerminal(node.getLabel()) ?
                "[label=\"%s\", style=\"rounded,filled\", fillcolor=lightblue]" :
                "[label=\"%s\", style=\"rounded,filled\", fillcolor=lightgreen]";

        writer.write(String.format("    node%d " + nodeStyle + ";\n",
                node.getId(),
                escapeLabel(node.getLabel())));

        // Escribir las conexiones con los hijos
        for (int i = 0; i < node.getChildCount(); i++) {
            writer.write(String.format("    node%d -> node%d;\n",
                    node.getId(),
                    node.getChildren()[i].getId()));
            generateDotFile(node.getChildren()[i], writer);
        }
    }

    /**
     * Determina si un símbolo es terminal
     */
    private static boolean isTerminal(String label) {
        return label.startsWith("'") || label.equals("λ");
    }

    /**
     * Escapa caracteres especiales para el formato .dot
     */
    private static String escapeLabel(String label) {
        return label.replace("\"", "\\\"").replace("\\", "\\\\");
    }

    /**
     * Imprime el encabezado del árbol
     */
    public static void printTreeHeader() {
        System.out.println("\nÁrbol de análisis sintáctico:");
        System.out.println("═════════════════════════════");
    }
}