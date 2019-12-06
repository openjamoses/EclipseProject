/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.core.DateOperations;
import eclipseproject.com.core.File_Details;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.picks.Pick_GeneralNumeric;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Collect_EclipseStatistics {

    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        //String file = "Eclipse_Internal-Class-Counts.xlsx";
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

        String file_interface = "00EclipseInternal-Interfaces.xlsx";

        //String output = "Problematic.xlsx";
        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/Eclipse-file/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/Eclipse-file/";

        List<String> projectL = new ArrayList<>();
        List<List<String>> importsL = new ArrayList<>();
        List<List<String>> firstL = new ArrayList<>();
        List<List<String>> lastL = new ArrayList<>();
        List<List<Double>> usageL = new ArrayList<>();
        List<List<Double>> comL = new ArrayList<>();
        List<List<Double>> devL = new ArrayList<>();
        List<String> filenameList = new ArrayList<>();

        //list.add(file);
        String[] FILES = {file1, file2, file3, file4, file5, file7, file8};

        Set<String> importsSet = new HashSet<>();

        for (int i = 0; i < FILES.length; i++) {
            int ccc = 0;
            int numb = File_Details.getWorksheets(path + FILES[i]);
            while (ccc < numb) {
                String project = File_Details.setProjectName(path + FILES[i], ccc, "A2");
                List<String> imports = Pick_GeneralNext.pick(path + FILES[i], ccc, 1, 2);
                List<Double> usage = Pick_GeneralNumeric.pick_3(path + FILES[i], ccc, 2, 2);
                List<Double> com = Pick_GeneralNumeric.pick_3(path + FILES[i], ccc, 3, 2);
                List<Double> dev = Pick_GeneralNumeric.pick_3(path + FILES[i], ccc, 4, 2);
                List<String> first = Pick_GeneralNext.pick(path + FILES[i], ccc, 6, 2);
                List<String> last = Pick_GeneralNext.pick(path + FILES[i], ccc, 7, 2);
                System.out.println(" : " + ccc + " \t " + project);
                
                String sheetName = File_Details.getWorksheetName(path + FILES[i], ccc);
                //if (!projectL.contains(project)) {
                projectL.add(project);
                firstL.add(first);
                lastL.add(last);
                usageL.add(usage);
                comL.add(com);
                devL.add(dev);
                List<String> importS = new ArrayList<>();
                for (int j = 0; j < imports.size(); j++) {
                    importS.add(imports.get(j).replaceAll("[\\n\\t ]", ""));
                }
                importsL.add(importS);
                importsSet.addAll(importS);
                filenameList.add(FILES[i]+"/"+sheetName);
                // }

                ccc++;
            }
        }

        List<String> list = Pick_GeneralNext.pick(path + file_interface, 0, 0, 1);
        List<String> interface_list = new ArrayList<>();
        //interface_list.addAll(importsSet);
        for (int i = 0; i < list.size(); i++) {
           interface_list.add(list.get(i).replaceAll("[\\n\\t ]", ""));
        }
//interface_list.re
        /**
         * Set<String> importSet = new HashSet<>(); for (int i = 0; i <
         * importsL.size(); i++) { for (int j = 0; j < importsL.get(i).size();
         * j++) { importSet.add(importsL.get(i).get(j)); } } List<String>
         * interface_list = new ArrayList<>(); interface_list.addAll(importSet);
         * *
         */

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Interface", "# Repos", "#Classes", "#Commits", "Total Developers", "Unique Developers", "#Weeks", "Repos", "Filename"};
        allobj.add(datas);
        for (int a = 0; a < interface_list.size(); a++) {
            double total_repos = 0;
            double total_usage = 0;
            double total_com = 0;
            double total_dev = 0;
            List<String> first = new ArrayList<>();
            List<String> last = new ArrayList<>();
            String reposString = "";
            String filename = "";
            for (int b = 0; b < projectL.size(); b++) {
                List<String> imports = importsL.get(b);
                //for (int c = 0; c < projectL.size(); c++) {
                //if (imports.contains(interface_list.get(a))) {
                //int flag = 0;
                for (int i = 0; i < imports.size(); i++) {
                    String importS = imports.get(i);
                    if (importS.contains(interface_list.get(a))) {
                        total_repos++;
                        total_usage += usageL.get(b).get(i);
                        total_com += comL.get(b).get(i);
                        total_dev += devL.get(b).get(i);
                        reposString = reposString.concat(projectL.get(b) + " , ");
                        filename = filename.concat(filenameList.get(b) + " , ");
                        //if (flag == 0) {
                        if (!firstL.get(b).get(i).equals("-") && !lastL.get(b).get(i).equals("-")) {
                            first.add(firstL.get(b).get(i));
                            last.add(lastL.get(b).get(i));
                        }
                        //flag++;
                        //}
                    }
                }
                //}
                //}

            }

            String minDate = "";
            String maxDate = "";
            if (first.size() > 0) {
                minDate = DateOperations.sorts(first, first).split("/")[0];
            }
            if (last.size() > 0) {
                maxDate = DateOperations.sorts(last, last).split("/")[1];
            }
            double days = 0;
            if (!minDate.equals("") && !maxDate.equals("")) {
                days = Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]);
            }

            datas = new Object[]{interface_list.get(a), total_repos, total_usage, total_com, total_dev, "", days / 7, reposString, filename};
            allobj.add(datas);

        }
        String f_name = "EclipseInterface-statistics000.xlsx";
        Create_Excel.createExcel2(allobj, 0, path_new + f_name, "stats_");

    }
}
