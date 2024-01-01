/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import OLC1PROYECTO2.Arbol.Tipo;
import OLC1PROYECTO2.Arbol.Tipo.tipos;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Aritmetica extends Instrucciones {

    private Instrucciones op1;
    private Instrucciones op2;
    private Instrucciones opU;
    private Aritmetica.OperadorAritmetico operador;

    public Aritmetica(Aritmetica.OperadorAritmetico operador, int linea, int columna, Instrucciones op1, Instrucciones op2) {
        super(Tipo.tipos.INT, linea, columna);

        this.operador = operador;

        if (op2 == null) {
            this.opU = op1;
        } else {
            this.op1 = op1;
            this.op2 = op2;
        }
    }

    public String getStringOperador(Aritmetica.OperadorAritmetico op) {
        switch (op) {
            case DIV:
                return "/";
            case MENOSUNARIO:
                return "-";
            case MODULO:
                return "%";
            case MULT:
                return "*";
            case POTE:
                return "^";
            case RESTA:
                return "-";
            case SUMA:
                return "+";
            default:
                return "";
        }
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("ARITMETICA");
        if (this.opU != null) {
            nodo.agregarHijo(getStringOperador(this.operador));
            nodo.agregarHijoNodo(this.opU.getNodo());
        } else {
            nodo.agregarHijoNodo(this.op1.getNodo());
            nodo.agregarHijo(getStringOperador(this.operador));
            nodo.agregarHijoNodo(this.op2.getNodo());
        }
        return nodo;
    }

    @Override
    public Object ejecutar(Arbol tree, TablaDeSimbolos table) {
        Object izquierdo = null, derecho = null, unario = null;

        if (this.opU == null) {
            izquierdo = this.op1.ejecutar(tree, table);
            if (izquierdo instanceof Excepcion) {
                return izquierdo;
            }

            derecho = this.op2.ejecutar(tree, table);
            if (derecho instanceof Excepcion) {
                return derecho;
            }
        } else {
            unario = this.opU.ejecutar(tree, table);
            if (unario instanceof Excepcion) {
                return unario;
            }
        }
        System.out.println("eleccion: " + this.operador);
        switch (this.operador) {
            case DIV:
                break;
            case MENOSUNARIO:
                break;
            case MODULO:
                break;
            case MULT:
                break;
            case POTE:
                break;
            case RESTA:
                break;

            case SUMA:
                // SWITCH SEGÚN EL TIPO
                switch (this.op1.getTipo()) {
                    // SI EL PRIMER TIPO ES UN ENTERO
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            this.tipo = Tipo.tipos.INT;
                            System.out.println(izquierdo + " " + derecho);
                            

                           
                            return Integer.parseInt( izquierdo+"") + Integer.parseInt(derecho+"");
                        } // TIPO DECIMAL
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            return Double.parseDouble(""+ izquierdo) + Double.parseDouble(""+ derecho);
                        } // TIPO BOOLEAN
                        else if (this.op2.getTipo() == Tipo.tipos.BOOLEAN) {
                            this.tipo = Tipo.tipos.INT;

                            int suma = (Boolean) derecho ? 1 : 0;
                            return Integer.parseInt(""+ izquierdo) + suma;
                        } // TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            this.tipo = Tipo.tipos.INT;
                            return Integer.parseInt(""+ izquierdo) + (""+ derecho).charAt(0);
                        } // TIPO CADENA
                        else if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        }
                        break;
                    case DOUBLE:
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            return Double.parseDouble(""+ izquierdo) + Double.parseDouble(""+ derecho);
                        } // TIPO DECIMAL
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            return Double.parseDouble(""+ izquierdo) + Double.parseDouble(""+ derecho);
                        } // TIPO BOOLEAN
                        else if (this.op2.getTipo() == Tipo.tipos.BOOLEAN) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            double suma = (Boolean) derecho ? 1.0 : 0.0;
                            return Double.parseDouble(""+ izquierdo) + suma;
                        } // TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            return Double.parseDouble(""+ izquierdo) + (""+ derecho).charAt(0);
                        } // TIPO CADENA
                        else if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case BOOLEAN:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            this.tipo = Tipo.tipos.INT;
                            int suma = 0;
                            if ((Boolean) izquierdo) {  // Asumiendo que izquierdo es un valor booleano
                                suma = 1;
                            }
                            return Integer.parseInt(""+ derecho) + suma;
                        } // TIPO DECIMAL
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            double suma = 0;
                            if ((Boolean) izquierdo) {  // Asumiendo que izquierdo es un valor booleano
                                suma = 1.0;
                            }
                            return suma + Double.parseDouble(""+ derecho);
                        } // TIPO CADENA
                        else if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";  // Asumiendo que tanto izquierdo como derecho son cadenas
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            this.tipo = Tipo.tipos.INT;
                            return (""+ izquierdo).charAt(0) + Integer.parseInt(""+ derecho);
                        } // TIPO DECIMAL
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            this.tipo = Tipo.tipos.DOUBLE;
                            return (double) (""+ izquierdo).charAt(0) + Double.parseDouble(""+ derecho);
                        } // TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            this.tipo = Tipo.tipos.CHAR;
                            return (char) izquierdo + (char) derecho;
                        } // TIPO CADENA
                        else if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case STRING:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } // TIPO DECIMAL
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } // TIPO BOOLEAN
                        else if (this.op2.getTipo() == Tipo.tipos.BOOLEAN) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } // TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } // TIPO CADENA
                        else if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            this.tipo = Tipo.tipos.STRING;
                            return ""+ izquierdo + ""+ derecho + " ";
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo", this.linea, this.columna);
                }
                break;
            // ...
            // Other cases for different operators if exist
            // ...

            default:
                break;

        }
        return null;
    }

    public static enum OperadorAritmetico {
        SUMA,
        RESTA,
        MULT,
        DIV,
        MENOSUNARIO,
        POTE,
        MODULO
    }
}
