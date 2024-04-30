/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author fanbo
 */
public class Arquives {
    public String foldertoFiles = "agenda";
    private final Path pathFoldertoFiles = Paths.get("/"+foldertoFiles);
    private final Path pathConfigFile = Paths.get("Config.txt");
    
    
    public void createConfigFile() {
        
        if (!Files.exists(pathConfigFile)) {
            final String str = """
                               countTarefas:0 
                               countIdeias:0
                               """;
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Config.txt"))) {
                    writer.write(str);
                    writer.close();
            }
            catch (IOException e){
                System.err.println("Failed to create Configuration File: "+ e.getMessage());
            }
        }
        
    }
    
    public void createFolder() {
        try {
            if (!Files.exists(pathFoldertoFiles)){
                Files.createDirectories(pathFoldertoFiles);
            }
        }
        catch (IOException e) {
            System.err.println("Failed to create directory: "+ foldertoFiles + e.getMessage());
        }
    }
    
    
    
    
}
