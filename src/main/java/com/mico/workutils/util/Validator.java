package com.mico.workutils.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Stack;

public class Validator {
    public static class Pair {

        private Integer leftLine;
        private Integer rightLine;


        public Integer getLeftLine() {
            return leftLine;
        }

        public void setLeftLine(Integer leftLine) {
            this.leftLine = leftLine;
        }

        public Integer getRightLine() {
            return rightLine;
        }

        public void setRightLine(Integer rightLine) {
            this.rightLine = rightLine;
        }
    }

    public static boolean isPair(Stack<Pair> sc, String s, String start, String end) {
        BufferedReader br = new BufferedReader(new StringReader(s));
        Stack<String> pairs = new Stack<String>();
        try {
            String line = br.readLine();
            Pair pair;
            Integer lineNumber = 1;
            while (null != line) {
                line = line.trim();
                pair = new Pair();
                if (line.startsWith(start)) {
                    pairs.push(start);
                    pair.setLeftLine(lineNumber);
                    sc.push(pair);
                }
                if (line.startsWith(end)) {
                    if(pairs.size()>0){
                        pairs.pop();
                        sc.peek().setRightLine(lineNumber);
                    }
                    else
                        return false;
                }
                line = br.readLine();
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

} 
