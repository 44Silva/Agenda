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
    public String folderPendentes = "Pendentes";
    public String folderConcluidos = "Concluidos";
    private final Path pathFoldertoPendentes = Paths.get(folderPendentes);
    private final Path pathFoldertoConcluidos = Paths.get(folderConcluidos);
    private final Path pathConfigFile = Paths.get("Config.txt");
    
    public String pendencesTypes[] = {"Tarefas","Ideias"};
    
    
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
    
    public void createFolderPendentes() {
        try {
            if (!Files.exists(pathFoldertoPendentes)){
                Files.createDirectories(pathFoldertoPendentes);
                for(int i = 0; i < pendencesTypes.length; i++ ){
                    Path pathFile = Paths.get(folderPendentes+"/"+pendencesTypes[i]+".txt");
                    Files.createFile(pathFile);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Failed to create directory: "+ folderPendentes + e.getMessage());
        }
    }
    
    public void createFolderConcluidos() {
        try {
            if (!Files.exists(pathFoldertoConcluidos)){
                Files.createDirectories(pathFoldertoConcluidos);
                for(int i = 0; i < pendencesTypes.length; i++ ){
                    Path pathFile = Paths.get(folderConcluidos+"/"+pendencesTypes[i]+".txt");
                    Files.createFile(pathFile);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Failed to create directory: "+ folderConcluidos + e.getMessage());
        }
    }
    
    public void setup(){
        this.createConfigFile();
        this.createFolderPendentes();
        this.createFolderConcluidos();
    }
    
    
}
