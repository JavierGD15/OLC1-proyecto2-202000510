/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OLC1PROYECTO2.Analizadores.Expresiones;

import OLC1PROYECTO2.Arbol.Instrucciones;
import OLC1PROYECTO2.Arbol.TablaDeSimbolos;
import OLC1PROYECTO2.Arbol.Tipo;
import OLC1PROYECTO2.Estructuras.Arbol;
import OLC1PROYECTO2.Estructuras.Excepcion;
import OLC1PROYECTO2.Estructuras.NodoAST;

/**
 *
 * @author javie
 */
public class Relacional extends Instrucciones {

    private Instrucciones op1;
    private Instrucciones op2;
    private Instrucciones opU;
    private Relacional.OperadorRelacional operador;

    public Relacional(Relacional.OperadorRelacional operador, int fila, int columna, Instrucciones operando1,
            Instrucciones operando2) {
        super(Tipo.tipos.BOOLEAN, fila, columna);
        this.operador = operador;
        if (operando2 == null) {
            this.opU = operando1;
        } else {
            this.op1 = operando1;
            this.op2 = operando2;
        }
    }

    public String getStringOperador(Relacional.OperadorRelacional op) {
        switch (op) {
            case IGUAL:
                return "==";
            case DIFERENCIA:
                return "!=";
            case MAYOR:
                return ">";
            case MAYORIGUAL:
                return ">=";
            case MENOR:
                return "<";
            case MENORIGUAL:
                return "<=";
            default:
                return "";
        }
    }

    @Override
    public NodoAST getNodo() {
        NodoAST nodo = new NodoAST("RELACIONAL");
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

        switch (this.operador) {
            case IGUAL:
                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") == Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") == Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") == (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") == Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") == Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") == (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) == Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") == Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) == (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case BOOLEAN:
                        // SEGUNDO TIPO BOOLEANO
                        if (this.op2.getTipo() == Tipo.tipos.BOOLEAN) {
                            return izquierdo == derecho;
                        } else {
                            return new Excepcion("Semántico", "Operandos erroneos para +", this.linea, this.columna);
                        }

                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;
            case DIFERENCIA:
                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") != Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") != Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") != (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") != Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") != Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") != (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) != Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") != Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) != (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case BOOLEAN:
                        // SEGUNDO TIPO BOOLEANO
                        if (this.op2.getTipo() == Tipo.tipos.BOOLEAN) {
                            return izquierdo != derecho;
                        } else {
                            return new Excepcion("Semántico", "Operandos erroneos para +", this.linea, this.columna);
                        }
                    case STRING:
                        // SEGUNDO TIPO CADENA
                        if (this.op2.getTipo() == Tipo.tipos.STRING) {
                            return izquierdo != derecho;
                        } else {
                            new Excepcion("Semántico", "Operandos erroneos para +", this.linea, this.columna);
                        }
                        break;
                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;
            case MAYOR:

                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") > Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") > Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") > (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") > Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") > Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") > (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) > Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") > Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) > (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;

                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;
            case MAYORIGUAL:
                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") >= Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") >= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") >= (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") >= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") >= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") >= (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) >= Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") >= Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) >= (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;

                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;
            case MENOR:
                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") < Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") < Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") < (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") < Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") < Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") < (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) < Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") < Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) < (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;

                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;
            case MENORIGUAL:
                switch (this.op1.getTipo()) {
                    case INT:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Integer.parseInt(izquierdo + "") <= Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") <= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Integer.parseInt(izquierdo + "") <= (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }

                        break;
                    case DOUBLE:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return Double.parseDouble(izquierdo + "") <= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble(izquierdo + "") <= Double.parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return Double.parseDouble(izquierdo + "") <= (double) (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;
                    case CHAR:
                        // SEGUNDO TIPO ENTERO
                        if (this.op2.getTipo() == Tipo.tipos.INT) {
                            return (izquierdo + "").charAt(0) <= Integer.parseInt(derecho + "");
                        } // SEGUNDO TIPO DOUBLE
                        else if (this.op2.getTipo() == Tipo.tipos.DOUBLE) {
                            return Double.parseDouble((izquierdo + "").charAt(0) + "") <= Double
                                    .parseDouble(derecho + "");
                        } // SEGUNDO TIPO CARACTER
                        else if (this.op2.getTipo() == Tipo.tipos.CHAR) {
                            return (izquierdo + "").charAt(0) <= (derecho + "").charAt(0);
                        } else {
                            new Excepcion("Semántico", "Operandos erróneos para +", this.linea, this.columna);
                        }
                        break;

                    default:
                        return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
                }
                break;

            default:
                return new Excepcion("Semántico", "Tipo de Operación Erróneo.", this.linea, this.columna);
        }

        return null;
    }

    public static enum OperadorRelacional {
        IGUAL,
        DIFERENCIA,
        MENOR,
        MAYOR,
        MAYORIGUAL,
        MENORIGUAL
    }
}
