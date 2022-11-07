import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Rule;

public class CoralToPython extends CoralBaseListener {
    static String traduccion ="";
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
        if(ctx.FUNCTION()!=null){
            String nombre_funcion = ctx.ID().getText();
            traduccion = traduccion + "def" +" "+ nombre_funcion + "(";
            //System.out.printf("def" +" "+ nombre_funcion + "(" + ctx.);
            // ctx.FUNCTION())==Function  ctx.ID()==nombre de la funcion
            //System.out.printf(traduccion);
        }

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFuncion(CoralParser.FuncionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDeclaracion(CoralParser.DeclaracionContext ctx) {
        if(ctx.tipo()!=null){
            String tipo_var = ctx.tipo().getText();
            String variable = ctx.ID().getText();
            if(ctx.array()!=null){
                traduccion = traduccion + variable + " = [";
            }
            if(ctx.array()==null && (tipo_var == "integer" || tipo_var == "float")){
                traduccion = traduccion + variable + " = 0";
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
            //System.out.printf(tipo_var);
            //System.out.print(ctx.FUNCTION());
        }
        if(ctx.operacion()!=null && ctx.array_elemento()==null){
            String variable = ctx.ID().getText();
            String operacion = ctx.operacion().getText();
            traduccion = traduccion + variable + '=' + operacion;
        }
        if(ctx.arrayE()!=null){
            String variable = ctx.ID().getText();
            String arraye = ctx.arrayE().getText();
            traduccion = traduccion + variable + '=' + arraye  ;
        }
        if(ctx.array_elemento()!=null ){
            String array_nom = ctx.ID().getText();
            String array_elem = ctx.array_elemento().getText();
            String operacion = ctx.operacion().getText();
            traduccion = traduccion + array_nom + array_elem + '=' + operacion;
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
        }
        if(ctx.NUMERO()==null){
            String longitud = "s";
            traduccion = traduccion.substring(0, traduccion.length()-1);
            traduccion = traduccion + "[]";
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
            if(ctx.ID()!=null){
                String variable = ctx.ID().getText();
                traduccion = traduccion + variable + ",end=\"\"" +")";
                //System.out.printf(tipo_var);
                //System.out.print(ctx.FUNCTION());
            }
            if(ctx.CADENA()!=null){
                String cadena = ctx.CADENA().getText();
                traduccion = traduccion + cadena + ",end=\"\"" +")";
                //System.out.printf(tipo_var);
                //System.out.print(ctx.FUNCTION());
            }
            if(ctx.operacion()!=null){
                String operacion = ctx.operacion().getText();
                traduccion = traduccion + operacion + ",end=\"\"" +")";
                //System.out.printf(tipo_var);
                //System.out.print(ctx.FUNCTION());
            }

        }
    }

    @Override public void exitImprimir(CoralParser.ImprimirContext ctx) {

    }
    @Override public void enterDesigualdad(CoralParser.DesigualdadContext ctx) {
        if(ctx.ID()!=null){
            String desi = ctx.getText();

            traduccion = traduccion + desi + ";";

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
            }
            if(ctx.desigualdad_fl().operacion_fl()!=null ){
                String limite_sup = ctx.desigualdad_fl().operacion_fl().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        limite_sup + "," + sentido + avance + ")"+":";
            }
            if(ctx.desigualdad_fl().op_bool()!=null ){
                String limite_sup = ctx.desigualdad_fl().op_bool().ID().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        limite_sup + "," + sentido + avance + ")"+":";
            }
            if(ctx.desigualdad_fl().arrayS()!=null){
                String limite_sup = ctx.desigualdad_fl().arrayS().ID().getText();
                traduccion = traduccion + "for " + i + " in " + "range(" + limite_inf + "," +
                        "len("+limite_sup + ")" + "," + sentido + avance + ")"+":";
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
            }
            if(ctx.CONDICIONAL()!=null ){
                String condicion= ctx.op_bool().getText();
                String condicional = ctx.CONDICIONAL().getText();
                String condicion2 = ctx.decla_bool().getText();
                traduccion = traduccion + "if " + iden  + condicion + " " + condicional + " " +condicion2+":";
            }
            if(ctx.op_bool()!=null ){
                String condicion= ctx.op_bool().getText();
                //String condicion2 = ctx.decla_bool().getText();
                traduccion = traduccion + "if " + iden  + condicion + ":";
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
            }
            if (ctx.CONDICIONAL() != null) {
                String condicion = ctx.op_bool().getText();
                String condicional = ctx.CONDICIONAL().getText();
                String condicon2 = ctx.decla_bool().getText();
                traduccion = traduccion + "elseif " + iden + condicion + " " + condicional + " " + condicon2 + ":";
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
    @Override
    public void enterAssign_size(CoralParser.Assign_sizeContext ctx) {
        if(ctx.NUMERO()!=null){
            int longitud = Integer.valueOf(ctx.NUMERO().getText());
            traduccion = ctx.arrayS().ID().getText() + "=" + "[" ;
            for(int i =0 ; i<longitud;i++){
                traduccion = traduccion + "0"+",";
            }
            traduccion = traduccion.substring(0, traduccion.length()-1);
            traduccion = traduccion + ']';;
        }
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

