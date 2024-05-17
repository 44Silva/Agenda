/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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
    
    public String pendenciesTypes[] = {"Tarefas","Ideias"};
    
    
    public void createConfigFile() {
        
        if (!Files.exists(pathConfigFile)) {
            final String str = """
                               countTarefas:0 
                               countIdeias:0
                               tipos:Corpo,Estudos,Aerodinâmica
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
                for(int i = 0; i < pendenciesTypes.length; i++){
                    Path pathFile = Paths.get(folderPendentes+"/"+pendenciesTypes[i]+".txt");
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
                for(int i = 0; i < pendenciesTypes.length; i++){
                    Path pathFile = Paths.get(folderConcluidos+"/"+pendenciesTypes[i]+".txt");
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
    
    public String[] getConfigTipos() throws IOException{
        return Files.readAllLines(pathConfigFile).get(2).substring(6).split(",");
    }
    
    public void addPendency(int logicPendencyType, String titulo, String tipo, String dataCriacao, String dataLimite, String descricao) throws FileNotFoundException, IOException{
        PrintWriter printWriter = new PrintWriter(folderPendentes+"/"+pendenciesTypes[logicPendencyType]+".txt");
        String lineConfig = Files.readAllLines(pathConfigFile).get(logicPendencyType);
        int idPendency = Integer.parseInt(lineConfig.substring(lineConfig.indexOf(':')+1))+1;
        
        switch (logicPendencyType) {
            case 0:
                printWriter.printf("%d,%s,%s,%s,%s,%s", idPendency, titulo, tipo, dataCriacao, dataLimite, descricao);
                printWriter.close();
                break;
            case 1:
                printWriter.printf("%d,%s,%s,%s,%s", idPendency, titulo, tipo, dataCriacao, descricao);
                printWriter.close();
                break;
        }
            
        
          
    }
    
}
