/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;
import OLC1PROYECTO2.Interfaz.menu_principal;

/**
 *
 * @author javie
 */
public class Imprimir extends Instrucciones {

    private Instrucciones expresion;

    public Imprimir(Instrucciones expresion, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object value = this.expresion.ejecutar(tree, table); //OBTIENE EL VALOR

        if (value instanceof Excepcion) {
            return value;
        }
        if (value instanceof LlamadaFunciones) {
            return new Excepcion("Semántico", "Retorno vacío", this.linea, this.columna);
        }

        tree.updateConsola(value.toString());
        if (menu_principal.jTextArea2.getText().isEmpty()) {

            menu_principal.jTextArea2.setText(value.toString() + "\n");
        } else {

            menu_principal.jTextArea2.setText(menu_principal.jTextArea2.getText() + "\n" + value.toString() + "\n");
        }
        return null;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("PRINT");
        nodo.agregarHijo("print");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.expresion.getNodo());
        nodo.agregarHijo(")");
        System.out.println(nodo);
        return nodo;
    }

}
