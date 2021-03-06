/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kickassdb;
import java.io.*;
import javax.swing.JOptionPane;


/**
 *
 * @author cindy
 */
public class Storage 
{
    static private String readFile(String fileName) 
    {
        String read = "";
        File file = new File(fileName);
        BufferedReader reader = null;
        
        try 
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) 
            {
                read += tempString + "\r\n";
            }
            reader.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            if (reader != null) 
            {
                try 
                {
                    reader.close();
                } catch (IOException e1) 
                {
                }
            }
        }
        
        return read;
    }
    
    static public void AddNewFolder(String path_name) 
    {
        try 
        {
            /* Creates a new folder for the recieved path */
            File myFilePath = new File(path_name);
            
            if ( !myFilePath.exists() )             
                myFilePath.mkdir();              
            else
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "There is already a diretory with this name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }  
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Error creating directory.", "Error", JOptionPane.ERROR_MESSAGE);                                         
            e.printStackTrace();  
        }
    }
    
    public static boolean createSchema(Schema schema)
    {
        try 
        {
            /* Creates a new folder for the schema from the recieved path */
            File schemaFile = new File("src/schemas/" + schema.getName() + ".sdef");
            
            if ( schemaFile.exists() )
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "There is already a schema with this name.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            /* Create Blank Definition File */            
            schemaFile.createNewFile();
            
            /* Writes the Schema object to the file */
            OutputStream out = new FileOutputStream(schemaFile);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            
            oos.writeObject(schema);            
            oos.close();
            
            return true;
        }  
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Error creating schema directory.", "Error", JOptionPane.ERROR_MESSAGE);                                         
            e.printStackTrace();
            return false;
        }                
    }
    
    public static void saveSchema(Schema schema)
    {
        try 
        {                                     
            /* Re-Create Blank Definition File */
            File schemaDef = new File("src/schemas/" + schema.getName() + ".sdef");
            schemaDef.delete(); // Delete file
            schemaDef.createNewFile(); // Re-create file
            
            /* Writes the Schema object to the file */
            OutputStream out = new FileOutputStream(schemaDef);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            
            oos.writeObject(schema);            
            oos.close();                        
        }  
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Error saving schema", "Error", JOptionPane.ERROR_MESSAGE);                                         
            e.printStackTrace();            
        }
    }
            
    public static void openSchema(File schema_file)
    {
        /* Reads a definition file and loads it into a Schema object */
        try
        {
            FileInputStream fin = new FileInputStream(schema_file);
            ObjectInputStream ois = new ObjectInputStream(fin);
            
            Schema schema = (Schema) ois.readObject();
            
            /* Set this schema as the current schema */
            MainWindow.setDefault_schema(schema);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /* turns the tuple to a string to write in the data.txt*/
    static public void AddData(String path_name, Tuple tuple) 
    {
        String content = "";
        
        for(Value val : tuple.getTuple_values()) 
            content += val.getValue().toString() + " ";
        
        AddToFile(path_name, content);
    }

    /* add some data to the data.txt */
    static public void AddToFile(String file_name, String file_content) 
    {  
        try 
        {
            File myFilePath = new File(file_name);
            file_content = readFile(file_name) + file_content;
            
            if (!myFilePath.exists())            
                myFilePath.createNewFile();  
            
            FileWriter resultFile = new FileWriter(myFilePath);  
            PrintWriter myFile = new PrintWriter(resultFile);  
            myFile.println(file_content);
            
            resultFile.close();
        }
        catch (Exception e) 
        {  
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "File creation error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();  
        }
    }
    
    /* create the attribute.txt file */
    public static void AddAttribute(String file_name, Table table) throws Exception 
    {  
        File myFilePath = new File(file_name);
        
        if ( !myFilePath.exists() ) 
            myFilePath.createNewFile();

        OutputStream out = new FileOutputStream(myFilePath);
        ObjectOutputStream oos = new ObjectOutputStream(out) ;  
        oos.writeObject(table);
        oos.close();
    }  
    
    /* to see if the information in the attibute.txt is right */
    public static void TestPrint(String file_name, Table table) throws Exception 
    {  
        File f = new File(file_name);
        InputStream input = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(input);
        
        Table obj = (Table)ois.readObject();
        ois.close();
        
        String show_message = "Table name of the attibute.txt is: " + obj.getTable_name();
        
        System.out.println(show_message);
    }
}
