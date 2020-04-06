// Grammaire du langage Exp2, poly page 6
// // A. GRAZON - V.MASSON
// grammaire fournie aux étudiants qui doivent y ajouter les appels à Ptgen.pt(k)

// attention l'analyse est poursuivie après erreur si l'on supprime la clause rulecatch

grammar Exp2;

options {
  language=Java; 
  k=1;
 }

@header {           
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
} 

// partie syntaxique :  description de la grammaire //


@members {
// variables globales et methodes utiles à placer ici
}
// la directive rulecatch permet d'interrompre l'analyse à la premiere erreur de syntaxe
@rulecatch {
catch (RecognitionException e) {reportError (e) ; throw e ; }}


unite  :    exp {System.out.println("fin analyse syntaxique");}  EOF
  ;
  
exp   : (terme) 
        ( { PtGen2.pt(3); } '+' terme 
        | { PtGen2.pt(4); } '-' terme
        )*
  ;
  
terme  : (primaire) 
        ( { PtGen2.pt(5); } '*' primaire 
        | { PtGen2.pt(6); } 'div' primaire 
        )*
  ;
  
primaire: nbentier { PtGen2.pt(1); }
  | ident { PtGen2.pt(2); }
  | '(' { PtGen2.pt(7); } exp ')' { PtGen2.pt(8); }
  ;

// partie lexicale  //
// attention : ANTLR n'autorise pas certains traitements sur les unites lexicales, 
// il est alors necessaire de passer par un non-terminal intermediaire 
// exemple : pour l'unite lexicale INT, le non-terminal nbentier a du etre introduit
       
nbentier  :   INT { PtGen2.valNb = Integer.parseInt($INT.text);};
ident : ID  { PtGen2.idLu = $ID.text; } ; 
     
// zone purement lexicale //

INT :   '0'..'9'+ ;
ID  :   ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z')* ; 
WS  :   (' '|'\t' | '\n' |'\r')+ {skip();} ; // définition des "espaces"