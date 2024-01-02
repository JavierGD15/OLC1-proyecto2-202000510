/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.Simbolo;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import OLC1PROYECTO2.Arbol.Tipo;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Variable extends Instrucciones {

    private String identificador;

    public Variable(int fila, int columna, String identificador) {
        super(Tipo.tipos.STRING, fila, columna);
        this.identificador = identificador;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("VARIABLE");
        nodo.agregarHijo(this.identificador);
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Simbolo variable = table.getVariable(this.identificador);

        if (variable != null) {
            this.tipo = variable.getTipo();
            // Suponiendo que 'VectorLista' es un campo de la clase, y 'listaovector' es un campo de 'Simbolo'
            if (variable.getListaovector() != null) {
                this.vectorLista = variable.getListaovector();
            }
            return variable.getValor();
        } else {
            new Excepcion("Semántico", "Referencia a una variable no declarada", this.linea, this.columna);
        }
        return null;
    }
}
