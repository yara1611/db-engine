package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP hello
        Database db = new Database();
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        Executor executor = new Executor(db);
        db.createTable("users",List.of(
                new Column("id","INT"),
                new Column("name","varchar")
        ));

        db.insertRow("users", Map.of("id", 1, "name", "yara"));
        db.insertRow("users", Map.of("id", 2, "name", "sara"));
        db.insertRow("users", Map.of("id", 3, "name", "layla"));


        //TODO id constraint and auto id
        //TODO queryengine class
        //todo drop table and create db
        //todo update

        Scanner sc = new Scanner(System.in);
        System.out.println("db-engine v0.1 — type SQL or 'exit'");
        while (true){
            System.out.print("db> ");
            String sql = sc.nextLine().trim();

            if(sql.equals("exit")) break;
            if(sql.isEmpty())continue;
            try{
                var tokens = tokenizer.tokenize(sql);
                var query = parser.parse(tokens);
                List<Row> results = executor.executeQuery(query);
                if(results.isEmpty())System.out.println("OK");
                else results.forEach(System.out::println);
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}




