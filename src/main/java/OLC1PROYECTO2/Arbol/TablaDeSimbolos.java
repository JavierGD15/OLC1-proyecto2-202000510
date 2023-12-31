/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author javie
 */
public class TablaDeSimbolos {

    private String nombreEntorno; // En Java, no es común usar 'String | any'
    private HashMap<String, Simbolo> tabla;
    private TablaDeSimbolos anterior; // TABLA DE SÍMBOLOS ANTERIOR
    private Tipo tipo; // Asumiendo que la clase Tipo está definida

    public TablaDeSimbolos(TablaDeSimbolos anterior) {
        this.anterior = anterior;
        this.tabla = new HashMap<>();
        this.tipo = new Tipo(Tipo.tipos.INT); // Asumiendo que Tipos es un enum y Tipo tiene este constructor
    }

    public TablaDeSimbolos() {
        this.tabla = new HashMap<>();
    }

    public boolean setVariable(Simbolo simbolo) {
        
        for (TablaDeSimbolos e = this; e != null; e = e.getAnterior()) {
            HashMap<String, Simbolo> tablaActual = e.getTable();
            if (tablaActual != null) {
                Simbolo encontrado = tablaActual.get(simbolo.getId());
                if (encontrado != null) {
                    return false;
                }
            }
            break;
        }
       System.out.println(simbolo.toString());
        this.tabla.put(simbolo.getId(), simbolo);
        return true;
    }

    public Simbolo getVariable(String identificador) {
        for (TablaDeSimbolos e = this; e != null; e = e.getAnterior()) {
            Simbolo encontrado = e.getTable().get(identificador);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public HashMap<String, Simbolo> getTable() {
        return this.tabla;
    }

    public void setTable(HashMap<String, Simbolo> tabla) {
        this.tabla = tabla;
    }

    public TablaDeSimbolos getAnterior() {
        return this.anterior;
    }

    public void setAnterior(TablaDeSimbolos anterior) {
        this.anterior = anterior;
    }
}
