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
import org.w3c.dom.Document;

/**
 *
 * @author john
 */
public class ShaaDetails_Files {

    /**
     *
     * @param project
     * @param imports_list
     * @param shaa
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static List<String> details(String project, List<String> imports_list, String shaa, String[] tockens, int ct) throws ParseException {
        List<String> lists_set = new ArrayList<>();
        
        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            String login = "login######";
            if ((JSONObject) jSONObject.get("author") != null) {
                JSONObject authorOBJ = (JSONObject) jSONObject.get("author");
                login = (String) authorOBJ.get("login");

            }
            if ((JSONArray) jSONObject.get("files") != null) {
                JSONArray fileObj = (JSONArray) jSONObject.get("files");
                //System.out.println("      Files: "+fileObj.size());
                for (int i = 0; i < fileObj.size(); i++) {
                    JSONObject fileOBJ = (JSONObject) fileObj.get(i);
                    String fileName = (String) fileOBJ.get("filename");
                    
                    if (i%50 == 0 && i > 0) {
                       // System.out.println("    "+i+" \t FILE: "+fileName);
                    }
                    
                    if (fileName.contains(".java")) {
                        if (ct >= (tockens.length)) {/// the the index for the tokens array...
                            ct = 0; //// go back to the first index......
                        }
                        String url_string = Call_URL.callURL((String) fileOBJ.get("contents_url") + "&access_token=" + tockens[ct++]);
                        if (JSONUtils.isValidJSONObject(url_string)) {
                            JSONObject url_obj = (JSONObject) parser.parse(url_string);
                            if (ct >= (tockens.length)) {/// the the index for the tokens array...
                                ct = 0; //// go back to the first index......
                            }
                            
                            String files_ = Call_URL.callURL((String) url_obj.get("download_url") + "?access_token=" + tockens[ct++]);
                            
                            List<String> lists = ReadImportFile.read2(files_, imports_list);
                            
                            if (lists.size() > 0) {
                                for (int a = 0; a < lists.size(); a++) {
                                    lists_set.add(lists.get(a));
                                }
                                System.out.println("     "+lists.size());
                            }
                        }

                    }

                }
            }
        }
        lists_set.add(ct+"");
        
        return lists_set;
    }
    
    
    public static List<String> details2(String project, String shaa, String[] tockens, int ct) throws ParseException {
        List<String> lists_files = new ArrayList<>();
        String seperator = "########";
        
        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            String login = "login######";
            if ((JSONObject) jSONObject.get("author") != null) {
                JSONObject authorOBJ = (JSONObject) jSONObject.get("author");
                login = (String) authorOBJ.get("login");

            }
            if ((JSONArray) jSONObject.get("files") != null) {
                JSONArray fileObj = (JSONArray) jSONObject.get("files");
                //System.out.println("      Files: "+fileObj.size());
                for (int i = 0; i < fileObj.size(); i++) {
                    JSONObject fileOBJ = (JSONObject) fileObj.get(i);
                    String fileName = (String) fileOBJ.get("filename");
                    String status_ = (String) fileOBJ.get("status");
                    String previous_file = "-";
                    if (fileOBJ.get("previous_filename") != null) {
                        previous_file = (String) fileOBJ.get("previous_filename");
                    }
                    
                    lists_files.add(fileName+seperator+status_+seperator+previous_file);
                    //String previous_filename = (String) fileOBJ.get("previous_filename");
                    //String status_ = (String) fileOBJ.get("status");
                    
                    if (i%50 == 0 && i > 0) {
                       // System.out.println("    "+i+" \t FILE: "+fileName);
                    }
                    
                    

                }
            }
        }
        lists_files.add(ct+"");
        
        return lists_files;
    }
}
