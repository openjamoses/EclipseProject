/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.read;

import eclipseproject.com.core.Call_URL;
import eclipseproject.com.core.DateOperations;
import eclipseproject.com.core.JSONUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class CountCommits_Files {

    /**
     *
     * @param project
     * @param imports
     * @param first_date
     * @param last_date
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static List<List<String>> count(String project, List<String> imports, List<Integer> countL, String first_date, String last_date, String[] tockens, int ct) throws ParseException, java.text.ParseException {
        
        Set<String> comSets = new HashSet<>();
        Set<String> devSets = new HashSet<>();
        Set<String> datesSets = new HashSet<>();

        //Map<String, Set<String>> devMap = new HashMap<>();
        //Map<String, Set<String>> comMap = new HashMap<>();
        //Map<String, Set<String>> dateMap = new HashMap<>();
        
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        Set<String> shaaList = new HashSet<>();
        Set<String> devList = new HashSet<>();
        List<List<String>> allLists = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;

        while (true) {////loop thru the pagess....
            if (ct >= (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            if (!first_date.equals("")) {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            } else {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }

            //System.out.println("                https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE);
            String date = "date";
            String name = "name";
            String email = "name";
            String login = "login####";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                times += jsonArray.size();
                if (times % 500 == 0) {
                    System.out.println("     " + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) jsonObj;
                    String shaa = (String) jsonObject.get("sha");

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            email = (String) author_Obj.get("email");
                            date = (String) author_Obj.get("date");

                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            email = (String) commiterObj.get("email");

                        }

                    }

                    if ((JSONObject) jsonObject.get("author") != null) {
                        JSONObject author = (JSONObject) jsonObject.get("author");
                        login = (String) author.get("login");
                    }

                    List<String> list_sets = ShaaDetails_Files.details(project, imports, shaa, tockens, ct);
                    ct = Integer.parseInt(list_sets.get(list_sets.size() - 1));
                    list_sets.remove(list_sets.size() - 1);
                    Set<String> listSet = new HashSet<>();
                    listSet.addAll(list_sets);
                    List<String> lists = new ArrayList<>();
                    lists.addAll(listSet);
                    
                    for (int a = 0; a < lists.size(); a++) {
                        if (imports.contains(lists.get(a))) {
                            int index = imports.indexOf(lists.get(a));
                        }
                    }

                    for (int a = 0; a < imports.size(); a++) {
                        for (int b = 0; b < lists.size(); b++) {
                            if (imports.get(a).equals(lists.get(b))) {
                                //todo:::: ........................
                                comSets.add(shaa+"/"+lists.get(b));
                                devSets.add(email + "/" + login+"/"+lists.get(b));
                                datesSets.add(date+"/"+lists.get(b));
                            }
                        }
                    }
                    devList.add(email + "/" + login);
                    shaaList.add(shaa);

                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......

        List<String> details_dev = new ArrayList<>();
        List<String> details_com = new ArrayList<>();
        List<String> details_date = new ArrayList<>();
        
        details_dev.addAll(devSets);
        details_com.addAll(comSets);
        details_date.addAll(datesSets);
        
        Set<String> devSet = new HashSet<>();
        Set<String> comSet = new HashSet<>();
        List<String> dateL = new ArrayList<>();
        List<String> imports_details = new ArrayList<>();
        List<String> total_details = new ArrayList<>();
        

        for (int a = 0; a < imports.size(); a++) {
            double dev = 0, com = 0;
            for (int b = 0; b < details_dev.size(); b++) {
                String impt = details_dev.get(b).split("/")[2];
                if (impt.equals(imports.get(a))) {
                    dev ++;
                    devSet.add(details_dev.get(b).split("/")[0]+"/"+details_dev.get(b).split("/")[1]);
                }
            }
            for (int b = 0; b < details_com.size(); b++) {
                String impt = details_com.get(b).split("/")[1];
                if (impt.equals(imports.get(a))) {
                    com ++;
                    comSet.add(details_com.get(b).split("/")[0]);
                }
            }
            List<String> dates = new ArrayList<>();
            for (int b = 0; b < details_date.size(); b++) {
                String impt = details_date.get(b).split("/")[1];
                if (impt.equals(imports.get(a))) {
                    dates.add(details_date.get(b).split("/")[0]);
                    dateL.add(details_date.get(b).split("/")[0]);
                }
            }
            double weeks = 0;
            String minDate = "-", maxDate = "-";
            if (dates.size() > 0) {
                minDate = DateOperations.sorts(dates, dates).split("/")[0];
                maxDate = DateOperations.sorts(dates, dates).split("/")[1];
                weeks = Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]) / 7;
            }
            
            imports_details.add(com+"/"+dev+"/"+weeks+"/"+minDate+"/"+maxDate); 
        }
        double days = 0;
        double weeks = 0;
        String minDate = "-", maxDate = "-";
        if (dateL.size() > 0) {
            minDate = DateOperations.sorts(dateL, dateL).split("/")[0];
            maxDate = DateOperations.sorts(dateL, dateL).split("/")[1];
            weeks = Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]) / 7;
        }

        total_details.add(comSet.size()+"/"+devSet.size()+"/"+weeks+"/"+minDate+"/"+maxDate+"/"+shaaList.size()+"/"+devList.size());
        total_details.add(ct + "");
        
        allLists.add(imports_details);
        allLists.add(total_details);
        
        return allLists;
    }
    
    
    
    public static List<List<String>> count2(String project, List<String> imports, List<Integer> countL, String first_date, String last_date, String[] tockens, int ct) throws ParseException, java.text.ParseException {
        
        Set<String> comSets = new HashSet<>();
        Set<String> devSets = new HashSet<>();
        Set<String> datesSets = new HashSet<>();

        //Map<String, Set<String>> devMap = new HashMap<>();
        //Map<String, Set<String>> comMap = new HashMap<>();
        //Map<String, Set<String>> dateMap = new HashMap<>();
        
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        Set<String> shaaList = new HashSet<>();
        Set<String> devList = new HashSet<>();
        List<List<String>> allLists = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;

        while (true) {////loop thru the pagess....
            if (ct >= (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            if (!first_date.equals("")) {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            } else {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }

            //System.out.println("                https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE);
            String date = "date";
            String name = "name";
            String email = "name";
            String login = "login####";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                times += jsonArray.size();
                if (times % 500 == 0) {
                    System.out.println("     " + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) jsonObj;
                    String shaa = (String) jsonObject.get("sha");

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            email = (String) author_Obj.get("email");
                            date = (String) author_Obj.get("date");

                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            email = (String) commiterObj.get("email");

                        }

                    }

                    if ((JSONObject) jsonObject.get("author") != null) {
                        JSONObject author = (JSONObject) jsonObject.get("author");
                        login = (String) author.get("login");
                    }

                    List<String> list_sets = ShaaDetails_Files.details(project, imports, shaa, tockens, ct);
                    ct = Integer.parseInt(list_sets.get(list_sets.size() - 1));
                    list_sets.remove(list_sets.size() - 1);
                    Set<String> listSet = new HashSet<>();
                    listSet.addAll(list_sets);
                    List<String> lists = new ArrayList<>();
                    lists.addAll(listSet);
                    
                    for (int a = 0; a < lists.size(); a++) {
                        if (imports.contains(lists.get(a))) {
                            int index = imports.indexOf(lists.get(a));
                        }
                    }

                    for (int a = 0; a < imports.size(); a++) {
                        for (int b = 0; b < lists.size(); b++) {
                            if (imports.get(a).equals(lists.get(b))) {
                                //todo:::: ........................
                                comSets.add(shaa+"/"+lists.get(b));
                                devSets.add(email + "/" + login+"/"+lists.get(b));
                                datesSets.add(date+"/"+lists.get(b));
                            }
                        }
                    }
                    devList.add(email + "/" + login);
                    shaaList.add(shaa);

                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......

        List<String> details_dev = new ArrayList<>();
        List<String> details_com = new ArrayList<>();
        List<String> details_date = new ArrayList<>();
        
        details_dev.addAll(devSets);
        details_com.addAll(comSets);
        details_date.addAll(datesSets);
        
        Set<String> devSet = new HashSet<>();
        Set<String> comSet = new HashSet<>();
        List<String> dateL = new ArrayList<>();
        List<String> imports_details = new ArrayList<>();
        List<String> total_details = new ArrayList<>();
        

        for (int a = 0; a < imports.size(); a++) {
            double dev = 0, com = 0;
            String developers = "";
            String shaas = "";
            
            for (int b = 0; b < details_dev.size(); b++) {
                String impt = details_dev.get(b).split("/")[2];
                if (impt.equals(imports.get(a))) {
                    dev ++;
                    developers = developers.concat(details_dev.get(b).split("/")[0]+"!"+details_dev.get(b).split("/")[1]+",");
                    devSet.add(details_dev.get(b).split("/")[0]+"/"+details_dev.get(b).split("/")[1]);
                }
            }
            for (int b = 0; b < details_com.size(); b++) {
                String impt = details_com.get(b).split("/")[1];
                if (impt.equals(imports.get(a))) {
                    com ++;
                    shaas = shaas.concat(details_com.get(b).split("/")[0]+",");
                    comSet.add(details_com.get(b).split("/")[0]);
                }
            }
            List<String> dates = new ArrayList<>();
            for (int b = 0; b < details_date.size(); b++) {
                String impt = details_date.get(b).split("/")[1];
                if (impt.equals(imports.get(a))) {
                    dates.add(details_date.get(b).split("/")[0]);
                    dateL.add(details_date.get(b).split("/")[0]);
                }
            }
            double weeks = 0;
            String minDate = "-", maxDate = "-";
            if (dates.size() > 0) {
                minDate = DateOperations.sorts(dates, dates).split("/")[0];
                maxDate = DateOperations.sorts(dates, dates).split("/")[1];
                weeks = Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]) / 7;
            }
            if (shaas.equals("")) {
                shaas = "-";
            }
            if (developers.equals("")) {
                developers = "-";
            }
            
            imports_details.add(com+"/"+dev+"/"+weeks+"/"+minDate+"/"+maxDate+"/"+shaas+"/"+developers); 
        }
        double days = 0;
        double weeks = 0;
        String minDate = "-", maxDate = "-";
        if (dateL.size() > 0) {
            minDate = DateOperations.sorts(dateL, dateL).split("/")[0];
            maxDate = DateOperations.sorts(dateL, dateL).split("/")[1];
            weeks = Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]) / 7;
        }
        
        //List<String> developersL = new ArrayList<>();
        //List<String> shaaL = new ArrayList<>();
        
        //developersL.addAll(devSet);
        //shaaL.addAll(comSet);

        total_details.add(comSet.size()+"/"+devSet.size()+"/"+weeks+"/"+minDate+"/"+maxDate+"/"+shaaList.size()+"/"+devList.size());
        total_details.add(ct + "");
        
        allLists.add(imports_details);
        allLists.add(total_details);
        //allLists.add(developersL);
        //allLists.add(shaaL);
        
        return allLists;
    }
}
