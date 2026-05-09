package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
* keyword = ["SELECT","INSERT INTO","FROM","VALUES","CREATE","WHERE"]
* identifier = [table,id,column]
* Select * from table {optional} where col = val ;
* Insert into table (col1,col2) values (val1,val2)
* */
/*
* //I type this
db.query("SELECT * FROM users WHERE id = 1");

// executor (class) does this
// 1. sends string to parser → gets Query object
// 2. looks at query.keyword → "SELECT"
// 3. gets all rows from the table -> db.getTable(table).getAllrows()
// 4. filters by WHERE condition
// 5. returns matching rows
* */
public class Executor {
    //depencedcy onjecton later
    Tokenizer tokenizer = new Tokenizer();
    Parser parser = new Parser();
    private final Database db;
    public Executor(Database db){
        this.db=db;
    }
    public List<String> getTokens(String query){
        return tokenizer.tokenize(query);
    }
    public Query executeParser(List<String> tokens){
        return parser.parse(tokens);
    }

    public List<Row> executeQuery(Query query){
        return switch (query.keyword){
            case "SELECT" -> executeSelect(query);
            case "INSERT" -> {executeInsert(query); yield List.of();}
            case "DELETE" -> {executeDelete(query); yield List.of();}
            case "CREATE"->{executeCreate(query); yield List.of();}//return empty list instead of null
            default -> throw new IllegalArgumentException("Unknown keyword: " + query.keyword);
        };
    }

    private void executeCreate(Query query){
        var cols = query.columns;
        List<Column> list = new ArrayList<Column>();
        for (int i = 0; i < cols.size(); i++) {
            list.add(new Column(query.columns.get(i),query.dataTypes.get(i)));
        }
       db.createTable(query.table,list);
        System.out.println("TABLE CREATED: "+query.table);
    }

    private List<Row> executeSelect(Query query){

        if(query.rawWhere.isEmpty())
            return db.getAllRows(query.table);
        else {
            return db.getWhere(query.table, query.condition.column,query.condition.value);
        }
    }

    private void executeDelete(Query query){
        System.out.println("Quert"+query.condition.column);
        db.deleteRow(query.table, query.condition.column,query.condition.value);
    }

    private void executeInsert(Query query){
        Map<String, Object> row = new HashMap<>();

        for (int i = 0; i < query.columns.size(); i++) {

            row.put(query.columns.get(i),query.values.get(i));
        }
        db.insertRow(query.table, row);
    }

}
