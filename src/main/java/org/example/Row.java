package org.example;

import java.util.HashMap;
import java.util.Map;

public class Row{
    //map<col_name,data>
    private final Map<String,Object> data;

    public Row(Map<String, Object> data) {
        this.data = new HashMap<>(data);
    }
    //get the column
    public Object get(String column){
        return data.get(column);
    }
    //get the data

    public Map<String, Object> getData() {
        return Map.copyOf(data);//leh?copyOf
    }

    //tostring
    @Override
    public  String toString(){
        return data.toString();
    }
}

