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

public class InstruccionCase extends Instrucciones {

    private Instrucciones condicion;
    private LinkedList<Instrucciones> listaInstruccion;
    private String valorCondicion;

    public InstruccionCase(Instrucciones expresion, LinkedList<Instrucciones> lista, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.condicion = expresion;
        this.listaInstruccion = lista;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("CASE");
        nodo.agregarHijo("case");
        nodo.agregarHijoNodo(this.condicion.getNodo());
        nodo.agregarHijo(":");
        NodoAST nodito = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccion) {
            nodito.agregarHijoNodo(m.getNodo());
        }
        nodo.agregarHijoNodo(nodito);
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.nombreEntorno = "Case";
        tree.listaTablas.add(nuevaTabla);
        nuevaTabla.setAnterior(table);

        Object result2 = condicion.ejecutar(tree, table);
        if (!this.valorCondicion.equals(result2)) {
            return false;
        }
        for (Instrucciones m : this.listaInstruccion) {
            Object n = m;
            if (n instanceof Excepcion) {
                tree.errores.add((Excepcion) n);
                tree.updateConsola(m.toString());
                continue;
            }

            Object result = m.ejecutar(tree, nuevaTabla);

            if (m.getTipo() == Tipo.tipos.BREAK) {
                return false;
            }

            if (result instanceof Excepcion) {
                tree.errores.add((Excepcion) result);
                tree.updateConsola(((Excepcion) result).toString());
            }
        }
        return true;
    }

    public Boolean getCondicion(String result2) {
        if (!this.valorCondicion.equals(result2)) {
            return false;
        }
        return true;
    }

    public void setValorCondicion(String valorCondicion) {
        this.valorCondicion = valorCondicion;
    }
}
