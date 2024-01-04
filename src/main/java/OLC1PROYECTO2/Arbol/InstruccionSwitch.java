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

public class InstruccionSwitch extends Instrucciones {

    private Instrucciones expresionEvaluada;
    private LinkedList<InstruccionCase> listaCase;
    private LinkedList<Instrucciones> defaultInstrucciones;

    public InstruccionSwitch(Instrucciones expresion, LinkedList<InstruccionCase> lista, int linea, int columna, LinkedList<Instrucciones> defaul) {
        super(Tipo.tipos.STRING, linea, columna);
        this.expresionEvaluada = expresion;
        this.listaCase = lista;
        this.defaultInstrucciones = defaul != null ? defaul : new LinkedList<>();
    }

    public InstruccionSwitch(Instrucciones expresion, LinkedList<InstruccionCase> lista, int linea, int columna) {
        super(Tipo.tipos.STRING, linea, columna);
        this.expresionEvaluada = expresion;
        this.listaCase = lista;
    }

    public InstruccionSwitch(int linea, int columna, LinkedList<Instrucciones> defaul) {
        super(Tipo.tipos.STRING, linea, columna);
        this.defaultInstrucciones = defaul != null ? defaul : new LinkedList<>();
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("SWITCH");
        nodo.agregarHijo("switch");
        nodo.agregarHijo("(");
        nodo.agregarHijoNodo(this.expresionEvaluada.getNodo());
        nodo.agregarHijo(")");
        nodo.agregarHijo(":");
        if (this.listaCase != null) {
            NodoAST nodito = new NodoAST("CASES");
            for (InstruccionCase m : this.listaCase) {
                nodito.agregarHijoNodo(m.getNodo());
            }
            nodo.agregarHijoNodo(nodito);
        }
        if (this.defaultInstrucciones != null) {
            NodoAST nod = new NodoAST("DEFAULT");
            for (Instrucciones m : this.defaultInstrucciones) {
                nod.agregarHijoNodo(m.getNodo());
            }
            nodo.agregarHijoNodo(nod);
        }
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object valorEvaluar = this.expresionEvaluada.ejecutar(tree, table);
        LinkedList<Boolean> condicionesBool = new LinkedList<>();

        for (InstruccionCase caso : this.listaCase) {
            caso.setValorCondicion(valorEvaluar + "");
            Object valorCaso = caso.ejecutar(tree, table);
            condicionesBool.add(caso.getCondicion(valorEvaluar + ""));
            if (valorCaso.equals(valorEvaluar)) {
                Object resultadoCaso = caso.ejecutar(tree, table);
                if (resultadoCaso instanceof InstruccionBreak) {
                    return true;
                }
            }
        }
        Boolean dato = false;
        for (Boolean boolean1 : condicionesBool) {
            if (boolean1) {
                dato = true;
                break;
            }
        }

        if (!dato) {
            if (this.defaultInstrucciones != null) {
                interpretarNuevoEntorno(tree, table, this.defaultInstrucciones);
            }
        }

        return true;
    }

    private void interpretarNuevoEntorno(Arbol tree, TablaDeSimbolos table, LinkedList<Instrucciones> instrucciones) {
        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.nombreEntorno = "Switch";
        tree.listaTablas.add(nuevaTabla);
        nuevaTabla.setAnterior(table);

        for (Instrucciones Instrucciones : instrucciones) {
            Object resultado = Instrucciones.ejecutar(tree, nuevaTabla);
            if (resultado instanceof Excepcion) {
                tree.errores.add((Excepcion) resultado);
                tree.updateConsola(((Excepcion) resultado).toString());
            }
        }
    }
}
