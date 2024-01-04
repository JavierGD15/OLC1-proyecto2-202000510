/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Estructuras;

/**
 *
 * @author javie
 */
public class GeneradorAST {
    private int contador;
    private StringBuilder grafo;

    public String graphAST(NodoAST raiz) {
        return getDot(raiz);
    }

    private String getDot(NodoAST raiz) {
        grafo = new StringBuilder();
        grafo.append("digraph {\n");
        String raizId = "n0";
        grafo.append(raizId).append("[label=\"").append(raiz.getValor().replace("\"", "\\\"")).append("\"];\n");
        contador = 1;
        recorrerAST(raizId, raiz);
        grafo.append("}");
        return grafo.toString();
    }

    private void recorrerAST(String padre, NodoAST npadre) {
        for (NodoAST hijo : npadre.getHijos()) {
            String nombreHijo = "n" + contador;
            grafo.append(nombreHijo).append("[label=\"").append(hijo.getValor().replace("\"", "\\\"")).append("\"];\n");
            grafo.append(padre).append("->").append(nombreHijo).append(";\n");
            contador++;
            recorrerAST(nombreHijo, hijo);
        }
    }
}

