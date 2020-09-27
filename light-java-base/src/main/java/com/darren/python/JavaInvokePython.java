package com.darren.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-09-27 20:02
 * Desc   :  *
 */
public class JavaInvokePython {
    public static void main(String[] args) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python /Users/eric/PythonHelloWorld.py data.log file.log");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            int res = proc.waitFor();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
