package Screens;


//import UMLParcer.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alaa
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */
public class GenerateReqDoc {
    
    final String inPath;
    final String outPath;
    String releaseVersion;
    HashMap<String, Boolean> map;
    HashMap<String, String> mapClassConn;
    String yumlCode;
    ArrayList<CompilationUnit> cuArray;
//    List<String> MyClasses = new ArrayList<String>();
//    List<String> MyMethods = new ArrayList<String>();

    GenerateReqDoc(String inPath, String outFile, String releaseVersion) {
        this.inPath = inPath;
        this.outPath = inPath + "\\" + outFile + ".png";
        map = new HashMap<String, Boolean>();
        mapClassConn = new HashMap<String, String>();
        yumlCode = "";
        this.releaseVersion =releaseVersion;
    }
    public void start() throws Exception {
          cuArray = getCuArray(inPath);
          buildMap(cuArray);
          for (CompilationUnit cu : cuArray)
              yumlCode += parser(cu);
          yumlCode += parseAdditions();
          yumlCode = yumlCodeUniquer(yumlCode);
          System.out.println("Unique Code: " + yumlCode);
          GenerateDiagram.generatePNG(yumlCode, outPath,releaseVersion);
      }

      private String yumlCodeUniquer(String code) {
          String[] codeLines = code.split(",");
          String[] uniqueCodeLines = new LinkedHashSet<String>(
                  Arrays.asList(codeLines)).toArray(new String[0]);
          String result = String.join(",", uniqueCodeLines);
          return result;
      }

      private String parseAdditions()  {
          String result = "";
          Set<String> keys = mapClassConn.keySet(); // get all keys
          for (String i : keys) {
              String[] classes = i.split("-");
              if (map.get(classes[0]))
                  result += "[<<interface>>;" + classes[0] + "]";
              else
                  result += "[" + classes[0] + "]";
              result += mapClassConn.get(i); // Add connection
              if (map.get(classes[1]))
                  result += "[<<interface>>;" + classes[1] + "]";
              else
                  result += "[" + classes[1] + "]";
              result += ",";

  //             writeFile(result);
          }
          return result;
      }

  //    public void writeFile (String classN) throws IOException
  //    {
  //        PrintWriter writer = new PrintWriter("C:\\Users\\Alaa\\Desktop\\class-diagram-test-1\\the-file-name.txt", "UTF-8");
  //        writer.println(classN); 
  //
  //         List<String> lines = Arrays.asList(classN );
  //          Path file = Paths.get("C:\\Users\\Alaa\\Desktop\\class-diagram-test-1\\the-file-name.txt");
  //          Files.write(file, lines, Charset.forName("UTF-8"));
  //          writer.close();
  //    }
      private String parser(CompilationUnit cu)  {
          String result = "";
          String className = "";
          String classShortName = "";
          String methods = "";
          String fields = "";
          String additions = ",";

          ArrayList<String> makeFieldPublic = new ArrayList<String>();
          List<TypeDeclaration> ltd = cu.getTypes();
          Node node = ltd.get(0); // assuming no nested classes

          // Get className
          ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) node;
          if (coi.isInterface()) {
              className = "[" + "<<interface>>;";
          } else {
              className = "[";
          }
          className += coi.getName();
          classShortName = coi.getName();

//          MyClasses.add(classShortName);


          // Parsing Methods
          boolean nextParam = false;
          for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
              // Get Methods
              if (bd instanceof ConstructorDeclaration) {
                  ConstructorDeclaration cd = ((ConstructorDeclaration) bd);
                  if (cd.getDeclarationAsString().startsWith("public")
                          && !coi.isInterface()) {
                      if (nextParam)
                          methods += ";";
                      methods += "+ " + cd.getName() + "(";
                      //----------------
//                      MyMethods.add(cd.getName());
                      //----------------
//                      for (Object gcn : cd.getChildrenNodes()) {
//                          if (gcn instanceof Parameter) {
//                              Parameter paramCast = (Parameter) gcn;
//                              String paramClass = paramCast.getType().toString();
//                              String paramName = paramCast.getChildrenNodes()
//                                      .get(0).toString();
//                              methods += paramName + " : " + paramClass;
//                              if (map.containsKey(paramClass)
//                                      && !map.get(classShortName)) {
//                                  additions += "[" + classShortName
//                                          + "] uses -.->";
//                                  if (map.get(paramClass))
//                                      additions += "[<<interface>>;" + paramClass
//                                              + "]";
//                                  else
//                                      additions += "[" + paramClass + "]";
//                              }
//                              additions += ",";
//                          }
//                      }
//                      methods += ")";
//                      nextParam = true;
                  }
                   
              }
          }
          
          
          for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
              if (bd instanceof MethodDeclaration) {
                  MethodDeclaration md = ((MethodDeclaration) bd);
                  // Get only public methods
                  if (md.getDeclarationAsString().startsWith("public")
                          && !coi.isInterface()) {
                      // Identify Setters and Getters
                      if (md.getName().startsWith("set")
                              || md.getName().startsWith("get")) {
                          String varName = md.getName().substring(3);
                          makeFieldPublic.add(varName.toLowerCase());
                      } else {
                          if (nextParam)
                              methods += ";";
                          methods += "+ " + md.getName() + "(";
                          //----------------
//                            MyMethods.add(md.getName());
                          //----------------
//                          for (Object gcn : md.getChildrenNodes()) {
//                              if (gcn instanceof Parameter) {
//                                  Parameter paramCast = (Parameter) gcn;
//                                  String paramClass = paramCast.getType()
//                                          .toString();
//                                  String paramName = paramCast.getChildrenNodes()
//                                          .get(0).toString();
//                                  methods += paramName + " : " + paramClass;
//                                  if (map.containsKey(paramClass)
//                                          && !map.get(classShortName)) {
//                                      additions += "[" + classShortName
//                                              + "] uses -.->";
//                                      if (map.get(paramClass))
//                                          additions += "[<<interface>>;"
//                                                  + paramClass + "]";
//                                      else
//                                          additions += "[" + paramClass + "]";
//                                  }
//                                  additions += ",";
//                              } else {
//                                  String methodBody[] = gcn.toString().split(" ");
//                                  for (String foo : methodBody) {
//                                      if (map.containsKey(foo)
//                                              && !map.get(classShortName)) {
//                                          additions += "[" + classShortName
//                                                  + "] uses -.->";
//                                          if (map.get(foo))
//                                              additions += "[<<interface>>;" + foo
//                                                      + "]";
//                                          else
//                                              additions += "[" + foo + "]";
//                                          additions += ",";
//                                      }
//                                  }
//                              }
//                          }
//                          methods += ") : " + md.getType();
//                          nextParam = true;
                      }
                  }
              }
          }
          // Parsing Fields
//          boolean nextField = false;
//          for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
//              if (bd instanceof FieldDeclaration) {
//                  FieldDeclaration fd = ((FieldDeclaration) bd);
//                  String fieldScope = aToSymScope(
//                          bd.toStringWithoutComments().substring(0,
//                                  bd.toStringWithoutComments().indexOf(" ")));
//                  String fieldClass = changeBrackets(fd.getType().toString());
//                  String fieldName = fd.getChildrenNodes().get(1).toString();
//                  if (fieldName.contains("="))
//                      fieldName = fd.getChildrenNodes().get(1).toString()
//                              .substring(0, fd.getChildrenNodes().get(1)
//                                      .toString().indexOf("=") - 1);
//                  // Change scope of getter, setters
//                  if (fieldScope.equals("-")
//                          && makeFieldPublic.contains(fieldName.toLowerCase())) {
//                      fieldScope = "+";
//                  }
//                  String getDepen = "";
//                  boolean getDepenMultiple = false;
//                  if (fieldClass.contains("(")) {
//                      getDepen = fieldClass.substring(fieldClass.indexOf("(") + 1,
//                              fieldClass.indexOf(")"));
//                      getDepenMultiple = true;
//                  } else if (map.containsKey(fieldClass)) {
//                      getDepen = fieldClass;
//                  }
//                  if (getDepen.length() > 0 && map.containsKey(getDepen)) {
//                      String connection = "-";
//
//                      if (mapClassConn
//                              .containsKey(getDepen + "-" + classShortName)) {
//                          connection = mapClassConn
//                                  .get(getDepen + "-" + classShortName);
//                          if (getDepenMultiple)
//                              connection = "*" + connection;
//                          mapClassConn.put(getDepen + "-" + classShortName,
//                                  connection);
//                      } else {
//                          if (getDepenMultiple)
//                              connection += "*";
//                          mapClassConn.put(classShortName + "-" + getDepen,
//                                  connection);
//                      }
//                  }
//                  if (fieldScope == "+" || fieldScope == "-") {
//                      if (nextField)
//                          fields += "; ";
//                      fields += fieldScope + " " + fieldName + " : " + fieldClass;
//                      nextField = true;
//                  }
//              }
//
//          }
//          // Check extends, implements
//          if (coi.getExtends() != null) {
//              additions += "[" + classShortName + "] " + "-^ " + coi.getExtends();
//              additions += ",";
//          }
//          if (coi.getImplements() != null) {
//              List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi
//                      .getImplements();
//              for (ClassOrInterfaceType intface : interfaceList) {
//                  additions += "[" + classShortName + "] " + "-.-^ " + "["
//                          + "<<interface>>;" + intface + "]";
//                  additions += ",";
//              }
//          }
//          // Combine className, methods and fields
//          result += className;
//          if (!fields.isEmpty()) {
//              result += "|" + changeBrackets(fields);
//          }
//          if (!methods.isEmpty()) {
//              result += "|" + changeBrackets(methods);
//          }
          result += "]";
          result += additions;
          return result;
      }

      private String changeBrackets(String foo) {
          foo = foo.replace("[", "(");
          foo = foo.replace("]", ")");
          foo = foo.replace("<", "(");
          foo = foo.replace(">", ")");
          return foo;
      }

      private String aToSymScope(String stringScope) {
          switch (stringScope) {
          case "private":
              return "-";
          case "public":
              return "+";
          default:
              return "";
          }
      }

       
      private void buildMap(ArrayList<CompilationUnit> cuArray) {
          for (CompilationUnit cu : cuArray) {
              List<TypeDeclaration> cl = cu.getTypes();
              for (Node n : cl) {
                  ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) n;
                  map.put(coi.getName(), coi.isInterface()); // false is class,
                                                             // true is interface
              }
          }
      }

      @SuppressWarnings("unused")
      private void printMaps() {
          System.out.println("Map:");
          Set<String> keys = mapClassConn.keySet(); // get all keys
          for (String i : keys) {
              System.out.println(i + "->" + mapClassConn.get(i));
          }
          System.out.println("---");
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


    
}
