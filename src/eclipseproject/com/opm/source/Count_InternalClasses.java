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
import java.util.List;

/**
 *
 * @author john
 */
public class Count_InternalClasses {

    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String file1 = "Eclipse_Internal-Statistics-Lists_0.xlsx";
        String file2 = "Eclipse_Internal-Statistics-Lists_1.xlsx";
        String file3 = "Eclipse_Internal-Statistics-Lists_2.xlsx";
        String file4 = "Eclipse_Internal-Statistics-Lists_3.xlsx";
        String file5 = "Eclipse_Internal-Statistics-Lists_4.xlsx";
        String file6 = "Eclipse_Internal-Statistics-Lists_5.xlsx";

        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";

        String[] FILES = {file1, file2, file3, file4,file5,file6};
        List<String> importsL = new ArrayList<>();
        List<String> projectList = new ArrayList<>();
        List<List<String>> importsLists = new ArrayList<>();
        for (int i = 0; i < FILES.length; i++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path + FILES[i]);
            while (count < numbers) {
                String project = File_Details.setProjectName(path + FILES[i], count, "A2");
                List<String> imports = Pick_GeneralNext.pick(path + FILES[i], count, 1, 2);
                System.out.println(i + " : " + count + " \t " + project);
                imports.remove(imports.size() - 1);
                List<String> importL = new ArrayList<>();
                for (int a = 0; a < imports.size(); a++) {
                    if (!imports.get(a).contains("//")) {
                        importsL.add(imports.get(a));
                        importL.add(imports.get(a));
                    }
                    
                }
                projectList.add(project);
                importsLists.add(importL);
                count++;
            }
        }

         String f_name = "Eclipse_Internal-Class-Counts.xlsx";
         for (int a = 0; a < projectList.size(); a++) {
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"Project", "Interfaces", "Counts"};
            allobj.add(datas);
            datas = new Object[]{projectList.get(a), "", ""};
            allobj.add(datas);
            for (int b = 0; b < importsLists.get(a).size(); b++) {
                double counts = 0;
                for (int c = 0; c < importsL.size(); c++) {
                    if (importsLists.get(a).get(b).equals(importsL.get(c))) {
                        counts++;
                    }
                }
                datas = new Object[]{"", importsLists.get(a).get(b), counts};
                allobj.add(datas);
            }
            
            String sheet = projectList.get(a).split("/")[0];
            if (sheet.length() > 25) {
                sheet = sheet.substring(0, 25);
            }
            Create_Excel.createExcel2(allobj, 0, path_new + f_name, sheet + "_" + a);
        }
    }
}
