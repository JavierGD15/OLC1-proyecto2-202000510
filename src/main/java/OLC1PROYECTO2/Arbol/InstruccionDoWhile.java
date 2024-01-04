/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

/**
 *
 * @author javie
 */
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;
import java.util.LinkedList;

public class InstruccionDoWhile extends Instrucciones {
    private Instrucciones condicion;
    private LinkedList<Instrucciones> listaInstruccion;

    public InstruccionDoWhile(Instrucciones condicion, LinkedList<Instrucciones> listaInstruccion, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.condicion = condicion;
        this.listaInstruccion = listaInstruccion;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("DO-WHILE");
        nodo.agregarHijo("do");
        nodo.agregarHijo("{");
        NodoAST nodito = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccion) {
            nodito.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(nodito);
        nodo.agregarHijo("}");
        nodo.agregarHijo("while");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.condicion.getNodo());
        nodo.agregarHijo(")");
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        this.condicion.ejecutar(tree, table);

        if (this.condicion.getTipo() != Tipo.tipos.BOOLEAN) {
             new Excepcion("Semántico", "Condición de tipo incorrecto", this.linea, this.columna);
        }

        do {
            TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
            nuevaTabla.nombreEntorno ="Do-While";
            tree.listaTablas.add(nuevaTabla);
            nuevaTabla.setAnterior(table);

            for (Instrucciones m : this.listaInstruccion) {
                Object result = m.ejecutar(tree, nuevaTabla);
                if (result instanceof InstruccionBreak) {
                    return result;
                } else if (result instanceof InstruccionContinue) {
                    break;
                } else if (result instanceof InstruccionReturn) {
                    return result;
                } else if (result instanceof Excepcion) {
                    tree.errores.add((Excepcion)result);
                    tree.updateConsola(((Excepcion) result).toString());
                }
            }
        } while ((Boolean) this.condicion.ejecutar(tree, table));

        return true;
    }
}

