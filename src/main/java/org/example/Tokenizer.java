package org.example;

import java.util.ArrayList;
import java.util.List;


//split yhr class later to tokenizer and parser
public class Tokenizer {
    public boolean isDelimiter(char ch) {
        return " +-*/ ,;><=()[]{}".indexOf(ch) != -1;
    }

    //lexcial analysis
    public List<String> tokenize(String str){
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for(char c : str.toCharArray()){
            if(isDelimiter(c)){
                if (!current.isEmpty()) tokens.add(current.toString());
                if (c != ' ') tokens.add(String.valueOf(c)); // Add symbol if not space
                current.setLength(0);
            }
            else{
                current.append(c);
            }
        }
        return tokens;
    }


}

