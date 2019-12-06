/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.read;

import eclipseproject.com.core.Call_URL;
import eclipseproject.com.core.JSONUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class ReadJavaClass {
    
    static int totalcount = 0;
    static int totalFiles = 0;
    static int totalJava = 0;
    static int totalInterface = 0;
    static int totalInterfacesInternal = 0;
    
    static List<String> interfaces = new ArrayList<>();
       static List<String> interfaces_internal = new ArrayList<>();

    /**
     *
     * @param project
     * @param status
     * @param importLists
     * @param dir
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static String count(String project, int status, List<String> importLists, String dir, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        if (status == 0) {
            totalcount = 0;
            totalFiles = 0;
            totalJava = 0;
            totalInterface = 0;
            totalInterfacesInternal = 0;
            interfaces = new ArrayList<>();
            interfaces_internal = new ArrayList<>();
           
        }

        //while (true) {
        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = "";
        if (dir.equals("")) {
            jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/contents?access_token=" + tockens[ct++]);
        } else {
            jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/contents/" + dir + "?access_token=" + tockens[ct++]);
        }
        
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSON(jsonString) == true) {
            
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
            //System.out.println( "    "+p+" \t"+jsonArray.size());

            if (totalFiles % 500 == 0) {
                System.out.println("     " + totalFiles);
            }
            
            for (Object jsonObj : jsonArray) {
                //if (totalcount == 0) {

                JSONObject jsonObject = (JSONObject) jsonObj;
                String name = (String) jsonObject.get("name");
                String path = (String) jsonObject.get("path");
                String type = (String) jsonObject.get("type");
                String download_url = (String) jsonObject.get("download_url");
                //System.out.println("          " + download_url+"\t"+type);
                //String shaa = (String) jsonObject.get("sha");
                if (type.equals("dir")) {
                    System.err.println(" loop");
                    count(project, 1, importLists, path, tockens, ct);
                    
                } else {
                    totalFiles++;
                    if (name.contains(".java")) {
                        if (ct == (tockens.length)) {/// the the index for the tokens array...
                            ct = 0; //// go back to the first index......
                        }
                        totalJava++;
                        String file_java = Call_URL.callURL(download_url + "?access_token=" + tockens[ct++]);
                        List<List<String>> lists = ReadJavaClassFile.read(file_java, importLists);
                       
                        for (int j = 0; j < lists.get(0).size(); j++) {
                            interfaces.add(lists.get(0).get(j));
                        }
                        for (int j = 0; j < lists.get(1).size(); j++) {
                            interfaces_internal.add(lists.get(1).get(j));
                        }
                        if (lists.get(0).size() > 0) {
                            totalcount ++;
                        }
                        
                        System.out.println("        "+totalcount+" : "+lists.get(0).size()+"\t"+lists.get(1).size());
                    }
                }
                //}
            }/// *** End of JSon Object.....  
        }
        p++;//// Goto the next Page.......
        // } /// ******** End of while loop ......
        Set<String> interfaceSet = new HashSet<>();
        Set<String> interfaceinternalSet = new HashSet<>();
        interfaceSet.addAll(interfaces);
        interfaceinternalSet.addAll(interfaces_internal);
        
        totalInterface = interfaces.size();
        totalInterfacesInternal = interfaces_internal.size();
        
        int totalInterface_unique = interfaceSet.size();
        int interfaces_internal_unique = interfaceinternalSet.size();
        
        return totalcount + "XXXXXXXX" + totalFiles + "XXXXXXXX" + totalJava + "XXXXXXXX" + totalInterface + "XXXXXXXX" + totalInterfacesInternal+ "XXXXXXXX" + totalInterface_unique+ "XXXXXXXX" + interfaces_internal_unique + "XXXXXXXX" + ct;
    }
}
