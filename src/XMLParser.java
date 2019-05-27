package kurz.java.taxdecoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.*;

public class XMLParser 
{
    public static List<ResultStore> parse(String fileName) 
    {
        List<ResultStore> result = new ArrayList<>();
        try 
        {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
           
            ResultStore rs = null;
            while (xmlr.hasNext()) 
            {
                xmlr.next();

                if (xmlr.isStartElement()) 
                {                    
                    if (xmlr.getLocalName().equals("Документ"))
                        rs = new ResultStore();
                    
                    if (xmlr.getLocalName().equals("Документ") || xmlr.getLocalName().equals("СведНП") || xmlr.getLocalName().equals("СведДохРасх"))
                    {                        
                        for (int i = 0 ; i < xmlr.getAttributeCount(); i++)
                        {   
                            switch (xmlr.getAttributeLocalName(i))
                            {
                                case "ИдДок": rs.setIdDoc(xmlr.getAttributeValue(i)); break;
                                case "ДатаДок": rs.setDateDocument(xmlr.getAttributeValue(i)); break;
                                case "ДатаСост": rs.setDateCreate(xmlr.getAttributeValue(i)); break;
                                case "НаимОрг": rs.setOrgName(xmlr.getAttributeValue(i)); break;
                                case "ИННЮЛ": rs.setInnUL(xmlr.getAttributeValue(i)); break;
                                case "СумДоход": rs.setIncome(xmlr.getAttributeValue(i)); break;
                                case "СумРасход": rs.setExpense(xmlr.getAttributeValue(i)); break;
                            } 
                        }
                    }
                } 
                else if (xmlr.isEndElement() && xmlr.getLocalName().equals("Документ"))
                {
                    result.add(rs);
                    rs = null;
                }
            }
            return result;
        } 
        catch (FileNotFoundException | XMLStreamException ex) 
        {
            System.err.println(ex.getMessage());
            return null;
        }
    }      
}
