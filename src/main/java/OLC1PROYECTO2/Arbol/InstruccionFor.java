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

public class InstruccionFor extends Instrucciones {

    private Instrucciones condicion;
    private Instrucciones declaracion;
    private Instrucciones actualizacion;
    private LinkedList<Instrucciones> listaInstruccion;

    public InstruccionFor(Instrucciones condicion, Instrucciones declaracion, Instrucciones actualizacion, LinkedList<Instrucciones> listaInstruccion, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.condicion = condicion;
        this.declaracion = declaracion;
        this.actualizacion = actualizacion;
        this.listaInstruccion = listaInstruccion;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("FOR");
        nodo.agregarHijo("for");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.declaracion.getNodo());
        nodo.agregarHijo(";");
        nodo.agregarHijoNodo(this.condicion.getNodo());
        nodo.agregarHijo(";");
        nodo.agregarHijoNodo(this.actualizacion.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAST nodito = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccion) {
            nodito.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(nodito);
        nodo.agregarHijo("}");
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.nombreEntorno = "For";
        tree.listaTablas.add(nuevaTabla);
        nuevaTabla.setAnterior(table);

        this.declaracion.ejecutar(tree, nuevaTabla);
        Object valorCondicion = this.condicion.ejecutar(tree, nuevaTabla);

        if (this.condicion.getTipo() != Tipo.tipos.BOOLEAN) {
            new Excepcion("Semántico", "Condición de tipo incorrecto", this.linea, this.columna);
        }

        while ((Boolean) valorCondicion) {
            TablaDeSimbolos tablaInstrucciones = new TablaDeSimbolos();
            tablaInstrucciones.nombreEntorno = "Instrucciones For";
            tree.listaTablas.add(tablaInstrucciones);
            tablaInstrucciones.setAnterior(nuevaTabla);

            for (Instrucciones m : this.listaInstruccion) {
                Object result = m.ejecutar(tree, tablaInstrucciones);
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
            this.actualizacion.ejecutar(tree, nuevaTabla);
            valorCondicion = this.condicion.ejecutar(tree, nuevaTabla);
        }
        return true;
    }
}
