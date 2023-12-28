/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Generadores;

import java.io.File;

/**
 *
 * @author javie
 */
public class GLexico {
      public static void main(String[] args) 
    {
        String path="src/main/java/OLC1PROYECTO2/Analizadores/lexico.jflex";
        generarLexer(path);
    } 
    
    public static void generarLexer(String path)
    {
        File file=new File(path);
        JFlex.Main.generate(file);
    } 
}
