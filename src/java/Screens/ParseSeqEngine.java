/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

/**
 *
 * @author AlaaAlmrayat
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
import DB.DBManager;
import java.io.*;
import java.util.*;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import net.sourceforge.plantuml.SourceStringReader;

public class ParseSeqEngine {

    String pumlCode;
    final String inPath;
    final String outPath;
    final String inFuncName;
    final String inClassName;
    String releaseVersion;
    DBManager dBManager;
    MethodCallExpr startedLoop = new MethodCallExpr();
    MethodCallExpr startedIF = new MethodCallExpr();
    MethodCallExpr elseIF = new MethodCallExpr();
    MethodCallExpr ended = new MethodCallExpr();

    HashMap<String, String> mapMethodClass;
    ArrayList<CompilationUnit> cuArray;
    HashMap<String, ArrayList<MethodCallExpr>> mapMethodCalls;
    HashMap<String, String> mapLoopMethodCalls;

    ParseSeqEngine(String inPath, String outPath, String inClassName, String inFuncName,
            String outFile,String releaseVersion) {
        this.releaseVersion =releaseVersion;
        startedLoop.setName("UMLparser.startedLoop");
        startedIF.setName("UMLparser.startedIF");
        elseIF.setName("UMLparser.elseIF");
        ended.setName("UMLparser.end");
        this.inPath = inPath;
        this.outPath = outPath + "\\" + outFile + ".png";
        this.inClassName = inClassName;
        this.inFuncName = inFuncName;
        mapMethodClass = new HashMap<String, String>();
        mapMethodCalls = new HashMap<String, ArrayList<MethodCallExpr>>();
        mapLoopMethodCalls = new HashMap<String, String>();
        pumlCode = "@startuml\n";
        
    }

    public void start() throws Exception {
        cuArray = getCuArray(inPath);
        buildMaps();
//        pumlCode += "actor user \n";
//        pumlCode += "user" + " -> " + inClassName + " : " + inFuncName + "\n";
        pumlCode += " -> " + inClassName + " : " + inFuncName + "\n";
        pumlCode += "activate " + mapMethodClass.get(inFuncName) + "\n";
        preParse(inFuncName);
        parse(inFuncName);
        pumlCode += "@enduml";
        generateDiagram(pumlCode);
        System.out.println("Plant UML Code:\n" + pumlCode);

//         dBManager  = new DBManager();
//         dBManager.InsertImage( pumlCode,"Seq");
    }

    private String changeBrackets(String foo) {
        foo = foo.replace("[", "(");
        foo = foo.replace("]", ")");
        foo = foo.replace("<", "(");
        foo = foo.replace(">", ")");
        return foo;
    }

    private String cehckCalleeClass(String methodName, String callerObjectName) {
        ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();
        List<String> variablesDeclerations = new ArrayList<String>();
        boolean doesExist = false;
        String className = "";
        for (CompilationUnit cu : cuArray) {
            if (!doesExist) {
                List<TypeDeclaration> td = cu.getTypes();
                for (Node n : td) {
                    ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) n;
                    className = coi.getName();
                    if (className.equals(this.inClassName)) {
                        for (BodyDeclaration bd : ((TypeDeclaration) coi).getMembers()) {

                            if (bd instanceof FieldDeclaration) {
                                FieldDeclaration fd = ((FieldDeclaration) bd);
                                String fieldClass = changeBrackets(fd.getType().toString());
                                String fieldName = fd.getChildrenNodes().get(1).toString();
                                if (callerObjectName.equals(fieldName)) {
                                    className = fieldClass;
                                    doesExist = true;
                                    break;
                                }
                            }
                            // check method declaration and get what is inside the method 
                            if (bd instanceof MethodDeclaration) {
                                MethodDeclaration md = (MethodDeclaration) bd;
                                if (methodName.equals(md.getName())) {
                                    doesExist = true;
                                    break;
                                }

                            }
                        }
                    }
                }
            }

        }
        if (!doesExist) {
            className = "System";
        }

        return className;
    }
    
    private void preParse(String callerFunc) {
        ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();

        String calleeFunc1 = "";

        if (callerFunc.equals(this.inFuncName)) {
            for (MethodCallExpr mce : mapMethodCalls.get(callerFunc)) {
                String callerClass = mapMethodClass.get(callerFunc);
                String[] clee = mce.toString().split(Pattern.quote("."));// getNameExpr().toString();

                String callerObjectName = clee[0];
                calleeFunc1 = mce.getName();
//                String calleeClass = mapMethodClass.get(calleeFunc1);

                String className = cehckCalleeClass(calleeFunc1, callerObjectName);
                if (!mapMethodClass.containsKey(calleeFunc1)) {
                    mcea.add(mce);
                    mapMethodCalls.put(calleeFunc1, mcea);
                    mapMethodClass.put(calleeFunc1, className);
                }

            }
        }
    }

    private void parse(String callerFunc) {

        for (MethodCallExpr mce : mapMethodCalls.get(callerFunc)) {
            String callerClass = mapMethodClass.get(callerFunc);
            String calleeFunc = mce.getName();
            String calleeClass = mapMethodClass.get(calleeFunc);
            if (mapMethodClass.containsKey(calleeFunc)) {

                if (calleeFunc.equals("UMLparser.startedLoop")) {
                    pumlCode += "loop \n";
                }
                if (calleeFunc.equals("UMLparser.startedIF")) {
                    pumlCode += "alt \n";
                }
                if (calleeFunc.equals("UMLparser.elseIF")) {
                    pumlCode += "else \n";
                }
                if (calleeFunc.equals("UMLparser.end")) {
                    pumlCode += "end \n";
                } else if (!calleeFunc.equals("UMLparser.startedLoop") && !calleeFunc.equals("UMLparser.startedIF") && !calleeFunc.equals("UMLparser.elseIF")) {
                    pumlCode += callerClass + " -> " + calleeClass + " : "
                            + mce.toStringWithoutComments() + "\n";
                    pumlCode += "activate " + calleeClass + "\n";

                    pumlCode += calleeClass + " -->> " + callerClass + "\n";
                    pumlCode += "deactivate " + calleeClass + "\n";
                }
            }

        }
    }

    private ArrayList<MethodCallExpr> buildLoops(Object es) {
        ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();

        if (es instanceof ForStmt || es instanceof ForeachStmt || es instanceof WhileStmt || es instanceof DoStmt) {
            mcea.add(startedLoop);
            for (Object es1 : ((Node) es).getChildrenNodes()) {
                if (es1 instanceof BlockStmt) {
                    for (Object es2 : ((Node) es1).getChildrenNodes()) {

                        if (es2 instanceof ExpressionStmt) {
                            if (((ExpressionStmt) (es2)).getExpression() instanceof MethodCallExpr) {
                                mcea.add((MethodCallExpr) (((ExpressionStmt) (es2)).getExpression()));
                            } else if (es2 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                String fieldName = ((ExpressionStmt) es2).getChildrenNodes().toString();
                                System.out.println(fieldName);
                                String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                fieldName = clee[1].replace("]", "");
                                fieldName = fieldName.replace(")", "");
                                fieldName = fieldName.replace("(", "");
                                MethodCallExpr mc = new MethodCallExpr();
                                mc.setName(fieldName);
                                if (mc instanceof MethodCallExpr) {
                                    System.out.println(mc);
                                    mcea.add(mc);
                                }
                            }
                        } else {
                            mcea.addAll(buildSubMap(es2));
                        }

                    }
                } else {
                    if (es1 instanceof ExpressionStmt) {
                        if (((ExpressionStmt) (es1)).getExpression() instanceof MethodCallExpr) {
                            mcea.add((MethodCallExpr) (((ExpressionStmt) (es1)).getExpression()));
                        } else if (es1 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                            String fieldName = ((ExpressionStmt) es1).getChildrenNodes().toString();
                            System.out.println(fieldName);
                            String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                            fieldName = clee[1].replace("]", "");
                            fieldName = fieldName.replace(")", "");
                            fieldName = fieldName.replace("(", "");
                            MethodCallExpr mc = new MethodCallExpr();
                            mc.setName(fieldName);
                            if (mc instanceof MethodCallExpr) {
                                System.out.println(mc);
                                mcea.add(mc);
                            }
                        }
                    }
                }
            }
            mcea.add(ended);
        }

        return mcea;
    }

    private ArrayList<MethodCallExpr> buildIF(Object es) {
        ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();
        String elseStmt = "";
        String elseStmt2 = "";
        if (es instanceof IfStmt) {
            mcea.add(startedIF);
            int counter = 0;
            ((IfStmt) es).getCondition();
            String conditionExpr = ((IfStmt) es).getCondition().toString();
            if (((IfStmt) es).getElseStmt() != null) {
                elseStmt = ((IfStmt) es).getElseStmt().toString();
            }
            for (Object es1 : ((Node) es).getChildrenNodes()) {
                if (counter >= 1) {
                    mcea.add(elseIF);
                }
                if (es1 instanceof IfStmt) {
                    int counter2 = 0;
                    ((IfStmt) es).getCondition();
                    String conditionExpr2 = ((IfStmt) es).getCondition().toString();
                    if (((IfStmt) es).getElseStmt() != null) {
                        elseStmt2 = ((IfStmt) es).getElseStmt().toString();
                    }
                    for (Object es2 : ((Node) es1).getChildrenNodes()) {
                        if (counter2 >= 1) {
                            mcea.add(elseIF);
                        }
                        if (es2 instanceof BlockStmt) {
                            for (Object es3 : ((Node) es2).getChildrenNodes()) {
                                if (es3 instanceof ExpressionStmt) {
                                    if (((ExpressionStmt) (es3)).getExpression() instanceof MethodCallExpr) {
                                        counter2++;
                                        mcea.add((MethodCallExpr) (((ExpressionStmt) (es3)).getExpression()));
                                    } else if (es2 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                        String fieldName = ((ExpressionStmt) es2).getChildrenNodes().toString();
                                        System.out.println(fieldName);
                                        String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                        fieldName = clee[1].replace("]", "");
                                        fieldName = fieldName.replace(")", "");
                                        fieldName = fieldName.replace("(", "");
                                        MethodCallExpr mc = new MethodCallExpr();
                                        mc.setName(fieldName);
                                        if (mc instanceof MethodCallExpr) {
                                            System.out.println(mc);
                                            mcea.add(mc);
                                        }
                                    }
                                } else {
                                    mcea.addAll(buildSubMap(es3));
                                }
                            }
                        } else {
                            if (es2 instanceof ExpressionStmt) {
                                if (((ExpressionStmt) (es2)).getExpression() instanceof MethodCallExpr) {
                                    counter2++;
                                    mcea.add((MethodCallExpr) (((ExpressionStmt) (es2)).getExpression()));
                                } else if (es2 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                    String fieldName = ((ExpressionStmt) es2).getChildrenNodes().toString();
                                    System.out.println(fieldName);
                                    String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                    fieldName = clee[1].replace("]", "");
                                    fieldName = fieldName.replace(")", "");
                                    fieldName = fieldName.replace("(", "");
                                    MethodCallExpr mc = new MethodCallExpr();
                                    mc.setName(fieldName);
                                    if (mc instanceof MethodCallExpr) {
                                        System.out.println(mc);
                                        mcea.add(mc);
                                    }
                                }
                            }
                        }
                    }
                }
                if (es1 instanceof BlockStmt) {
                    for (Object es2 : ((Node) es1).getChildrenNodes()) {
                        if (es2 instanceof ExpressionStmt) {
                            if (((ExpressionStmt) (es2)).getExpression() instanceof MethodCallExpr) {
                                counter++;
                                mcea.add((MethodCallExpr) (((ExpressionStmt) (es2)).getExpression()));
                            } else if (es2 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                String fieldName = ((ExpressionStmt) es2).getChildrenNodes().toString();
                                System.out.println(fieldName);
                                String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                fieldName = clee[1].replace("]", "");
                                fieldName = fieldName.replace(")", "");
                                fieldName = fieldName.replace("(", "");
                                MethodCallExpr mc = new MethodCallExpr();
                                mc.setName(fieldName);
                                if (mc instanceof MethodCallExpr) {
                                    System.out.println(mc);
                                    mcea.add(mc);
                                }
                            }
                        } else {
                            mcea.addAll(buildSubMap(es2));
                        }
                    }
                } else {
                    if (es1 instanceof ExpressionStmt) {
                        if (((ExpressionStmt) (es1)).getExpression() instanceof MethodCallExpr) {
                            counter++;
                            mcea.add((MethodCallExpr) (((ExpressionStmt) (es1)).getExpression()));
                        } else if (es1 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                            String fieldName = ((ExpressionStmt) es1).getChildrenNodes().toString();
                            System.out.println(fieldName);
                            String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                            fieldName = clee[1].replace("]", "");
                            fieldName = fieldName.replace(")", "");
                            fieldName = fieldName.replace("(", "");
                            MethodCallExpr mc = new MethodCallExpr();
                            mc.setName(fieldName);
                            if (mc instanceof MethodCallExpr) {
                                System.out.println(mc);
                                mcea.add(mc);
                            }
                        }
                    }
                }
            }
            mcea.add(ended);
        }

        return mcea;
    }

    
    private ArrayList<MethodCallExpr> buildSubMap(Object es) {
        ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();
        // check Loop statments then check normal excpretion statments insiede
        mcea.addAll(buildLoops(es));
        // check If statments then check normal excpretion statments insiede 
        mcea.addAll(buildIF(es));

        return mcea;
    }

    private void buildMaps() {

        for (CompilationUnit cu : cuArray) {
            String className = "";
            String elseStmt = "";
            String elseStmt2 = "";
            String methodName = "";
            List<TypeDeclaration> td = cu.getTypes();
            for (Node n : td) {
                ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) n;
                className = coi.getName();
                if (className.equals(this.inClassName)) {
                    for (BodyDeclaration bd : ((TypeDeclaration) coi).getMembers()) {
                        // check method declaration and get what is inside the method 
                        if (bd instanceof MethodDeclaration) {
                            MethodDeclaration md = (MethodDeclaration) bd;

                            methodName = md.getName();
                            ArrayList<MethodCallExpr> mcea = new ArrayList<MethodCallExpr>();
                            if (methodName.equals(this.inFuncName)) {

                                // get method childrens (what inside the method)
                                for (Object bs : md.getChildrenNodes()) {
                                    if (bs instanceof BlockStmt) {
                                        for (Object es : ((Node) bs).getChildrenNodes()) {

                                            // check normal Excpretion statments 
                                            if (es instanceof ExpressionStmt) {

                                                if (((ExpressionStmt) (es)).getExpression() instanceof MethodCallExpr) {
                                                    mcea.add((MethodCallExpr) (((ExpressionStmt) (es)).getExpression()));
                                                } else {
                                                    if (es instanceof ExpressionStmt) {

                                                        if (((ExpressionStmt) (es)).getExpression() instanceof MethodReferenceExpr) {
                                                            mcea.add((MethodCallExpr) (((ExpressionStmt) (es)).getExpression()));
                                                        } else if (es instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                                            String fieldName = ((ExpressionStmt) es).getChildrenNodes().toString();
                                                            System.out.println(fieldName);
                                                            String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                                            fieldName = clee[1].replace("]", "");
                                                            fieldName = fieldName.replace(")", "");
                                                            fieldName = fieldName.replace("(", "");
                                                            MethodCallExpr mc = new MethodCallExpr();
                                                            mc.setName(fieldName);
                                                            if (mc instanceof MethodCallExpr) {
                                                                System.out.println(mc);
                                                                mcea.add(mc);
                                                            }
                                                        }

                                                    }
                                                }

                                            }

                                            // check Loop statments then check normal excpretion statments insiede 
                                            mcea.addAll(buildLoops(es));
                                            // check If statments then check normal excpretion statments insiede 
                                            mcea.addAll(buildIF(es));
                                            // check what inside try catch if exist
                                            if (es instanceof TryStmt) {
                                                for (Object es4 : ((Node) es).getChildrenNodes()) {
                                                    if (es4 instanceof BlockStmt) {
                                                        for (Object es3 : ((Node) es4).getChildrenNodes()) {

                                                            // check normal Excpretion statments 
                                                            if (es3 instanceof ExpressionStmt) {
                                                                if (((ExpressionStmt) (es3)).getExpression() instanceof MethodCallExpr) {
                                                                    mcea.add((MethodCallExpr) (((ExpressionStmt) (es3)).getExpression()));
                                                                } else if (es3 instanceof ExpressionStmt) {
//                            FieldDeclaration fd = ((FieldDeclaration) es);
                                                                    String fieldName = ((ExpressionStmt) es3).getChildrenNodes().toString();
                                                                    System.out.println(fieldName);
                                                                    String[] clee = fieldName.split(Pattern.quote("="));// getNameExpr().toString();
                                                                    fieldName = clee[1].replace("]", "");
                                                                    fieldName = fieldName.replace(")", "");
                                                                    fieldName = fieldName.replace("(", "");
                                                                    MethodCallExpr mc = new MethodCallExpr();
                                                                    mc.setName(fieldName);
                                                                    if (mc instanceof MethodCallExpr) {
                                                                        System.out.println(mc);
                                                                        mcea.add(mc);
                                                                    }
                                                                }
                                                            }
                                                            // check Loop statments then check normal excpretion statments insiede 
                                                            mcea.addAll(buildLoops(es3));
                                                            // check If statments then check normal excpretion statments insiede 
                                                            mcea.addAll(buildIF(es3));
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }

                                mapMethodCalls.put(md.getName(), mcea);
                                mapMethodClass.put(md.getName(), className);
                            }
                        }
                    }
                }
            }
        }
        //printMaps();
    }

    private ArrayList<CompilationUnit> getCuArray(String inPath)
            throws Exception {
        File folder = new File(inPath);
        ArrayList<CompilationUnit> cuArray = new ArrayList<CompilationUnit>();
        for (final File f : folder.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".java")) {
                FileInputStream in = new FileInputStream(f);
                CompilationUnit cu;
                try {
                    cu = JavaParser.parse(in);
                    cuArray.add(cu);
                } finally {
                    in.close();
                }
            }
        }
        return cuArray;
    }

    private String generateDiagram(String source) throws IOException, SQLException {

//        ByteArrayOutputStream bos = null;
        OutputStream png = new FileOutputStream(outPath);
        SourceStringReader reader = new SourceStringReader(source);
        String desc = reader.generateImage(png);
        dBManager = new DBManager();
        dBManager.InsertSeqImage(source, "Seq", outPath,releaseVersion);

        return desc;

    }

    @SuppressWarnings("unused")
    private void printMaps() {
        System.out.println("mapMethodCalls:");
        Set<String> keys = mapMethodCalls.keySet(); // get all keys
        for (String i : keys) {
            System.out.println(i + "->" + mapMethodCalls.get(i));
        }
        System.out.println("---");
        keys = null;

        System.out.println("mapMethodClass:");
        keys = mapMethodClass.keySet(); // get all keys
        for (String i : keys) {
            System.out.println(i + "->" + mapMethodClass.get(i));
        }
        System.out.println("---");
    }

}
