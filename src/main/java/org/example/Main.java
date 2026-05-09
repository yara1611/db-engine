package org.example;

import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Database db = new Database();
        db.createTable("users",List.of(
                new Column("id","INT"),
                new Column("name","varchar")
        ));

        db.insertRow("users", Map.of("id", 1, "name", "yara"));
        db.insertRow("users", Map.of("id", 2, "name", "sara"));
        db.insertRow("users", Map.of("id", 3, "name", "layla"));

        Executor executor = new Executor(db);
        //TODO id constraint and auto id
        //TODO queryengine class
        var tokens = executor.getTokens("INSERT INTO users (id,name) VALUES (2,'yara');");
        var query = executor.executeParser(tokens);
        //var results = executor.executeQuery(query);
        //results.forEach(System.out::println);
        //db.getTable("users").deleteRow(3);
        var tokens2 = executor.getTokens("DELETE FROM users WHERE id = 3;");
        var query2 = executor.executeParser(tokens2);
        var rows = db.getAllRows("users");
        rows.forEach(System.out::println);
        //System.out.println(query2.rawWhere);
        var results2 = executor.executeQuery(query2);
        results2.forEach(System.out::println);
        rows = db.getAllRows("users");
        rows.forEach(System.out::println);

    }
}




