/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.read;

import eclipseproject.com.core.Call_URL;
import eclipseproject.com.core.JSONUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class ReadJavaFile {

    /**
     *
     * @param project
     * @param importLists
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    static int totalcount = 0;
    static int totalFiles = 0;
    static int totalJava = 0;
    static String files = "";
    static String downloads = "";

    public static String count(String project, int status,List<String> importLists, String dir, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        if (status == 0) {
            totalcount = 0;
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
            totalFiles += jsonArray.size();
            if (totalFiles % 500 == 0) {
                System.out.println("     " + totalFiles);
            }

            if (totalFiles >= 351000) {
                return totalcount + "XXXXXXXX" + totalFiles + "XXXXXXXX" + totalJava + "XXXXXXXX" + files + "XXXXXXXX" + downloads + "XXXXXXXX" + ct;
            }
            for (Object jsonObj : jsonArray) {
                if (totalcount == 0) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    String name = (String) jsonObject.get("name");
                    String path = (String) jsonObject.get("path");
                    String type = (String) jsonObject.get("type");
                    String download_url = (String) jsonObject.get("download_url");
                    //System.out.println("          " + download_url+"\t"+type);
                    //String shaa = (String) jsonObject.get("sha");
                    if (type.equals("dir")) {
                        System.err.println(" loop");
                        count(project,1, importLists, path, tockens, ct);

                    } else {
                        if (name.contains(".java")) {
                            if (ct == (tockens.length)) {/// the the index for the tokens array...
                                ct = 0; //// go back to the first index......
                            }
                            totalJava++;
                            String file_java = Call_URL.callURL(download_url + "?access_token=" + tockens[ct++]);
                            int values = ReadImportFile.read(file_java, importLists);
                            totalcount += values;
                            if (values > 0) {
                                downloads = downloads.concat(download_url + "###");
                                files = files.concat(name + "###");
                                //tobe removed..!
                                return totalcount + "XXXXXXXX" + totalFiles + "XXXXXXXX" + totalJava + "XXXXXXXX" + files + "XXXXXXXX" + downloads + "XXXXXXXX" + ct;

                                //System.out.println(values+" : "+download_url);
                                //System.out.println(counts+" : "+name);
                            }

                        }
                    }
                }
            }/// *** End of JSon Object.....  
        }
        p++;//// Goto the next Page.......
        // } /// ******** End of while loop ......
        return totalcount + "XXXXXXXX" + totalFiles + "XXXXXXXX" + totalJava + "XXXXXXXX" + files + "XXXXXXXX" + downloads + "XXXXXXXX" + ct;
    }
}
