/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Declaracion extends Instrucciones {

    private Tipo tipoDeclarado;
    private Instrucciones contenido;
    private String nombre;

    public Declaracion(String nombre, Tipo.tipos tipoDeclarado, int linea, int columna, Instrucciones contenido) {
        super(tipoDeclarado, linea, columna);
        this.nombre = nombre;
        this.tipoDeclarado.setTipo(tipoDeclarado);
        this.contenido = contenido;

    }

    public Declaracion(String nombre, Tipo.tipos tipoDeclarado, int linea, int columna) {
        super(tipoDeclarado, linea, columna);
        this.nombre = nombre;
        this.tipoDeclarado.setTipo(tipoDeclarado);

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
        NodoAST nodo = new NodoAST("DECLARACION VARIABLE");
        nodo.agregarHijo(this.getTipoString(this.tipoDeclarado.getTipos()));
        nodo.agregarHijo(this.nombre);
        if (this.contenido != null) {
            nodo.agregarHijo("=");
            if (this.contenido instanceof Instrucciones) {
                nodo.agregarHijoNodo(this.contenido.getNodo());
            } else {
                nodo.agregarHijo("Error");
            }
        }
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        // VERIFICACION DE QUE LOS TIPOS COINCIDAN
        boolean opcion = true;

        // Si hay contenido se verifica que este coincida con el tipo declarado
        if (this.contenido != null) {
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.linea, this.columna, this.contenido.ejecutar(tree, table), null, null);
            opcion = false;

            // Para parseos automáticos
            if (this.tipoDeclarado.getTipos() == Tipo.tipos.DOUBLE && this.contenido.getTipo() == Tipo.tipos.INT) {
                opcion = true;
            } // Si no se requieren parseos verificar que coincidan los tipos
            else {
                if (this.tipoDeclarado.getTipos() != this.contenido.getTipo()) {
                    new Excepcion("Semántico", "Error en la declaración de los tipos", this.linea, this.columna);
                } else {
                    opcion = true;
                }
            }

            if (!opcion) {
                new Excepcion("Semántico", "Error en la declaración de los tipos", this.linea, this.columna);
            }

            if (table.setVariable(simbolo)) {
                return table.getVariable(this.nombre);
            } else {
                new Excepcion("Semántico", "El identificador de la variable ya existe", this.linea, this.columna);
            }
        } else {
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.linea, this.columna, null, null, null);
            if (table.setVariable(simbolo)) {
                return table.getVariable(this.nombre);
            } else {
                new Excepcion("Semántico", "El identificador de la variable ya existe", this.linea, this.columna);
            }
        }
        return null;
    }

}
