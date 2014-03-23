package kickassdb;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/* Special helper class for data validations. This class will help
   reduce the code in the CUP file by calling methods declared here.
*/
public class Validations
{
    public static boolean validateInsertingTuple(ArrayList<String> attName, ArrayList<String> valType, Table t)
    {
        ArrayList<String> attType = new ArrayList<String>();
        int index = 0;
       
        /* get the types of the attributes */
        for ( String name : attName )
            attType.add(t.getAttType(name));
       
        if(attType.size() == 0) 
        {
            for( Attribute att : t.getTable_domain() )
                attType.add(att.getType().toString());
        }
       
        /* check if the attribute names are valid */
        boolean flag = false;       //if flag is set to false, it means the attribute name doesn't exist in the table
        for( String  name : attName) 
        {
            flag = false;
            for(Attribute att : t.getTable_domain()) 
            {
                if (att.getAttribute_name().equals(name)) 
                {
                    flag = true;
                    break;
                }
            }
            if(!flag) 
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Attribute " + name + " doesn't exist in the table!", "Error", JOptionPane.ERROR_MESSAGE);                                         
                return false;
            }
        }

 
        /* check if the two array sizes match */
        if(attType.size() != valType.size()) 
        {
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "The number of the records doesn't match with the attributes!", "Error", JOptionPane.ERROR_MESSAGE);                                         
            return false;
        }
       
        /* check if the data types are valid */
        for( String s : attType) 
        {
            if(valType.get(index).equals(s))
                index ++;
            else 
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Invalid data type!", "Error", JOptionPane.ERROR_MESSAGE);                                         
                return false;
            }
        }
        return true;
    }
   
    public static boolean validateColumnSize(Tuple tuple) 
    {
        for(Value value : tuple.getTuple_values()) 
        {
            int len = value.getValue().toString().length();
            System.out.println("len: " + len);
            if (len > 40) 
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Can't exceed the max size 40!", "Error", JOptionPane.ERROR_MESSAGE);                                         
                return false;
            }
        }
        return true;
    }
   
    public static boolean validatePrimaryKey(Tuple tuple, Table t)
    {
        int pk_index = 0;
        Attribute tmp_pk = t.getPrimary_key();
       
        /* find out the index of the primary key in the table */
        for ( Attribute att : t.getTable_domain() ) 
        {
            if (att.getAttribute_name().equals(tmp_pk.getAttribute_name()))
                break;
            else
                pk_index++;
        }
       
        /* contrast the tuple with the existed tuples to see if the primary key already existed */
        Object cur_pk = tuple.getValue(pk_index).getValue();
        if (cur_pk.equals("") ) 
        {
            JOptionPane.showMessageDialog(KickAssDB.mainwindow, "Primary key can't be null!", "Error", JOptionPane.ERROR_MESSAGE);                                         
            return false;
        }
        for ( Tuple tmp_tuple : t.getTable_tuples() ) 
        {
           
            Object existed_pk = tmp_tuple.getValue(pk_index).getValue();
           
            if (existed_pk.equals(cur_pk)) 
            {
                JOptionPane.showMessageDialog(KickAssDB.mainwindow, "primary key already existed: " + existed_pk, "Error", JOptionPane.ERROR_MESSAGE);                                         
                return false;
            }
        }

        return true;
    }
}