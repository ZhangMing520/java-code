package com.example.works.diff;

// Copyright 2010 Google Inc. All Rights Reserved.

/**
 * Diff Speed Test
 *
 * Compile from diff-match-patch/java with:
 * javac -d classes src/name/fraser/neil/plaintext/diff_match_patch.java tests/name/fraser/neil/plaintext/Speedtest.java
 * Execute with:
 * java -classpath classes name/fraser/neil/plaintext/Speedtest
 *
 * @author fraser@google.com (Neil Fraser)
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Speedtest {

    public static void main(String args[]) throws IOException {
        String text1 = readFile("E:\\workspaces\\ideaSpaces\\IDEAProjects\\forjava\\src\\main\\java\\com\\demo\\works\\diff\\Speedtest1.txt");
        String text2 = readFile("E:\\workspaces\\ideaSpaces\\IDEAProjects\\forjava\\src\\main\\java\\com\\demo\\works" +
                "\\diff\\Speedtest2.txt");

        diff_match_patch dmp = new diff_match_patch();
        dmp.Diff_Timeout = 0;

        // Execute one reverse diff as a warmup.
        LinkedList<diff_match_patch.Diff> diffs = dmp.diff_main(text1, text2, true);

        System.out.println(diffs);
    }

    private static String readFile(String filename) throws IOException {
        // Read a file from disk and return the text contents.
        StringBuilder sb = new StringBuilder();
        FileReader input = new FileReader(filename);
        BufferedReader bufRead = new BufferedReader(input);
        try {
            String line = bufRead.readLine();
            while (line != null) {
                sb.append(line).append('\n');
                line = bufRead.readLine();
            }
        } finally {
            bufRead.close();
            input.close();
        }
        return sb.toString();
    }
}
