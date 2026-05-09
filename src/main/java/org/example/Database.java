package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    //insert row
    public void insertRow(String tableName, Map<String, Object> data){
        getTable(tableName).insertRow(data);
    }
    //delete row

    //update row

    //get row

    //get all rows
    public List<Row> getAllRows(String tableName){
      return  getTable(tableName).getAllRows();
    };

    public List<Row> getWhere(String tableName,String col,Object val){
        return getTable(tableName).getWhere(col,val);
    }

    public void deleteRow(String tableName,String col,Object val){
        getTable(tableName).deleteRow(col,val);
    }
    public Table getTable(String name){
        Table table = tables.get(name);
        if(table==null){
            throw new IllegalArgumentException("Table not found: "+name);
        }
      return  table;
    };
}
