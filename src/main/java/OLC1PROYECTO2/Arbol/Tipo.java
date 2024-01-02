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
public class Tipo {

    private tipos tipos;

    public Tipo(tipos tipos) {
        this.tipos = tipos;
    }

    public boolean equals(Tipo obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.tipos == obj.tipos;
    }

    public tipos getTipos() {
        return this.tipos;
    }

    public void setTipo(tipos tipo) {
        this.tipos = tipo;
    }

    public static enum tipos {
        INT,
        DOUBLE,
        BOOLEAN,
        CHAR,
        STRING,
        BREAK,
        FUNCION,
        METODO,
        RETORNO,
        CONTINUE,
        VECTOR,
        LISTA
    }
}
