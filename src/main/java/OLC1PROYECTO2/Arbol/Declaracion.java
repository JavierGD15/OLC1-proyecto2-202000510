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

    private Tipo.tipos tipoDeclarado;
    private Instrucciones contenido;
    private String nombre;

    public Declaracion(String nombre, Tipo.tipos tipoDeclarado, int linea, int columna, Instrucciones contenido) {
        super(tipoDeclarado, linea, columna);       
        this.nombre = nombre;        
        this.tipoDeclarado = tipoDeclarado;
        this.contenido = contenido;
        
    }

    public Declaracion(String nombre, Tipo.tipos tipoDeclarado, int linea, int columna) {
        
        super(tipoDeclarado, linea, columna);
        System.out.println("entra en el constru 2");
        this.nombre = nombre;
        this.tipoDeclarado = tipoDeclarado;
       
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

    public void setContenido(Instrucciones contenido) {
        this.contenido = contenido;
    }

   

    @Override
    public NodoAST getNodo() {
        
        NodoAST nodo = new NodoAST("DECLARACION VARIABLE");
        nodo.agregarHijo(this.getTipoString(this.tipoDeclarado));
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
        System.out.println("entra en el ejecutar");
        // Si hay contenido se verifica que este coincida con el tipo declarado
        if (this.contenido != null) {
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.linea, this.columna, this.contenido.ejecutar(tree, table));
            opcion = false;
            System.out.println("EL simbolo"+ simbolo.getId());
            // Para parseos automáticos
            if (this.tipoDeclarado == Tipo.tipos.DOUBLE && this.contenido.getTipo() == Tipo.tipos.INT) {
                opcion = true;
            } // Si no se requieren parseos verificar que coincidan los tipos
            else {
                if (this.tipoDeclarado != this.contenido.getTipo()) {
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
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.linea, this.columna);
            if (table.setVariable(simbolo)) {
                return table.getVariable(this.nombre);
            } else {
                new Excepcion("Semántico", "El identificador de la variable ya existe", this.linea, this.columna);
            }
        }
        return null;
    }

}
