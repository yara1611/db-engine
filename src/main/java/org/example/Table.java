package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Table{
    private final String name;
    private final List<Column> columns;
    private final List<Row> rows;


    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = List.copyOf(columns);
        this.rows = new ArrayList<>();
    }

    public List<Row> getAllRows() {
        return List.copyOf(rows);
    }

    public List<Column> getColumns(){
        return columns;
    }

    //insert row
    //delete row
    //edit row
    public void insertRow(Map<String, Object>data){
        validateRow(data);
        rows.add(new Row(data));
    }

    //validate column has value, types match
    private void validateRow(Map<String, Object>data){
        for (Column col:columns){
            if(!data.containsKey(col.getName())){
                throw new IllegalArgumentException(
                        "Missing value for column: " + col.getName()
                );
            }
            Object value = data.get(col.getName());
            if(col.getType().equals("INT") && !(value instanceof Integer) ){
                throw new IllegalArgumentException(
                        "Column " + col.getName() + " expects INT"
                );
            }
            if(col.getType().equals("VARCHAR") && !(value instanceof String) ){
                throw new IllegalArgumentException(
                        "Column " + col.getName() + " expects VARCHAR"
                );
            }
        }
    }

    public void deleteRow(String col,Object val){
        rows.removeIf(r->r.get(col).equals(val));
    }

    public List<Row> getWhere(String col,Object val){
        return rows
                .stream()
                .filter(r->r.get(col).equals(val))
                .collect(Collectors.toList()); //why collect?
    }
}

