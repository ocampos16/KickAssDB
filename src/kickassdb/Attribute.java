package kickassdb;

import java.io.Serializable;
import java.util.Hashtable;

public class Attribute  implements Serializable
{

    private Type type;
    private IndexType indexType;
    private String attribute_name;
    private int attribute_size;
        
    private BTree bTree;
    private Hashtable hashTable;
    
    public enum Type 
    {
        INTEGER, VARCHAR
    }
    
    public enum IndexType
    {
        TREE_TYPE_INDEXING, HASH_TYPE_INDEXING, NULL
    }
    
    public Attribute()
    {
       indexType = IndexType.NULL;
    }
    
    public Attribute(String n, Type t, int size)
    {     
        this.attribute_name = n;
        this.type = t;
        this.attribute_size = size;
        
        indexType = IndexType.NULL;
    }
    
    public Type getType() 
    {
        return type;
    }

    public void setType(Type type) 
    {
        this.type = type;
    }

    public String getAttribute_name() 
    {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) 
    {
        this.attribute_name = attribute_name;
    }
            
    public int getAttributeSize()
    {
        return attribute_size;
    }
    
    public void setAttributeSize(int v)
    {
        attribute_size = v;
    }
    
    public void setIndexType(IndexType it)
    {
        indexType = it;
        
        if ( it == IndexType.TREE_TYPE_INDEXING )        
            bTree = new BTree();                             
        else if ( it == IndexType.HASH_TYPE_INDEXING )
            hashTable = new Hashtable();
        
    }
    
    public IndexType getIndexType()
    {
        return indexType;
    }
    
    public void setBTree(BTree bt)
    {
        bTree = bt;
    }
    
    public BTree getBTree()
    {
        return bTree;
    }
    
    public void setHashTable(Hashtable ht)
    {
        hashTable = ht;
    }
    
    public Hashtable getHashTable()
    {
        return hashTable;
    }
    
    @Override
    public String toString(){
    
        return this.attribute_name;
    
    }    
    
}
