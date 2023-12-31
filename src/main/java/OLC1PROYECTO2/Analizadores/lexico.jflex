package OLC1PROYECTO2.Analizadores;
import OLC1PROYECTO2.Estructuras.Errores;
import java_cup.runtime.*;
import java.util.ArrayList;
import java.util.List;


%% 
%class Lexico
%public
%line
%char
%cup 
%unicode
%ignorecase
%{
    public List<Errores> Errors = new ArrayList<>();
    public int cont = 1;
%}

%init{ 
    yyline = 1;
    yychar = 1;
%init} 
BLANCOS=[ \r\t]+
D = [0-9]
L = [a-zA-Z]
decimal = {D}+("."[  |0-9]+)?

//4.4 SECUENCIAS DE ESCAPE
TEXTO = \"(\\t|\\n|\\'|\\\"|\\\\|[^\\\"\n])*\"  // Para cadenas que incluyen escapes especiales y comillas dobles
CARACTERES = [\']((\\u[0-9A-Fa-f]{4})|[^\\\'\n]|(\\[\'\"tnr]))[\']// Para caracteres literales
            
ESPECIAL = (\\n|\\\'|\\\")
IDENTIFICADOR = {L}({L}|{D})*

//4.2 COMENTARIO
COMENTARIO    =  ("\/*"([^><]|[^!]">"|"!"[^>]|[^<]"!"|"<"[^!])*"*\/")|(\/\/(.*)*)
%%


{COMENTARIO} {return new Symbol(sym.COMENTARIO,yyline,yychar, yytext());}

//4.3 TIPO DE DATO
"ENTERO" {return new Symbol(sym.INT,yyline,yychar, yytext());}
"DOBLE" {return new Symbol(sym.DOUBLE,yyline,yychar, yytext());}
"BINARIO" {return new Symbol(sym.BOOLEAN,yyline,yychar, yytext());}
"CARACTER" {return new Symbol(sym.CHAR,yyline,yychar, yytext());}
"CADENA" {return new Symbol(sym.STRING,yyline,yychar, yytext());}


//Operadores Relacionales 
"==" {return new Symbol(sym.IGUAL,yyline,yychar, yytext());}
"!=" {return new Symbol(sym.DIFERENCIA,yyline,yychar, yytext());}
"<" {return new Symbol(sym.MENOR,yyline,yychar, yytext());}
"<=" {return new Symbol(sym.MENORIGUAL,yyline,yychar, yytext());}
">" {return new Symbol(sym.MAYOR,yyline,yychar, yytext());}
">=" {return new Symbol(sym.MAYORIGUAL,yyline,yychar, yytext());}

//Operadores Lógicos 
"||" {return new Symbol(sym.OR,yyline,yychar, yytext());}
"&&" {return new Symbol(sym.AND,yyline,yychar, yytext());}
"!" {return new Symbol(sym.NOT,yyline,yychar, yytext());}

//4.5 Operadores Aritméticos
"+" {return new Symbol(sym.SUMA,yyline,yychar, yytext());} //SUMA
"-" {return new Symbol(sym.RESTA,yyline,yychar, yytext());} //RESTA
"*" {return new Symbol(sym.MULT,yyline,yychar, yytext());} //MULTIPLICACION
"\/" {return new Symbol(sym.DIV,yyline,yychar, yytext());} //DIVISION
"^" {return new Symbol(sym.POTE,yyline,yychar, yytext());} //POTENCIA
"%" {return new Symbol(sym.MODULO,yyline,yychar, yytext());} //MODULO

"=" {return new Symbol(sym.ASIGNACION,yyline,yychar, yytext());}
":" {return new Symbol(sym.DOSPT,yyline,yychar, yytext());}
("%%"|("%%"[\n])) {return new Symbol(sym.FIN,yyline,yychar, yytext());} 

(";"|(";"[\n])) {return new Symbol(sym.PTCOMA,yyline,yychar, yytext());}
"." {return new Symbol(sym.PT,yyline,yychar, yytext());}
"," {return new Symbol(sym.COMA,yyline,yychar, yytext());}
"{" {return new Symbol(sym.LLAVEIZ,yyline,yychar, yytext());} 
"}" {return new Symbol(sym.LLAVEDER,yyline,yychar, yytext());} 
"(" {return new Symbol(sym.PARIZQ,yyline,yychar, yytext());} 
")" {return new Symbol(sym.PARDER,yyline,yychar, yytext());} 
"?" {return new Symbol(sym.INTERR,yyline,yychar, yytext());} 

//acciones
"imprimir" {return new Symbol(sym.RIMPRIMIR,yyline,yychar, yytext());} 

\n {yychar=1;}

{D} {return new Symbol(sym.DIGITO,yyline,yychar, yytext());}
{L} {return new Symbol(sym.LETRA,yyline,yychar, yytext());}
{decimal} {return new Symbol(sym.DECIMAL,yyline,yychar, yytext());}
{IDENTIFICADOR} {return new Symbol(sym.IDENTIFICADOR, yyline, yychar, yytext());}
"\" \"" {return new Symbol(sym.ESPACIO,yyline,yychar, yytext());}
{TEXTO} {return new Symbol(sym.PARRAF,yyline,yychar, yytext());}
{CARACTERES} {return new Symbol(sym.CARACTERES,yyline,yychar, yytext());}
{BLANCOS} {}
{ESPECIAL} {return new Symbol(sym.ESPECIAL,yyline,yychar, yytext());}



. {
    Errores err = new Errores(cont, "Léxico", "El caracter "+yytext()+" no pertenece al lenguaje", yyline, yychar);
    cont++;
    System.out.println(err.toString());
    Errors.add(err);
}