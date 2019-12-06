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
public class Collect_InternalInterfaces {

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
        //String file6 = "EclipseInternal-Commits-Developer-Counts-00001112.xlsx";
        String file7 = "EclipseInternal-Commits-Developer-Counts-00001113.xlsx";
        String file8 = "EclipseInternal-Commits-Developer-Counts-00001114.xlsx";
        //String file9 = "EclipseInternal-Commits-Developer-Counts-00001115.xlsx";
        //String file10 = "EclipseInternal-Commits-Developer-Counts-00001116.xlsx";

        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/Eclipse-file/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/Eclipse-file/";

        String[] FILES = {file1, file2, file3, file4, file5, file7, file8};
        List<String> projectList = new ArrayList<>();
        List<List<String>> importsLists = new ArrayList<>();
        Set<String> importSet = new HashSet<>();
        for (int i = 0; i < FILES.length; i++) {
            int count = 0;

            int numbers = File_Details.getWorksheets(path + FILES[i]);
            while (count < numbers) {
                String project = File_Details.setProjectName(path + FILES[i], count, "A2");
                List<String> imports = Pick_GeneralNext.pick(path + FILES[i], count, 1, 2);
                System.out.println(i + " : " + count + " \t " + project);

                projectList.add(project);
                //importSet.addAll(imports);
                for (int j = 0; j < imports.size(); j++) {
                    importSet.add(imports.get(j));
                }
                count++;
            }

        }

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Imports"};
        allobj.add(datas);
        List<String> importL = new ArrayList<>();
        importL.addAll(importSet);
        for (int a = 0; a < importL.size(); a++) {
            datas = new Object[]{importL.get(a)};
            allobj.add(datas);
        }
        String f_name = "EclipseInternal-Interfaces2.xlsx";
        Create_Excel.createExcel2(allobj, 0, path_new + f_name, "interfaces");
    }
}
