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
public class ReadEclipse {
    public static List<String> read(String input, List<String> importLISTS) {
        int flag = 0;
        List<String> list = new ArrayList<>();
              
        try {
            String[] splits = input.split(System.getProperty("line.separator"));
            for (int i = 0; i < splits.length; i++) {
                String line = splits[i];
                if (line.contains("import")) {
                    //System.out.println(i+"   "+line);
                    for (int a = 0; a < importLISTS.size(); a++) {
                        if (line.toLowerCase().contains(importLISTS.get(a)) 
                                && line.toLowerCase().contains(".internal.")
                                ) {
                            list.add(line);
                           // System.out.println("  wow...... have found another1 = "+line);
                            flag = 1;
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
