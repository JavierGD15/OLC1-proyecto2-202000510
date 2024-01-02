/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

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
public class Logica extends Instrucciones {

    private Instrucciones op1;
    private Instrucciones op2;
    private Instrucciones opU;
    private Logica.OperadorLogico operador;

    public Logica(Logica.OperadorLogico operador, int fila, int columna, Instrucciones operando1, Instrucciones operando2) {
        super(Tipo.tipos.BOOLEAN, fila, columna);
        this.operador = operador;
        if (operando2 == null) {
            this.opU = operando1;
        } else {
            this.op1 = operando1;
            this.op2 = operando2;
        }
    }

    public Logica(Logica.OperadorLogico operador, int fila, int columna, Instrucciones operando1) {
        super(Tipo.tipos.BOOLEAN, fila, columna);
        this.operador = operador;

        this.opU = operando1;

    }

    public String getStringOperador(Logica.OperadorLogico op) {
        switch (op) {
            case AND:
                return "&&";
            case OR:
                return "||";
            case NOT:
                return "!";
            default:
                return "";
        }
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("LOGICA");
        if (this.opU != null) {
            nodo.agregarHijo(getStringOperador(this.operador));
            nodo.agregarHijoNodo(this.opU.getNodo());
        } else {
            nodo.agregarHijoNodo(this.op1.getNodo());
            nodo.agregarHijo(getStringOperador(this.operador));
            nodo.agregarHijoNodo(this.op2.getNodo());
        }
        return nodo;
    }

    /**
     *
     * @param tree
     * @param table
     * @return
     */
    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object izquierdo = null, derecho = null, unario = null;

        if (this.opU == null) {
            izquierdo = this.op1.ejecutar(tree, table);
            if (izquierdo instanceof Excepcion) {
                return izquierdo;
            }

            derecho = this.op2.ejecutar(tree, table);
            if (derecho instanceof Excepcion) {
                return derecho;
            }
        } else {
            unario = this.opU.ejecutar(tree, table);
            if (unario instanceof Excepcion) {
                return unario;
            }
        }

        // SWITCH SEGÚN EL OPERADOR
        switch (this.operador) {
            case AND:
                if ((this.op1.getTipo() == Tipo.tipos.BOOLEAN) && (this.op2.getTipo() == Tipo.tipos.BOOLEAN)) {
                    return (Boolean) derecho && (Boolean) izquierdo;
                } else {
                    return new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                }
            case OR:
                if ((this.op1.getTipo() == Tipo.tipos.BOOLEAN) && (this.op2.getTipo() == Tipo.tipos.BOOLEAN)) {
                    return (Boolean) derecho || (Boolean) izquierdo;
                } else {
                    return new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                }
            case NOT:
                if (this.opU.getTipo() == Tipo.tipos.BOOLEAN) {
                    return !(Boolean) unario;
                } else {
                    return new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                }
            default:
                return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
        }
    }

    public static enum OperadorLogico {
        OR,
        AND,
        NOT
    }
}
