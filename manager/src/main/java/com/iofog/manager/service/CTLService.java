package com.iofog.manager.service;

import com.fasterxml.jackson.core.JsonFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;


import java.io.*;

public class CTLService {

    private static String deployCLI = "iofogctl deploy -f ";
    private static String describeCLI = "iofogctl describe ";
    private static String basePath = "/home/fogmaster/iofog-resources/components/";
    private static int count = 0;

    public static String deploy(String data) throws IOException, InterruptedException {
        String cmd = deployCLI + CTLService.getFile(data);
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

    private static String getFile(String data) throws IOException {
        ObjectMapper jsonReader = new ObjectMapper(new JsonFactory());
        Object obj = jsonReader.readValue(data, Object.class);
        String name = basePath+"component"+count+".yaml";
        count++;
        ObjectMapper yamlWriter = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        yamlWriter.writeValue(new File(name), obj);
        return name;
    }


}
