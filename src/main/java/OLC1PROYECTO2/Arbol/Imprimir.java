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
public class Imprimir extends Instrucciones{
 
    private final Instrucciones contenido;
 
    public Imprimir(Instrucciones contenido) {
        this.contenido = contenido;
    }

    @Override
    public Object ejecutar(TablaDeSimbolos ts) {
        if (menu_principal.jTextArea2.getText().isEmpty()) {
            menu_principal.jTextArea2.setText(contenido.ejecutar(ts).toString()+"\n");
        }else{
        menu_principal.jTextArea2.setText(menu_principal.jTextArea2.getText()+"\n"+contenido.ejecutar(ts).toString()+"\n");
        }
        System.out.println(contenido.ejecutar(ts).toString()+"\n");
        return null;
    }
    
}
