/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import javax.sound.midi.Patch;

/**
 *
 * @author fanbo
 */
public class Arquives {
    public String folderPendentes = "Pendentes";
    public String folderConcluidos = "Concluidos";
    public String stringPathConfigFile = "Config.txt";
    private final Path pathFoldertoPendentes = Paths.get(folderPendentes);
    private final Path pathFoldertoConcluidos = Paths.get(folderConcluidos);
    private final Path pathConfigFile = Paths.get(stringPathConfigFile);
    
    public String pendenciesTypes[]= {"Tarefas","Ideias"};
    
    
    public void createConfigFile() {
        
        if (!Files.exists(pathConfigFile)) {
            final String str = """
                               countTarefas:0 
                               countIdeias:0
                               tipos:
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
        String pathPendency = folderPendentes+"/"+pendenciesTypes[logicPendencyType]+".txt";
        String pathTempPendency = folderPendentes+"/temp"+pendenciesTypes[logicPendencyType]+".txt";
        File tempPendency = new File(pathTempPendency);
        BufferedWriter pendencyWriter = Files.newBufferedWriter(Paths.get(pathTempPendency));
        List<String> fileLines = Files.readAllLines(Paths.get(pathPendency));
        
        for (int i = 0;i < fileLines.size(); i++){
            pendencyWriter.append(fileLines.get(i)+"\n");
        }
        
        String lineConfig = Files.readAllLines(pathConfigFile).get(logicPendencyType);
        int idPendency = Integer.parseInt(lineConfig.substring(lineConfig.indexOf(':')+1))+1;
         List<String> configLines = Files.readAllLines(pathConfigFile);
        File tempConfig = new File("tempConfig.txt");
        BufferedWriter configWriter = Files.newBufferedWriter(Paths.get("tempConfig.txt"));
        
        
        switch (logicPendencyType) {
            case 0:
                pendencyWriter.append(idPendency +","+ titulo +","+ tipo +","+ dataCriacao +","+ dataLimite +","+ descricao + "\n");
                
                for (int i = 0; i < configLines.size(); i++){
                    if (i == 0) {
                        configWriter.append("countTarefas:"+idPendency+"\n");
                    }
                    else{
                        configWriter.append(configLines.get(i)+"\n");
                    }
                }
                break;
            case 1:
                pendencyWriter.append(idPendency +","+ titulo +","+ tipo +","+ dataCriacao +","+ descricao +","+ "\n");
                
                for (int i = 0; i < configLines.size(); i++){
                    if (i == 1) {
                        configWriter.append("countIdeias:"+idPendency+"\n");
                    }
                    else{
                        configWriter.append(configLines.get(i)+"\n");
                    }
                }
                break;
        }
        pendencyWriter.close();
        configWriter.close();
        Files.delete(Paths.get(pathPendency));
        File nomePendencyFile = new File(pathPendency);
        tempPendency.renameTo(nomePendencyFile);
        Files.delete(pathConfigFile);
        File nomeConfigFile = new File(stringPathConfigFile);
        tempConfig.renameTo(nomeConfigFile);
        
          
    }
    
    public String[] getPendencies(int logicPendencyType, int indexRow) throws IOException{
        String pathPendency = folderPendentes+"/"+pendenciesTypes[logicPendencyType]+".txt";
        String lineArray[] = {};
        if (logicPendencyType < 2 && logicPendencyType > -1){
            return Files.readAllLines(Paths.get(pathPendency)).get(indexRow).split(",");
        }
        else {
            return lineArray;
        } 
    }
    
    public int size(int logicPendencyType) throws IOException{
        String pathPendency = folderPendentes+"/"+pendenciesTypes[logicPendencyType]+".txt";
        List<String> listLines = Files.readAllLines(Paths.get(pathPendency));
        return listLines.size();
    }
    
}
