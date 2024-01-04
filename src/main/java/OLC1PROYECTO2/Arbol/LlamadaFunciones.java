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
import OLC1PROYECTO2.Analizadores.Expresiones.Primitivo;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;
import java.util.LinkedList;

public class LlamadaFunciones extends Instrucciones {

    private LinkedList<Instrucciones> listaParametros;
    private String identificador;

    public LlamadaFunciones(String identificador, int linea, int columna, LinkedList<Instrucciones> listaParametros) {
        super(Tipo.tipos.METODO, linea, columna);
        this.identificador = identificador;
        this.listaParametros = listaParametros != null ? listaParametros : new LinkedList<>();
    }
    
    public LlamadaFunciones(String identificador, int linea, int columna) {
        super(Tipo.tipos.METODO, linea, columna);
        this.identificador = identificador;
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("LLAMADA METODO/FUNCION");
        nodo.agregarHijo(this.identificador);
        nodo.agregarHijo("(");
        if (this.listaParametros != null) {
            NodoAST nodito = new NodoAST("PARAMETROS");
            for (Instrucciones m : this.listaParametros) {
                nodito.agregarHijoNodo(m.getNodo());
            }
            nodo.agregarHijoNodo(nodito);
        }
        nodo.agregarHijo(")");
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Simbolo metodo = table.getVariable(this.identificador);
        if (metodo == null) {
            new Excepcion("Semántico", "Referencia a un método o función no declarado", this.linea, this.columna);
        }

        if (metodo.getTipo() != Tipo.tipos.METODO) {
            this.tipo = metodo.getTipo();
        }

        if (metodo.getParametros() != null) {
            if (this.listaParametros != null) {
                if (metodo.getParametros() != this.listaParametros.size()) {
                    new Excepcion("Semántico", "El número de parámetros no coincide", this.linea, this.columna);
                }
            } else {
                new Excepcion("Semántico", "Se esperaban parámetros para esta función", this.linea, this.columna);
            }
        }

        LinkedList<Instrucciones> listaParInterpretados = new LinkedList<>();
        if (this.listaParametros != null) {
            for (Instrucciones m : this.listaParametros) {
                Object res = m.ejecutar(tree, table);
                Primitivo primitivo = new Primitivo(m.getTipo(), res, this.linea, this.columna);
                listaParInterpretados.add(primitivo);
            }
        }

        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.nombreEntorno = "Funcion" + this.identificador;
        tree.listaTablas.add(nuevaTabla);
        nuevaTabla.setAnterior(table);

        int ejecuciones = 0;
        for (Object m : (LinkedList<Object>) metodo.getValor()) {
            if (m instanceof Excepcion) {
                tree.errores.add((Excepcion)m);
                tree.updateConsola(m.toString());
                continue;
            }
            m = (Instrucciones) m;

            Object result;
            if (metodo.getParametros() != null && ejecuciones < metodo.getParametros()) {
                Declaracion dec = (Declaracion) m;
                dec.setContenido(listaParInterpretados.get(ejecuciones));
                result = dec.ejecutar(tree, nuevaTabla);
            } else {
                Instrucciones n = (Instrucciones) m;
                result = n.ejecutar(tree, nuevaTabla);
            }

            if (result instanceof InstruccionReturn) {
                if (result != null) {
                    Object retornofinal = ((InstruccionReturn) result).getRetorno().ejecutar(tree, nuevaTabla);
                    return retornofinal;
                }
                return result;
            }

            if (result instanceof Excepcion) {
                tree.errores.add((Excepcion) result);
                tree.updateConsola(((Excepcion) result).toString());
            }
            ejecuciones++;
        }
        return this;
    }
}
