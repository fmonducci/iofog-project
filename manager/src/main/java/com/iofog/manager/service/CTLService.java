package com.iofog.manager.service;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class CTLService {

    private static String deployCLI = "iofogctl deploy -f ";
    private static String describeCLI = "iofogctl describe ";

    public static String deploy(String filePath) throws IOException, InterruptedException {
        String cmd = deployCLI + filePath;
        Runtime run = Runtime.getRuntime();
        Process proc = run.exec(cmd);
        proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));
        // Read the output from the command
        String s = null;
        String output = "";
        while ((s = stdInput.readLine()) != null) {output+=s;}

        // Read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {output+=s;}
        System.out.println(output);

        return output;
    }

    public static String describe(String type, String name) throws IOException, InterruptedException {
        String cmd = describeCLI + type + " " + name;
        Runtime run = Runtime.getRuntime();
        Process proc = run.exec(cmd);
        proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));
        // Read the output from the command
        String s = null;
        String output = "";
        while ((s = stdInput.readLine()) != null) {output+=s;}

        // Read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {output+=s;}
        System.out.println(output);

        return output;
    }

}
