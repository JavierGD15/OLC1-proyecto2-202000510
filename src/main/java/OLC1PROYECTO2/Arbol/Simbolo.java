/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools  Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

/**
 *
 * @author javie
 */
public class Simbolo {

    private Tipo tipo;
    private String id;
    private Object valor; // En Java, 'any' se traduce mejor como 'Object'
    private Integer parametros; // Usamos Integer para permitir null
    private int linea;
    private int columna;
    private Tipo listaovector; // Suponiendo que listaovector puede ser nulo

    public Simbolo(Tipo tipo, String id, int linea, int columna, Object valor, Integer parametros, Tipo listavector) {
        this.linea = linea;
        this.columna = columna;
        this.tipo = tipo;
        this.id = id;

        if (valor != null) {
            this.valor = valor;
        } else {
            // Establecer valor por defecto según el tipo
            switch (this.tipo.getTipos()) {
                case INT:
                    this.valor = 0;
                    break;
                case DOUBLE:
                    this.valor = 0.0;
                    break;
                case BOOLEAN:
                    this.valor = false;
                    break;
                case CHAR:
                    this.valor = '\u0000'; // Carácter nulo
                    break;
                case STRING:
                    this.valor = "";
                    break;
                default:
                    this.valor = null; // o cualquier valor por defecto
                    break;
            }
        }

        this.parametros = parametros; // Java maneja 'null' directamente
        this.listaovector = listavector; // Igual para listaovector
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Integer getParametros() {
        return parametros;
    }

    public void setParametros(Integer parametros) {
        this.parametros = parametros;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Tipo getListaovector() {
        return listaovector;
    }

    public void setListaovector(Tipo listaovector) {
        this.listaovector = listaovector;
    }
}