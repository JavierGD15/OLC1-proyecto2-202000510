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

public class DeclaracionFunciones extends Instrucciones {

    private LinkedList<Instrucciones> listaInstruccion;
    private LinkedList<Instrucciones> listaParametros;
    private String identificador;
    private Tipo.tipos tipoDeclarado;

    public DeclaracionFunciones(Tipo.tipos tipo, String identificador, LinkedList<Instrucciones> listaInstruccion, int linea, int columna, LinkedList<Instrucciones> listaParametros) {
        super(Tipo.tipos.FUNCION, linea, columna);
        this.identificador = identificador;
        this.listaParametros = listaParametros;
        this.listaInstruccion = listaInstruccion;
        this.tipoDeclarado = tipo;
    }

    public DeclaracionFunciones(Tipo.tipos tipo, String identificador, LinkedList<Instrucciones> listaInstruccion, int linea, int columna) {
        super(Tipo.tipos.FUNCION, linea, columna);
        this.identificador = identificador;
        this.listaParametros = new LinkedList<>();
        this.listaInstruccion = listaInstruccion;
        this.tipoDeclarado = tipo;
    }

    public String getTipoString(Tipo.tipos tipo) {
        switch (tipo) {
            case BOOLEAN:
                return "boolean";
            case CHAR:
                return "char";
            case DOUBLE:
                return "double";
            case INT:
                return "int";
            case STRING:
                return "string";
            default:
                return "TipoError";
        }
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("DECLARAR FUNCION");
        nodo.agregarHijo(this.getTipoString(this.tipoDeclarado));
        nodo.agregarHijo(this.identificador);
        nodo.agregarHijo("(");
        if (this.listaParametros != null) {
            NodoAST nodito = new NodoAST("PARAMETROS");
            for (Instrucciones m : this.listaParametros) {
                if (m instanceof Instrucciones) {
                    nodito.agregarHijoNodo(m.getNodo());
                } else {
                    nodito.agregarHijo("Error");
                }
            }
            nodo.agregarHijoNodo(nodito);
        }
        nodo.agregarHijo(")");
        nodo.agregarHijo("{");
        NodoAST nodito = new NodoAST("INSTRUCCIONES");
        for (Instrucciones m : this.listaInstruccion) {
            if (m instanceof Instrucciones) {
                nodito.agregarHijoNodo(m.getNodo());
            } else {
                nodito.agregarHijo("Error");
            }
        }
        nodo.agregarHijoNodo(nodito);
        nodo.agregarHijo("}");
        return nodo;
    }
    // Métodos getTipoString y getNodo se omiten para enfocarse en interpretar

    @Override

    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        LinkedList<Instrucciones> listaAguardar = new LinkedList<>();
        int numeroParametros = 0;
        if (this.listaParametros != null) {
            listaAguardar.addAll(this.listaParametros);
            numeroParametros = this.listaParametros.size();
        }
        listaAguardar.addAll(this.listaInstruccion);

        // Creación de un nuevo árbol para el entorno de la función
        Arbol ast = new Arbol(listaAguardar);

        // Creación de una nueva tabla de símbolos para el entorno de la función
        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();
        nuevaTabla.setAnterior(table);
        ast.setGlobal(nuevaTabla);

        // Verificación de que haya un retorno
        boolean hayRetorno = false;

        for (Instrucciones m : ast.getInstrucciones()) {
            if (m != null) {
                ast.updateConsola(m.toString());
                return m;
            }

            Object result = m.ejecutar(ast, nuevaTabla);

            // Verificación para retorno de la función
            if (m.getTipo() == Tipo.tipos.RETORNO) {
                InstruccionReturn retor = (InstruccionReturn) m;
                if (result != null) {
                    retor.ejecutar(tree, table);
                    if (retor.getTipo() == this.tipoDeclarado) {
                        hayRetorno = true;
                    } else {
                        // Manejar error de tipo de retorno incorrecto
                    }
                } else {
                    // Manejar error de retorno vacío
                }
            }

            if (result instanceof Excepcion) {
                return result;
            }
        }

        if (!hayRetorno) {
            // Manejar error de función sin retorno
        }

        // Si llega hasta aquí sin problemas, se procede a guardar la función en el árbol
        Simbolo metodo = new Simbolo(this.tipoDeclarado, this.identificador, this.linea, this.columna, listaAguardar, numeroParametros);
        if (table.setVariable(metodo)) {
            return table.getVariable(this.identificador);
        } else {
            new Excepcion("Semántico", "El identificador del método ya existe", this.linea, this.columna);
        }
        return null;
    }

}
