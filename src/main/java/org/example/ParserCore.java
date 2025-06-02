package org.example;

/**
 * Núcleo del parser LL(1) que contiene la lógica de análisis sintáctico
 */
public class ParserCore {

    private String input;
    private int index;
    private char lookahead;

    public ParserCore(String input) {
        this.input = input;
        this.index = 0;
        this.lookahead = input.length() > 0 ? input.charAt(index) : '\0';
    }

    /**
     * Función para hacer match de un carácter esperado
     */
    private TreeNode match(char expected) {
        ParserLogger.printStep("match('" + expected + "')", lookahead);

        if (lookahead == expected) {
            TreeNode node = new TreeNode("'" + expected + "'");
            index++;
            lookahead = index < input.length() ? input.charAt(index) : '\0';
            return node;
        } else {
            throw new RuntimeException("Se esperaba '" + expected + "' pero se encontró '" + lookahead + "'");
        }
    }

    /**
     * Función de entrada del parser
     */
    public TreeNode parse() {
        TreeNode.resetCounter();
        return E();
    }

    /**
     * Verifica si queda input por procesar
     */
    public boolean hasMoreInput() {
        return lookahead != '\0';
    }

    // Producciones de la gramática

    /**
     * E -> T E'
     */
    private TreeNode E() {
        ParserLogger.printStep("E()", lookahead);
        TreeNode node = new TreeNode("E");
        node.addChild(T());
        node.addChild(EPrime());
        return node;
    }

    /**
     * E' -> | T E'
     * E' -> λ
     */
    private TreeNode EPrime() {
        ParserLogger.printStep("E'()", lookahead);
        TreeNode node = new TreeNode("E'");

        if (lookahead == '|') {
            node.addChild(match('|'));
            node.addChild(T());
            node.addChild(EPrime());
        } else {
            node.addChild(new TreeNode("λ"));
        }
        return node;
    }

    /**
     * T -> F T'
     */
    private TreeNode T() {
        ParserLogger.printStep("T()", lookahead);
        TreeNode node = new TreeNode("T");
        node.addChild(F());
        node.addChild(TPrime());
        return node;
    }

    /**
     * T' -> . F T' | ε
     * T' -> λ
     */
    private TreeNode TPrime() {
        ParserLogger.printStep("T'()", lookahead);
        TreeNode node = new TreeNode("T'");

        if (lookahead == '.') {
            node.addChild(match('.'));
            node.addChild(F());
            node.addChild(TPrime());
        } else {
            node.addChild(new TreeNode("λ"));
        }
        return node;
    }

    /**
     * F -> P F'
     */
    private TreeNode F() {
        ParserLogger.printStep("F()", lookahead);
        TreeNode node = new TreeNode("F");
        node.addChild(P());
        node.addChild(FPrime());
        return node;
    }

    /**
     * F' -> *
     * F' -> λ
     */
    private TreeNode FPrime() {
        ParserLogger.printStep("F'()", lookahead);
        TreeNode node = new TreeNode("F'");

        if (lookahead == '*') {
            node.addChild(match('*'));
        } else {
            node.addChild(new TreeNode("λ"));
        }
        return node;
    }

    /**
     * P -> (E)
     * P -> L
     */
    private TreeNode P() {
        ParserLogger.printStep("P()", lookahead);
        TreeNode node = new TreeNode("P");

        if (lookahead == '(') {
            node.addChild(match('('));
            node.addChild(E());
            node.addChild(match(')'));
        } else {
            node.addChild(L());
        }
        return node;
    }

    /**
     * L -> a | b | c
     * L -> b
     * L -> c
     */
    private TreeNode L() {
        ParserLogger.printStep("L()", lookahead);
        TreeNode node = new TreeNode("L");

        if (lookahead == 'a' || lookahead == 'b' || lookahead == 'c') {
            node.addChild(match(lookahead));
        } else {
            throw new RuntimeException("Se esperaba 'a', 'b' o 'c' pero se encontró '" + lookahead + "'");
        }
        return node;
    }
}