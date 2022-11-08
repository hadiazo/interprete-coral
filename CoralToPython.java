import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Rule;

public class CoralToPython extends CoralBaseListener {
    static String traduccion ="";
    static String f_return ="";
    @Override
    public void enterTipo(CoralParser.TipoContext ctx) {
    }

    @Override public void exitTipo(CoralParser.TipoContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterFuncion(CoralParser.FuncionContext ctx) {
        if(ctx.FUNCTION()!=null && ctx.MAIN()==null){
            String nombre_funcion = ctx.ID().getText();
            traduccion = traduccion + "def" +" "+ nombre_funcion + "(";
            //traduccion += "\n";
            String retorno = ctx.declaracion().ID().getText();
            f_return= "return(" + retorno + ")";
        }
        if(ctx.FUNCTION()!=null && ctx.MAIN()!=null){
            String nombre_funcion = "Main";
            traduccion = traduccion + "if __name__== '__main__':";
            traduccion += "\n";
            traduccion += "\t";
        }
        else{
            traduccion = traduccion + "):";
            traduccion += "\n";
        }

    }
    @Override public void exitFuncion(CoralParser.FuncionContext ctx) { }
    @Override public void enterDeclaracion(CoralParser.DeclaracionContext ctx) {
        if(ctx.tipo()!=null){
            String tipo_var = ctx.tipo().getText();
            String variable = ctx.ID().getText();
            if(ctx.array()!=null){
                traduccion = traduccion + variable + " = [";
                traduccion += "\n";
            }
            if(ctx.array()==null && (tipo_var == "integer" || tipo_var == "float")){
                traduccion = traduccion + variable + " = 0";
                traduccion += "\n";
            }

            //System.out.print(ctx.FUNCTION());
        }
    }

    @Override public void exitDeclaracion(CoralParser.DeclaracionContext ctx) {
        //System.out.printf(traduccion);
    }

    @Override
    public void enterDeclaracionF(CoralParser.DeclaracionFContext ctx) {
        if(ctx.tipo()!=null){
            String variable = ctx.ID().getText();
            traduccion += variable;
            traduccion += "):";
            traduccion += "\n";
            traduccion += "\t";
            //System.out.printf(tipo_var);
            //System.out.print(ctx.FUNCTION());
        }
    }

    @Override
    public void exitDeclaracionF(CoralParser.DeclaracionFContext ctx) {
        //System.out.printf(traduccion);

    }

    @Override public void enterOperacion(CoralParser.OperacionContext ctx) {
        if(ctx.ID()!=null){
            //String variable = ctx.ID().toString();
            //String value = (ctx.NUMERO().toString());
            //String operador= ctx.OPERADOR().getText();
            //traduccion += ctx.getText();
            //traduccion += operador;
            //traduccion += value;
        }
    }

    @Override public void exitOperacion(CoralParser.OperacionContext ctx) {
        //System.out.printf(traduccion);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void enterAsignacion(CoralParser.AsignacionContext ctx) {
        if(ctx.NUMERO()!=null){
            String variable = ctx.ID().getText();
            String variable2 = ctx.NUMERO().getText();
            //System.out.printf(variable,"\n");
            traduccion = traduccion + variable + " " + '=' + variable2;
            traduccion += "\n";
            //System.out.printf(tipo_var);
            //System.out.print(ctx.FUNCTION());
        }
        if(ctx.operacion()!=null && ctx.array_elemento()==null){
            String variable = ctx.ID().getText();
            String operacion = ctx.operacion().getText();
            traduccion = traduccion + variable + '=' + operacion;
            traduccion += "\n";
        }
        if(ctx.arrayE()!=null){
            String variable = ctx.ID().getText();
            String arraye = ctx.arrayE().getText();
            traduccion = traduccion + variable + '=' + arraye  ;
            traduccion += "\n";
        }
        if(ctx.array_elemento()!=null ){
            String array_nom = ctx.ID().getText();
            String array_elem = ctx.array_elemento().getText();
            String operacion = ctx.operacion().getText();
            traduccion = traduccion + array_nom + array_elem + '=' + operacion;
            traduccion += "\n";
        }
        if(ctx.llamada_funcion()!=null ){
            String variable = ctx.ID().getText();
            String llamada_f = ctx.llamada_funcion().getText();
            traduccion = traduccion + variable + '=' + llamada_f;
            traduccion += "\n";
        }
        if(ctx.randomnumber()!=null ){
            String variable = ctx.ID().getText();
            String rango = ctx.randomnumber().rango().getText();
            traduccion = traduccion + variable + "= random.randint" + rango ;
            traduccion += "\n";
        }
    }

    @Override public void exitAsignacion(CoralParser.AsignacionContext ctx) {
        //System.out.print(traduccion);
    }
    @Override
    public void enterArray(CoralParser.ArrayContext ctx) {
        if(ctx.NUMERO()!=null){
            int longitud = Integer.valueOf(ctx.NUMERO().getText());
            for(int i =0 ; i<longitud;i++){
                traduccion = traduccion + "0"+",";
            }
            traduccion = traduccion.substring(0, traduccion.length()-1);
            traduccion = traduccion + ']';;
            traduccion += "\n";
        }
        if(ctx.NUMERO()==null){
            String longitud = "s";
            traduccion = traduccion.substring(0, traduccion.length()-1);
            traduccion = traduccion + "[]";
            traduccion += "\n";
        }
    }

    @Override
    public void exitArray(CoralParser.ArrayContext ctx) {
    }
    @Override public void enterArrayS(CoralParser.ArraySContext ctx) {

    }

    @Override public void enterImprimir(CoralParser.ImprimirContext ctx) {
        if(ctx.PUT()!=null){
            traduccion = traduccion + "print(";
            if(ctx.ID()!=null && ctx.elegir_decimales()==null){
                String variable = ctx.ID().getText();
                traduccion = traduccion + variable + ",end=\"\"" +")";
                traduccion += "\n";
            }
            if(ctx.CADENA()!=null){
                String cadena = ctx.CADENA().getText();
                traduccion = traduccion + cadena + ",end=\"\"" +")";
                traduccion += "\n";
            }
            if(ctx.operacion()!=null){
                String operacion = ctx.operacion().getText();
                traduccion = traduccion + operacion + ",end=\"\"" +")";
                traduccion += "\n";
            }
            if(ctx.fun_in()!=null){
                //String in_fun = ctx.fun_in().getText();
                if(ctx.fun_in().squareroot()!=null){
                    String variable = ctx.fun_in().squareroot().ID().getText();
                    if(ctx.elegir_decimales()==null){
                        traduccion = traduccion + "pow("+ variable + "," + "0.5)" + ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                    if(ctx.elegir_decimales()!=null){
                        String decimales_n = ctx.elegir_decimales().NUMERO().getText();
                        traduccion = traduccion + "round(" + "pow("+ variable + "," + "0.5)"
                                + "," + decimales_n + ")"
                                +  ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                }
                if(ctx.fun_in().absolutevalue()!=null){
                    String variable = ctx.fun_in().absolutevalue().ID().getText();
                    if(ctx.elegir_decimales()==null){
                        traduccion = traduccion + "abs("+ variable +")" + ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                    if(ctx.elegir_decimales()!=null){
                        String decimales_n = ctx.elegir_decimales().NUMERO().getText();
                        traduccion = traduccion + "round(" + "abs("+ variable + ")"
                                + "," + decimales_n + ")"
                                +  ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                }
                if(ctx.fun_in().raisetopower()!=null){
                    String rango = ctx.fun_in().raisetopower().rango().getText();
                    if(ctx.elegir_decimales()==null){
                        traduccion = traduccion + "pow"+ rango + ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                    if(ctx.elegir_decimales()!=null){
                        String decimales_n = ctx.elegir_decimales().NUMERO().getText();
                        traduccion = traduccion + "round(" + "pow"+ rango +
                                 "," + decimales_n + ")" +  ",end=\"\"" +")";
                        traduccion += "\n";
                    }
                }
            }

            if(ctx.elegir_decimales()!=null && ctx.fun_in()==null){
                String decimales_n = ctx.elegir_decimales().NUMERO().getText();
                String variable = ctx.ID().getText();
                traduccion = traduccion + "round(" + variable + "," + decimales_n + ")" + ",end=\"\"" +")";
                traduccion += "\n";
            }

        }
    }

    @Override public void exitImprimir(CoralParser.ImprimirContext ctx) {

    }
    @Override public void enterDesigualdad(CoralParser.DesigualdadContext ctx) {
        if(ctx.ID()!=null){
            String desi = ctx.getText();

            traduccion = traduccion + desi + ";";
            traduccion += "\n";

        }
    }
    @Override public void exitDesigualdad(CoralParser.DesigualdadContext ctx) {

    }
    @Override
    public void enterFor_l(CoralParser.For_lContext ctx) {
        if(ctx.FOR()!=null){
            String i = ctx.ID().getText();
            int limite_inf = Integer.valueOf(ctx.NUMERO().getText());
            int avance = Integer.valueOf(ctx.asignacion_fl().operacion_fl().NUMERO().toString());
            String sentido = ctx.asignacion_fl().operacion_fl().OPERADOR().getText();
            if(ctx.desigualdad_fl().NUMERO()!=null){
                int limite_sup = Integer.valueOf(ctx.desigualdad_fl().NUMERO().getText());
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                         limite_sup + "," + sentido + avance + ")"+":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if(ctx.desigualdad_fl().operacion_fl()!=null ){
                String limite_sup = ctx.desigualdad_fl().operacion_fl().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        limite_sup + "," + sentido + avance + ")"+":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if(ctx.desigualdad_fl().op_bool()!=null ){
                String limite_sup = ctx.desigualdad_fl().op_bool().ID().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        limite_sup + "," + sentido + avance + ")"+":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if(ctx.desigualdad_fl().arrayS()!=null){
                String limite_sup = ctx.desigualdad_fl().arrayS().ID().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        "len("+limite_sup + ")" + "," + sentido + avance + ")"+":";
                traduccion += "\n";
                traduccion += "\t";
            }
        }
    }

    @Override
    public void exitFor_l(CoralParser.For_lContext ctx) {

    }
    @Override
    public void enterIf_c(CoralParser.If_cContext ctx) {
        if(ctx.IF()!=null){
            String iden = ctx.ID().getText();
            if(ctx.CONDICIONAL()==null && ctx.OPENING_PAR()==null){
                String condicion= ctx.op_bool().getText();
                traduccion = traduccion + "if " + iden + condicion + ":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if(ctx.CONDICIONAL()!=null ){
                String condicion= ctx.op_bool().getText();
                String condicional = ctx.CONDICIONAL().getText();
                String condicion2 = ctx.decla_bool().getText();
                traduccion = traduccion + "if " + iden  + condicion + " " + condicional + " " +condicion2+":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if(ctx.op_bool()!=null && ctx.CONDICIONAL()==null ){
                String condicion= ctx.op_bool().getText();
                //String condicion2 = ctx.decla_bool().getText();
                traduccion = traduccion + "if " + iden  + condicion + ":";
                traduccion += "\n";
                traduccion += "\t";
            }

        }
    }

    @Override
    public void exitIf_c(CoralParser.If_cContext ctx) {
    }

    @Override public void enterElse(CoralParser.ElseContext ctx) {
        if(ctx.ELSE()!=null){
            String els = ctx.ELSE().getText();
            traduccion = traduccion + "else: ";
            traduccion += "\n";
            traduccion += "\t";
        }
    }
    @Override
    public void exitElse(CoralParser.ElseContext ctx) { }

    @Override
    public void enterElseif_c(CoralParser.Elseif_cContext ctx) {
        if(ctx.ELSEIF()!=null) {
            String iden = ctx.ID().getText();
            if (ctx.CONDICIONAL() == null && ctx.OPENING_PAR() == null) {
                String condicion = ctx.op_bool().getText();
                traduccion = traduccion + "elseif " + iden + condicion + ":";
                traduccion += "\n";
                traduccion += "\t";
            }
            if (ctx.CONDICIONAL() != null) {
                String condicion = ctx.op_bool().getText();
                String condicional = ctx.CONDICIONAL().getText();
                String condicon2 = ctx.decla_bool().getText();
                traduccion = traduccion + "elseif " + iden + condicion + " " + condicional + " " + condicon2 + ":";
                traduccion += "\n";
                traduccion += "\t";
            }
        }
    }
    @Override
    public void exitElseif_c(CoralParser.Elseif_cContext ctx) {
    }

    @Override
    public void enterIndent(CoralParser.IndentContext ctx) {
        if(ctx.INDENTACION()!=null){
            traduccion = traduccion.concat("    ");

            //System.out.printf("HOLA");
        }
    }
    @Override
    public void exitIndent(CoralParser.IndentContext ctx) { }
    @Override
    public void enterIndent2(CoralParser.Indent2Context ctx) {
        if(ctx.INDENTACION2()!=null){
            traduccion = traduccion.concat("        ");
        }
    }
    public void enterIndent3(CoralParser.Indent3Context ctx) {
        if(ctx.INDENTACION3()!=null){
            traduccion = traduccion.concat("        ");
        }
    }
    @Override
    public void enterAssign_size(CoralParser.Assign_sizeContext ctx) {
        if(ctx.NUMERO()!=null){
            int longitud = Integer.valueOf(ctx.NUMERO().getText());
            traduccion = ctx.arrayS().ID().getText() + "=" + "[" ;
            for(int i =0 ; i<longitud;i++){
                traduccion = traduccion + "0"+",";
            }
            traduccion = traduccion.substring(0, traduccion.length()-1);
            traduccion = traduccion + ']';
            traduccion += "\n";
        }
        if(ctx.ID()!=null){
            String longitud = ctx.ID().getText();
            traduccion = ctx.arrayS().ID().getText() + "=" + "[" ;
            traduccion = traduccion + ']';
            traduccion += "\n";
        }
    }
    /*@Override public void enterIndentaciones(CoralParser.IndentacionesContext ctx) {
        if(ctx.indent()!=null && ctx.indent2()==null && ctx.indent3()==null){
            traduccion = traduccion.concat("    ");
        }
        if(ctx.indent()!=null && ctx.indent2()!=null && ctx.indent3()==null){
            traduccion = traduccion.concat("        ");
        }
        if(ctx.indent()!=null && ctx.indent2()==null && ctx.indent3()!=null){
            traduccion = traduccion.concat("            ");
        }
        //traduccion = traduccion.concat("    ");
    }*/

    @Override public void enterRandomnumber(CoralParser.RandomnumberContext ctx) {
        if(ctx.RANDOMNUMBER()!=null){

        }
    }

    @Override public void enterWhile(CoralParser.WhileContext ctx) {
        if(ctx.WHILE()!=null){
            String condicion= ctx.desigualdadW().getText();
            traduccion=traduccion + "while " + condicion + ":" ;
            traduccion+="\n";
            traduccion += "\t";
        }
    }
    @Override public void enterSquareroot(CoralParser.SquarerootContext ctx) {

    }
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        //System.out.print(ctx.getText());
        //String rule = ;
        //System.out.print();
    }
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        //System.out.print(traduccion);
        //System.out.print(ctx.getText());
    }
    @Override
    public void visitTerminal(TerminalNode node) {
        //System.out.print(traduccion);
    }
}

