package com.darren.shell;

import java.io.IOException;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-09-29 21:27
 * Desc   :  *
 */
public class JavaInvokeShell {
    public static void main(String[] args) {
        Process proc;
        try {
//            proc = Runtime.getRuntime().exec("timeout 10s sh /Users/eric/whileTimeOut.sh");
//            System.out.println(proc);
            proc = Runtime.getRuntime().exec("sh /Users/eric/whileTimeOut.sh /Users/eric/data.log /Users/eric/file.log & { sleep 10; kill $! & }");
//            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            in.close();
            int res = proc.waitFor();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
