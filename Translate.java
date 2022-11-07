// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;

public class Translate {
    public static String code_input;
    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from standard input / file
        // create a lexer that feeds off of input CharStream
        CoralLexer lexer;

        if (args.length>0) {
            lexer = new CoralLexer(CharStreams.fromFileName(args[0]));
            code_input =  CharStreams.fromFileName(args[0]).toString();
        }
        //entrada/01.in
        else {
            code_input = CharStreams.fromStream(System.in).toString();
            lexer = new CoralLexer(CharStreams.fromString(code_input));
        }
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        CoralParser parser = new CoralParser(tokens);
        ParseTree tree = parser.init(); // begin parsing at init rule
        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        CoralToPython coralT = new CoralToPython();
        // Walk the tree created during the parse, trigger callbacks
        walker.walk(new CoralToPython(), tree);   //(Vaya y recorra el arbol)
        System.out.println("Traducción a Python"); // print a \n after translation
        System.out.printf(CoralToPython.traduccion);
        /*try{
            // create a CharStream that reads from standard input / file
            // create a lexer that feeds off of input CharStream
            CoralLexer lexer;

            if (args.length>0) {
                lexer = new CoralLexer(CharStreams.fromFileName(args[0]));
                code_input =  CharStreams.fromFileName(args[0]).toString();
            }
            else {
                lexer = new CoralLexer(CharStreams.fromStream(System.in));
                code_input = CharStreams.fromStream(System.in).toString();
            }
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            CoralParser parser = new CoralParser(tokens);
            ParseTree tree = parser.init(); // begin parsing at init rule

            // Create a generic parse tree walker that can trigger callbacks
            ParseTreeWalker walker = new ParseTreeWalker();
            // Walk the tree created during the parse, trigger callbacks
            walker.walk(new CoralToPython(), tree);   //(Vaya y recorra el arbol)
            System.out.println("Traducción a Python"); // print a \n after translation
            System.out.printf(CoralToPython.traduccion);
        }
        catch(Exception e){
            System.err.println("Error caso: " + e);
        }
        */
    }

}
