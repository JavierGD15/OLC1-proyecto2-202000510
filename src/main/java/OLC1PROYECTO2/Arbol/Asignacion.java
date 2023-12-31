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
public class Asignacion extends Instrucciones {

    private Instrucciones contenido;
    private String identificador;

    public Asignacion(String nombre, int linea, int columna, Instrucciones contenido) {
        super(new Tipo(Tipo.tipos.STRING), linea, columna);
        this.contenido = contenido;
        this.identificador = nombre;
    }

   

    @Override
    public Object ejecutar( TablaDeSimbolos table) {
        Simbolo variable = table.getVariable(this.identificador);
        Object contenidoValor = this.contenido.ejecutar(table);

        // Si no hay variable con ese nombre
        if (variable == null) {
            return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);
        }

        boolean opcion = false;
        // Para parseos automáticos
        if (variable.getTipo().getTipos() == Tipo.tipos.DOUBLE && this.contenido.tipo.getTipos() == Tipo.tipos.INT) {
            opcion = true;
        }
        // Verificar coincidencia de tipos
        
        else if (variable.getTipo().getTipos() != this.contenido.tipo.getTipos()) {
            return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);
        } else {
            opcion = true;
        }

        if (!opcion) {
            return new Errores(0, "Semántico", "Error en la declaracion de los tipos", this.linea, this.columna);
        }

        variable.setValor(contenidoValor);
        return variable;
    }
}

