
package org.example;

/**
 * Clase que representa un nodo del árbol de análisis sintáctico
 */
public class TreeNode {
    private String label;
    private int id;
    private TreeNode[] children;
    private int childCount;
    private static int nodeCounter = 0;

    public TreeNode(String label) {
        this.label = label;
        this.id = nodeCounter++;
        this.children = new TreeNode[10]; // máximo 10 hijos
        this.childCount = 0;
    }

    public void addChild(TreeNode child) {
        if (childCount < children.length) {
            children[childCount++] = child;
        }
    }

    // Getters
    public String getLabel() { return label; }
    public int getId() { return id; }
    public TreeNode[] getChildren() { return children; }
    public int getChildCount() { return childCount; }

    // Método para resetear el contador (útil para múltiples parseos)
    public static void resetCounter() {
        nodeCounter = 0;
    }
}