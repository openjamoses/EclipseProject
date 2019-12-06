/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.core.File_Details;
import eclipseproject.com.picks.Pick_GeneralNext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Collect_AllImports {
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        readText();
    }
    /**
     * 
     * @throws Exception 
     */
    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String file = "Eclipse_Internal-Class-Counts.xlsx";
        
        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";

        //String[] FILES = {file1};
        //for (int i = 0; i < FILES.length; i++) {
        int count = 0;
        int numbers = File_Details.getWorksheets(path + file);
        //int numbers = 300;
        
        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Imports"};
        allobj.add(datas);
        Set<String> importSet = new HashSet<>();
        while (count < numbers) {
            String project = File_Details.setProjectName(path + file, count, "A2");
            List<String> imports = Pick_GeneralNext.pick(path + file, count, 1, 2);
            importSet.addAll(imports);
            System.out.println(count+"  :  "+project);
            String sheet = File_Details.getWorksheetName(path + file, count);
            count++;
        }
        List<String> importSList = new ArrayList<>();
        importSList.addAll(importSet);
        for (int a = 0; a < importSList.size(); a++) {
            datas = new Object[]{importSList.get(a)};
            allobj.add(datas);
        }
        String f_name = "EclipseBadInterface-Lists.xlsx";
        Create_Excel.createExcel2(allobj, 0, path_new + f_name, "sheet");
    }
}
