/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

import OLC1PROYECTO2.Arbol.Asignacion;
import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import OLC1PROYECTO2.Arbol.Tipo;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Decremento extends Instrucciones {

    private Instrucciones expresion;

    public Decremento(Instrucciones expresion, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.expresion = expresion;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("DECREMENTO");
        nodo.agregarHijoNodo(this.expresion.getNodo());
        nodo.agregarHijo("--");
        return nodo;
    }

    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object valor = this.expresion.ejecutar(tree, table);
        if (this.expresion.getTipo() == Tipo.tipos.INT) {
            valor = Integer.parseInt(valor.toString()) - 1;
        } else if (this.expresion.getTipo() == Tipo.tipos.DOUBLE) {
            valor = Double.parseDouble(valor.toString()) - 1;
        } else {
            new Excepcion("Semántico", "Tipo no válido para decremento", this.linea, this.columna);
        }

        try {
            if (this.expresion instanceof Variable) {
                Variable variable = (Variable) this.expresion;
                String identificador = variable.getIdentificador();
                if (identificador != null) {
                    Asignacion asig = new Asignacion(identificador, this.linea, this.columna, new Primitivo(this.expresion.getTipo(), valor,this.linea,this.columna));
                    Object ret = asig.ejecutar(tree, table);
                    if (ret instanceof Excepcion) {
                        return ret;
                    }
                }
            }
            return valor+"";
        } catch (Exception e) {
            return valor;
        }
    }
}
