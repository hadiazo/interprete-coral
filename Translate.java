// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;

public class Translate {
    public static void main(String[] args) throws Exception {
// create a CharStream that reads from standard input / file
// create a lexer that feeds off of input CharStream
        CoralLexer lexer;

        if (args.length>0)
            lexer = new CoralLexer(CharStreams.fromFileName(args[0]));
        else
            lexer = new CoralLexer(CharStreams.fromStream(System.in));
// create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
// create a parser that feeds off the tokens buffer
        CoralParser parser = new CoralParser(tokens);
        ParseTree tree = parser.init(); // begin parsing at init rule

// Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Walk the tree created during the parse, trigger callbacks
        walker.walk(new CoralToPython(), tree);   //(Vaya y recorra el arbol)
        System.out.println(); // print a \n after translation
        System.out.printf(CoralToPython.traduccion);
    }
}
