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
public class ReadJavaClassFile {
    public static List<List<String>> read(String input, List<String> importLISTS) {
        int flag = 0;
        int total_interfaces = 0;
        int total_interfaces_internal = 0;
        List<String> interfaces = new ArrayList<>();
        List<String> interfaces_internal = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        try {
            String[] splits = input.split(System.getProperty("line.separator"));
            for (int i = 0; i < splits.length; i++) {
                String line = splits[i];
                if (line.contains("import")) {
                    //System.out.println(i+"   "+line);
                    for (int a = 0; a < importLISTS.size(); a++) {
                        if (line.toLowerCase().contains(importLISTS.get(a))){
                            total_interfaces ++;
                            interfaces.add(line);
                            if (line.toLowerCase().contains(".internal.")) {
                                total_interfaces_internal ++;
                                interfaces_internal.add(line);
                                //System.out.println("  wow...... have found another1 = "+line);
                            }
                        } 
                                
                               
                    }
                }
                if (line.contains("class")) {
                    //System.out.println(i+"   "+line);
                    flag = 1;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lists.add(interfaces);
        lists.add(interfaces_internal);
        return lists;
    }
}
