package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database{
    //name->table
    private final Map<String,Table> tables;
    public Database(){
        this.tables=new HashMap<>();
    }

    public void createTable(String name, List<Column> columns){
        //validate it doesnt exist
        if(tables.containsKey(name)){
            new IllegalArgumentException("Table already exists: "+name);
        }

        tables.put(name,new Table(name, columns));
    }
}
