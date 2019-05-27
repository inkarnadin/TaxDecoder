package kurz.java.taxdecoder;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileIO
{
    public static List<File> getFileList(String directoryName)
    {
        List<File> result = new ArrayList<>();
        try
        {
            if (Files.notExists(Paths.get(directoryName)))
            {
                System.err.println("Директория не существует: " + directoryName);
                return result;
            }
            
            result = Files.walk(Paths.get(directoryName))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());            
        }
        return result;
    }
            
    public static void saveToCsv(List<ResultStore> list, String fileName)
    {
        try 
        {
            FileWriter writer = new FileWriter(fileName, true);
            try (BufferedWriter bufferWriter = new BufferedWriter(writer))
            {
                list.forEach(x ->
                {
                    try
                    {
                        bufferWriter.write(x.getRecord());
                    }
                    catch (IOException ex)
                    {
                        System.err.println(ex.getMessage());
                    }
                });
            }
        }
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }          
}
