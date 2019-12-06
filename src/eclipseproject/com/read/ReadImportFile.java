/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.read;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class ReadImportFile {
    /**
     * 
     * @param input
     * @param importLISTS
     * @return 
     */
    public static int read(String input, List<String> importLISTS) {
        int flag = 0;
        try {
            String[] splits = input.split(System.getProperty("line.separator"));
            for (int i = 0; i < splits.length; i++) {
                String line = splits[i];
                if (line.contains("import")) {
                    //System.out.println(i+"   "+line);
                    for (int a = 0; a < importLISTS.size(); a++) {
                        if (line.contains(importLISTS.get(a)) 
                                //&& line.toLowerCase().contains(".internal.")
                                ) {
                            flag = 1;
                            break;
                        }
                    }
                    
                    if (flag == 1) {
                       break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
    
    public static List<String> read2(String input, List<String> importLISTS) {
        int flag = 0;
        List<String> list = new ArrayList<>();
        try {
            String[] splits = input.split(System.getProperty("line.separator"));
            for (int i = 0; i < splits.length; i++) {
                String line = splits[i];
                if (line.contains("import")) {
                    //System.out.println(i+"   "+line);
                    for (int a = 0; a < importLISTS.size(); a++) {
                        if (line.contains(importLISTS.get(a)) 
                                //&& line.toLowerCase().contains(".internal.")
                                ) {
                            list.add(importLISTS.get(a));
                            //flag = 1;
                            //break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
