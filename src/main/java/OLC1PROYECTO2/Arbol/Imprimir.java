/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Arbol;

import OLC1PROYECTO2.Interfaz.menu_principal;

/**
 *
 * @author javie
 */
public class Imprimir extends Instrucciones {

    private Instrucciones contenido;

    public Imprimir(Instrucciones contenido, int linea, int columna) {
        super(new Tipo(Tipo.tipos.STRING), linea, columna);
        this.contenido = contenido;
    }

    @Override
    public Object ejecutar(TablaDeSimbolos ts) {
        if (menu_principal.jTextArea2.getText().isEmpty()) {
            Object value = this.contenido.ejecutar(ts); //OBTIENE EL VALOR

           

            menu_principal.jTextArea2.setText(value + "\n");
        } else {
            Object value = this.contenido.ejecutar(ts); //OBTIENE EL VALOR
            menu_principal.jTextArea2.setText(menu_principal.jTextArea2.getText() + "\n" + value + "\n");
        }
        System.out.println(contenido.ejecutar(ts).toString() + "\n");
        return null;
    }

}
