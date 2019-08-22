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
public class CompareDiagram {

    public String compareClassDiagrams(String oldDiagram, String newDiagram) {

        diff_match_patch diff = new diff_match_patch();
        Object arr[] = diff.diff_main(oldDiagram, newDiagram).toArray();
        String test[] = new String[arr.length];
        String status;
        String result = "";
        String color;
        boolean isChanged;
        String insert = "";
        String delete = "";
        String equal = "";

        for (int i = 0; i < arr.length; i++) {
            status = arr[i].toString();
            color = "";
            isChanged = false;
            if (status.contains("INSERT")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(INSERT,\"", "");
                status = status.replace("\")", "");

                insert = result.concat("*I*" + status + "*I*");
                result = result.concat("*I*" + status + "*I*");

            } else if (status.contains("DELETE")) {
                color = "{bg:blue}]";
                isChanged = true;
                status = status.replace("Diff(DELETE,\"", "");
                status = status.replace("\")", "");

                delete = result.concat("*D*" + status + "*D*");
//                result = result.concat("*D*"+status+"*D*");

            } //            else if (status.contains("INSERT")) {
            //                color = "{bg:blue}]";
            //                status = status.replace("Diff(INSERT,\"", "");
            //            }
            else if (status.contains("EQUAL")) {
//                color = "{bg:blue}]";
                status = status.replace("Diff(EQUAL,\"", "");
                status = status.replace("\")", "");
                equal = equal.concat(status);
                result = result.concat(status);
            }

        }

        System.out.println("insert " + insert);
        System.out.println("delete " + delete);

        if (result.charAt(result.indexOf("]", insert.indexOf("*I*")) - 1) != '[' && !insert.isEmpty()) {
            result = replaceCharAt(result, result.indexOf("]", insert.indexOf("*I*")), "{bg:blue}]");
        }

        if (result.charAt(result.indexOf("]", delete.indexOf("*D*")) - 1) != '[' && !delete.isEmpty()) {
            result = replaceCharAt(result, result.indexOf("]", delete.indexOf("*D*")), "{bg:red}]");
        }

        result = result.replace("*I*", "");
        result = result.replace("*D*", "");

        return result;
    }

    public String replaceCharAt(String s, int pos, String c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

}
