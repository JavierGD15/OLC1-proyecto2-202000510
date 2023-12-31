/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Estructuras.Errores;

/**
 *
 * @author javie
 */
public class Declaracion extends Instrucciones {

    Tipo tipoDeclarado;
    Instrucciones contenido;
    String nombre;

    public Declaracion(String nombre, Tipo.tipos tipo, int linea, int columna, Instrucciones contenido) {
        super(new Tipo(tipo), linea, columna);
        this.nombre = nombre;
        this.tipoDeclarado = new Tipo(tipo);
        this.contenido = contenido;
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
    public Object ejecutar(TablaDeSimbolos table) {
        boolean opcion = true;
        
        if (this.contenido != null) {
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.contenido.ejecutar(table), this.linea, this.columna);
            opcion = false;
            if (this.tipoDeclarado.getTipos() == Tipo.tipos.DOUBLE && this.contenido.tipo.getTipos() == Tipo.tipos.INT) {
                opcion = true;
            } else {
                opcion = true;
            }

            if (!opcion) {
                return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);

            }

            if (table.setVariable(simbolo)) {
                return table.getVariable(this.nombre);
            } else {
                return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);
            }
        } else {
            Simbolo simbolo = new Simbolo(this.tipoDeclarado, this.nombre, this.linea, this.columna, null, null, null);
            if (table.setVariable(simbolo)) {
                return table.getVariable(this.nombre);
            } else {
                return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);
            }
        }
    }
}
