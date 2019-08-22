/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.io.File;

/**
 *
 * @author AlaaAlmrayat
 * // Created By :: Ala'a Almarayat 
// Email   :: ALLA3LA2@gmail.com
 */ 
public class DirectoryCreation {

    private String path;

    public DirectoryCreation(String pathInput) {
        directoryPath(pathInput);
    }

    public String getFilePath() {
        return path;
    }

    public void setFilePath(String path) {
        this.path = path;
    }

    public void directoryPath(String filePath) {
//     String filePath = getServletContext().getRealPath("input");//getServletConfig.getServletContext.getRealPath("/someFolder");   
        File file = new File(filePath, "test.txt");
        if (!file.getParentFile().exists()) {
            System.out.println(String.format("Creating folder %s...", file.getParentFile().getAbsolutePath()));
            if (file.getParentFile().mkdirs()) {
                System.out.println("Done");
            } else {
                System.out.println("Unable to create folder");
            }
        }

//            if (!file.exists()) {
//                System.out.println(String.format("Creating file %s...", file.getAbsolutePath()));
//                if (file.createNewFile()) {
//                    System.out.println("Done");
//                } else {
//                    System.out.println("Unable to create file");
//                }
//            } else {
//                System.out.println(String.format("File %s already exists!", file.getAbsolutePath()));
//            }
        this.path = filePath;
//        return filePath;
    }

}
