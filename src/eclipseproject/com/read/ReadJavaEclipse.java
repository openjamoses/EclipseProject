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
public class ReadJavaEclipse {

    static int totalcount = 0;
    static int totalFile = 0;
    static List<String> import_name_list = new ArrayList<>();

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
    public static List<String> count(String project, int status, List<String> importLists, String dir, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        if (status == 0) {
            totalcount = 0;
            totalFile = 0;
            import_name_list = new ArrayList<>();

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

            if (totalFile % 500 == 0) {
                System.out.println("     " + totalFile);
            }

            for (Object jsonObj : jsonArray) {
                //if (totalcount == 0) {

                JSONObject jsonObject = (JSONObject) jsonObj;
                String name = (String) jsonObject.get("name");
                String sha = (String) jsonObject.get("sha");
                //long size = (long) jsonObject.get("size");
                String path = (String) jsonObject.get("path");
                String type = (String) jsonObject.get("type");
                String download_url = (String) jsonObject.get("download_url");
                //System.out.println("          " + download_url+"\t"+type);
                //String shaa = (String) jsonObject.get("sha");
                if (type.equals("dir")) {
                    System.err.println(" loop");
                    count(project, 1, importLists, path, tockens, ct);

                } else {
                    totalFile++;
                    if (name.contains(".java")) {
                        if (ct == (tockens.length)) {/// the the index for the tokens array...
                            ct = 0; //// go back to the first index......
                        }

                        String file_java = Call_URL.callURL(download_url + "?access_token=" + tockens[ct++]);
                        List<String> lists = ReadEclipse.read(file_java, importLists);
                        if (lists.size() > 0) {
                            totalcount++;

                            for (int j = 0; j < lists.size(); j++) {
                                import_name_list.add(lists.get(j));
                            }
                            System.out.println("        " + totalcount + " : " + lists.size());
                        }

                    }
                }
            }/// *** End of JSon Object.....  
        }
        p++;//// Goto the next Page.......
        // } /// ******** End of while loop ......
        Set<String> setImports = new HashSet<>();
        setImports.addAll(import_name_list);
        List<String> list_imports = new ArrayList<>();
        list_imports.addAll(setImports);
        list_imports.add(ct+"");
        return list_imports;
    }
}
