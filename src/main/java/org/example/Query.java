package org.example;

import java.util.ArrayList;
import java.util.List;

public class Query{
    String keyword;
    List<String> columns = new ArrayList<>();
    String table;
    List<String> rawWhere= new ArrayList<>();
    List<Object> values = new ArrayList<>();
    Condition condition;
}

class Condition {
    String column;   // "id"
    String operator; // "="
    Object value;    // 1

    public Condition(String column, String operator, Object value) {
        this.column=column;
        this.operator=operator;
        this.value=value;
    }
}
