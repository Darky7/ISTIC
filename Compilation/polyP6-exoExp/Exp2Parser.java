// $ANTLR 3.5.2 Exp2.g 2019-02-06 16:09:58
           
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Exp2Parser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "WS", "'('", "')'", 
		"'*'", "'+'", "'-'", "'div'"
	};
	public static final int EOF=-1;
	public static final int T__7=7;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int ID=4;
	public static final int INT=5;
	public static final int WS=6;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public Exp2Parser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public Exp2Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return Exp2Parser.tokenNames; }
	@Override public String getGrammarFileName() { return "Exp2.g"; }


	// variables globales et methodes utiles à placer ici



	// $ANTLR start "unite"
	// Exp2.g:31:1: unite : exp EOF ;
	public final void unite() throws RecognitionException {
		try {
			// Exp2.g:31:8: ( exp EOF )
			// Exp2.g:31:13: exp EOF
			{
			pushFollow(FOLLOW_exp_in_unite64);
			exp();
			state._fsp--;

			System.out.println("fin analyse syntaxique");
			match(input,EOF,FOLLOW_EOF_in_unite69); 
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unite"



	// $ANTLR start "exp"
	// Exp2.g:34:1: exp : ( terme ) ( '+' terme | '-' terme )* ;
	public final void exp() throws RecognitionException {
		try {
			// Exp2.g:34:7: ( ( terme ) ( '+' terme | '-' terme )* )
			// Exp2.g:34:9: ( terme ) ( '+' terme | '-' terme )*
			{
			// Exp2.g:34:9: ( terme )
			// Exp2.g:34:10: terme
			{
			pushFollow(FOLLOW_terme_in_exp85);
			terme();
			state._fsp--;

			}

			// Exp2.g:35:9: ( '+' terme | '-' terme )*
			loop1:
			while (true) {
				int alt1=3;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==10) ) {
					alt1=1;
				}
				else if ( (LA1_0==11) ) {
					alt1=2;
				}

				switch (alt1) {
				case 1 :
					// Exp2.g:35:11: '+' terme
					{
					 PtGen2.pt(3); 
					match(input,10,FOLLOW_10_in_exp101); 
					pushFollow(FOLLOW_terme_in_exp103);
					terme();
					state._fsp--;

					}
					break;
				case 2 :
					// Exp2.g:36:11: '-' terme
					{
					 PtGen2.pt(4); 
					match(input,11,FOLLOW_11_in_exp118); 
					pushFollow(FOLLOW_terme_in_exp120);
					terme();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp"



	// $ANTLR start "terme"
	// Exp2.g:40:1: terme : ( primaire ) ( '*' primaire | 'div' primaire )* ;
	public final void terme() throws RecognitionException {
		try {
			// Exp2.g:40:8: ( ( primaire ) ( '*' primaire | 'div' primaire )* )
			// Exp2.g:40:10: ( primaire ) ( '*' primaire | 'div' primaire )*
			{
			// Exp2.g:40:10: ( primaire )
			// Exp2.g:40:11: primaire
			{
			pushFollow(FOLLOW_primaire_in_terme146);
			primaire();
			state._fsp--;

			}

			// Exp2.g:41:9: ( '*' primaire | 'div' primaire )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==9) ) {
					alt2=1;
				}
				else if ( (LA2_0==12) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// Exp2.g:41:11: '*' primaire
					{
					 PtGen2.pt(5); 
					match(input,9,FOLLOW_9_in_terme162); 
					pushFollow(FOLLOW_primaire_in_terme164);
					primaire();
					state._fsp--;

					}
					break;
				case 2 :
					// Exp2.g:42:11: 'div' primaire
					{
					 PtGen2.pt(6); 
					match(input,12,FOLLOW_12_in_terme179); 
					pushFollow(FOLLOW_primaire_in_terme181);
					primaire();
					state._fsp--;

					}
					break;

				default :
					break loop2;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "terme"



	// $ANTLR start "primaire"
	// Exp2.g:46:1: primaire : ( nbentier | ident | '(' exp ')' );
	public final void primaire() throws RecognitionException {
		try {
			// Exp2.g:46:9: ( nbentier | ident | '(' exp ')' )
			int alt3=3;
			switch ( input.LA(1) ) {
			case INT:
				{
				alt3=1;
				}
				break;
			case ID:
				{
				alt3=2;
				}
				break;
			case 7:
				{
				alt3=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// Exp2.g:46:11: nbentier
					{
					pushFollow(FOLLOW_nbentier_in_primaire205);
					nbentier();
					state._fsp--;

					 PtGen2.pt(1); 
					}
					break;
				case 2 :
					// Exp2.g:47:5: ident
					{
					pushFollow(FOLLOW_ident_in_primaire213);
					ident();
					state._fsp--;

					 PtGen2.pt(2); 
					}
					break;
				case 3 :
					// Exp2.g:48:5: '(' exp ')'
					{
					match(input,7,FOLLOW_7_in_primaire221); 
					 PtGen2.pt(7); 
					pushFollow(FOLLOW_exp_in_primaire225);
					exp();
					state._fsp--;

					match(input,8,FOLLOW_8_in_primaire227); 
					 PtGen2.pt(8); 
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "primaire"



	// $ANTLR start "nbentier"
	// Exp2.g:56:1: nbentier : INT ;
	public final void nbentier() throws RecognitionException {
		Token INT1=null;

		try {
			// Exp2.g:56:11: ( INT )
			// Exp2.g:56:15: INT
			{
			INT1=(Token)match(input,INT,FOLLOW_INT_in_nbentier255); 
			 PtGen2.valNb = Integer.parseInt((INT1!=null?INT1.getText():null));
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "nbentier"



	// $ANTLR start "ident"
	// Exp2.g:57:1: ident : ID ;
	public final void ident() throws RecognitionException {
		Token ID2=null;

		try {
			// Exp2.g:57:7: ( ID )
			// Exp2.g:57:9: ID
			{
			ID2=(Token)match(input,ID,FOLLOW_ID_in_ident264); 
			 PtGen2.idLu = (ID2!=null?ID2.getText():null); 
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ident"

	// Delegated rules



	public static final BitSet FOLLOW_exp_in_unite64 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_unite69 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_terme_in_exp85 = new BitSet(new long[]{0x0000000000000C02L});
	public static final BitSet FOLLOW_10_in_exp101 = new BitSet(new long[]{0x00000000000000B0L});
	public static final BitSet FOLLOW_terme_in_exp103 = new BitSet(new long[]{0x0000000000000C02L});
	public static final BitSet FOLLOW_11_in_exp118 = new BitSet(new long[]{0x00000000000000B0L});
	public static final BitSet FOLLOW_terme_in_exp120 = new BitSet(new long[]{0x0000000000000C02L});
	public static final BitSet FOLLOW_primaire_in_terme146 = new BitSet(new long[]{0x0000000000001202L});
	public static final BitSet FOLLOW_9_in_terme162 = new BitSet(new long[]{0x00000000000000B0L});
	public static final BitSet FOLLOW_primaire_in_terme164 = new BitSet(new long[]{0x0000000000001202L});
	public static final BitSet FOLLOW_12_in_terme179 = new BitSet(new long[]{0x00000000000000B0L});
	public static final BitSet FOLLOW_primaire_in_terme181 = new BitSet(new long[]{0x0000000000001202L});
	public static final BitSet FOLLOW_nbentier_in_primaire205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_primaire213 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_7_in_primaire221 = new BitSet(new long[]{0x00000000000000B0L});
	public static final BitSet FOLLOW_exp_in_primaire225 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_8_in_primaire227 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_nbentier255 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_ident264 = new BitSet(new long[]{0x0000000000000002L});
}
