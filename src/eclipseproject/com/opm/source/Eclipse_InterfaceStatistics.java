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
public class Eclipse_InterfaceStatistics {
    
    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String file1 = "EclipseInternal-Commits-Developer-Counts-00000.xlsx";
        String file2 = "EclipseInternal-Commits-Developer-Counts-00001.xlsx";
        String file3 = "EclipseInternal-Commits-Developer-Counts-00002.xlsx";
        String file4 = "EclipseInternal-Commits-Developer-Counts-00003.xlsx";
        String file5 = "EclipseInternal-Commits-Developer-Counts-00001111.xlsx";
        String file6 = "EclipseInternal-Commits-Developer-Counts-00001112.xlsx";
        String file7 = "EclipseInternal-Commits-Developer-Counts-00001113.xlsx";
        String file8 = "EclipseInternal-Commits-Developer-Counts-00001114.xlsx";
        //String file9 = "EclipseInternal-Commits-Developer-Counts-00001111.xlsx";
        //String file5 = "Eclipse_Internal-Statistics-Lists_4.xlsx";
        //String file6 = "Eclipse_Internal-Statistics-Lists_5.xlsx";

        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/updated/";

        String[] FILES = {file1, file2, file3, file4, file5, file6,file7, file8};
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
