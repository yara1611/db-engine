package org.example;

import java.util.List;
import java.util.Set;

public class Parser{

    public Query parse(List<String> tokens){
        String firstToken = tokens.get(0).toUpperCase();
        return  switch (firstToken){
            case "SELECT" -> parseSelect(tokens);
            case "INSERT" -> parseInsert(tokens);
            case "DELETE" -> parseDelete(tokens);
            case "CREATE" -> parseCreate(tokens);
            default -> throw new IllegalArgumentException("Unknown keyword: " + firstToken);
        };
    }

    //SELECT col,col FROM table WHERE col=value;
    private Query parseSelect(List<String>tokens){
        Query query = new Query();
        String state  = "COLUMNS"; //starting state
        query.keyword="SELECT";

        for(int i = 1; i<tokens.size(); i++) //at i=1 to skip SELECT
        {
            String raw = tokens.get(i);
            String token = raw.toUpperCase();

            switch (token) {
                case "FROM":
                    state = "TABLE";
                    continue;
                case "WHERE":
                    state = "CONDITION";
                    continue;
                case ";":
                    break;
            }
            switch (state){
                case "COLUMNS"->{
                    if(!token.equals(",")) query.columns.add(raw);
                }
                case "TABLE" -> {
                    if (!token.equals(";")) query.table = raw;
                }
                case "CONDITION" -> query.rawWhere.add(raw);
            }
        }
        if (query.rawWhere.size() == 4) {
            System.out.println(query.rawWhere);
            query.condition = new Condition(
                    query.rawWhere.get(0),           // column
                    query.rawWhere.get(1),           // operator
                    parseValue(query.rawWhere.get(2)) // value
            );
            System.out.println(query.condition.column+" "+query.condition.value);
        }
        return query;
    }

    //INSERT INTO tableName (col,col) VALUES (val,val)
    private Query parseInsert(List<String>tokens){
        Query query = new Query();
        query.keyword="INSERT";

        String state = "";
        for (int i = 1; i < tokens.size(); i++) {
            String raw = tokens.get(i);
            String token = raw.toUpperCase();
            //System.out.println(token+" "+state);
            if(token.equals("INTO")){
                state = "TABLE";
                continue;
            } else if (token.equals("(")) {
                if (state.equals("VALUES")) {
                    continue;
                } else {
                    state = "COLUMNS";
                    continue;
                }
            } else if (token.equals("VALUES")) {
                state="VALUES";
                continue;
            } else if (token.equals(";")) { //end of query
                break;
            }


            switch (state){
                case "COLUMNS"->{
                    if(!token.equals(",") && !token.equals(")")) query.columns.add(raw);
                }
                case "TABLE" -> query.table = raw;
                case "VALUES" -> {
                    //can use a set for the skipped characters
                    if(!token.equals(",") && !token.equals(")") && !token.equals("(") && !token.equals(";")){
                        query.values.add(parseValue(raw)); //need to parseValues for  types
                    }

                }
            }
        }
        return query;
    }

    //DELETE FROM table_name WHERE condition;
    private Query parseDelete(List<String>tokens){
        Query query = new Query();
        query.keyword="DELETE";
        String state = "";
        for (int i = 1; i < tokens.size(); i++) {
            String raw = tokens.get(i);
            String token = raw.toUpperCase();
            if (token.equals("FROM")){
                state="TABLE";
                continue; //how does continue work here
            } else if(token.equals("WHERE")){
                state="CONDITION";
                continue;
            } else if (token.equals(";")) {
                break;
            }
            switch (state){
                case "TABLE" -> query.table = raw;
                case "CONDITION" -> query.rawWhere.add(raw);
            }
        }
        //only supports one WHERE condition at the moment
        if (query.rawWhere.size() == 3) {
            System.out.println(query.rawWhere);
            query.condition = new Condition(
                    query.rawWhere.get(0),           // column
                    query.rawWhere.get(1),           // operator
                    parseValue(query.rawWhere.get(2)) // value
            );
            System.out.println(query.condition.column+" "+query.condition.value);
        }
        return query;
    }
    //CREATE TABLE name (col type const); add const later
    private Query parseCreate(List<String>tokens){
        Query query = new Query();
        Set<String> datatypes= Set.of( "INT","VARCHAR");
        query.keyword="CREATE";
        String state = "";
        String currentCol ="";

        for (int i = 1; i < tokens.size(); i++) {

            String raw = tokens.get(i);
            String token = raw.toUpperCase();


            if(token.equals("TABLE")){
                state="TABLE";
                continue;
            }
            else if(token.equals("(") || token.equals(",")){
                state="COLUMNS";
                continue;
            }
            else if (token.equals(")") || token.equals(";")) {
                break;
            }
            switch (state){
                case "COLUMNS"->{
                   currentCol = raw;
                   state="DATATYPE";
                }
                case "TABLE" -> query.table = raw;
                case "DATATYPE" ->{
                    System.out.println("adding col: " + currentCol + " type: " + raw);
                    query.columns.add(currentCol);
                    query.dataTypes.add(raw);
                    state="COLUMNS";
            }
            }

        }
        return query;
    }

    //returns value  in correct type
    private Object parseValue(String raw){
        try{
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return raw.replace("'", ""); // strip quotes from strings
        }
    }
}



