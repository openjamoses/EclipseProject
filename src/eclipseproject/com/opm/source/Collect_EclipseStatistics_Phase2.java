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
public class Collect_EclipseStatistics_Phase2 {

    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        //String file = "Eclipse_Internal-Class-Counts.xlsx";
        String file_out1 = "EclipseInternal-Developer-Phase2_00000.xlsx";
        String file_out2 = "EclipseInternal-Developer-Phase2_00001.xlsx";
        String file_out3 = "EclipseInternal-Developer-Phase2_00010.xlsx";
        String file_out4 = "EclipseInternal-Developer-Phase2_00011.xlsx";
        String file_out5 = "EclipseInternal-Developer-Phase2_00020.xlsx";
        String file_out6 = "EclipseInternal-Developer-Phase2_00030.xlsx";
        String file_out7 = "EclipseInternal-Developer-Phase2_00031.xlsx";
        String file_out8 = "EclipseInternal-Developer-Phase2_00040.xlsx";
        String file_out9 = "EclipseInternal-Developer-Phase2_00050.xlsx";
        String file_out10 = "EclipseInternal-Developer-Phase2_00051.xlsx";
        String file_out11 = "EclipseInternal-Developer-Phase2_00060.xlsx";
        String file_out12 = "EclipseInternal-Developer-Phase2_00075.xlsx";
        String file_out14 = "EclipseInternal-Developer-Phase2_00061.xlsx";
        String file_out13 = "EclipseInternal-Developer-Phase2_00076.xlsx";

        String file_interface = "EclipseInterface-statistics0005.xlsx";

        //String output = "Problematic.xlsx";
        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/variability2/";

        List<String> projectL = new ArrayList<>();
        List<List<String>> importsL = new ArrayList<>();
        List<List<String>> firstL = new ArrayList<>();
        List<List<String>> lastL = new ArrayList<>();
        List<List<Double>> usageL = new ArrayList<>();
        List<List<Double>> comL = new ArrayList<>();
        List<List<Double>> devL = new ArrayList<>();
        List<List<String>> develL = new ArrayList<>();
        List<String> filenameList = new ArrayList<>();

        //list.add(file);
        String[] FILES = {file_out1, file_out2, file_out3, file_out4, file_out5, file_out6, file_out7,
            file_out8, file_out9, file_out10, file_out11, file_out12, file_out14, file_out13};

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
                List<String> deveopers = Pick_GeneralNext.pick(path + FILES[i], ccc, 9, 2);
                System.out.println(" : " + ccc + " \t " + project);
                if (!projectL.contains(project)) {
                    projectL.add(project);
                    firstL.add(first);
                    lastL.add(last);
                    usageL.add(usage);
                    comL.add(com);
                    devL.add(dev);
                    filenameList.add(FILES[i]+"/"+File_Details.getWorksheetName(path + FILES[i], ccc));

                    List<String> importS = new ArrayList<>();
                    for (int j = 0; j < imports.size(); j++) {
                        importS.add(imports.get(j).replaceAll("[\\n\\t ]", ""));
                    }
                    importsL.add(importS);
                    develL.add(deveopers);
                }

                ccc++;
            }
        }

        List<String> list = Pick_GeneralNext.pick(path + file_interface, 0, 0, 1);
        /**
         * Set<String> importSet = new HashSet<>(); for (int i = 0; i <
         * importsL.size(); i++) { for (int j = 0; j < importsL.get(i).size();
         * j++) { importSet.add(importsL.get(i).get(j)); } } List<String>
         * interface_list = new ArrayList<>(); interface_list.addAll(importSet);
         * *
         */
        List<String> interface_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            interface_list.add(list.get(i).replaceAll("[\\n\\t ]", ""));
        }

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Interface", "# Repos", "#Classes", "#Commits", "Total Developers", "Unique Developers", "#Weeks","Repos","Filename"};
        allobj.add(datas);
        for (int a = 0; a < interface_list.size(); a++) {
            double total_repos = 0;
            double total_usage = 0;
            double total_com = 0;
            double total_dev = 0;
            Set<String> devSet = new HashSet<>();
            List<String> devList = new ArrayList<>();
            //Set<String> devSet = new HashSet<>();
            List<String> first = new ArrayList<>();
            List<String> last = new ArrayList<>();
            String reposString = "";
            String fileNameString = "";
            for (int b = 0; b < projectL.size(); b++) {
                List<String> imports = importsL.get(b);

                //for (int c = 0; c < projectL.size(); c++) {
                //if (imports.contains(interface_list.get(a))) {
                //int flag = 0;
                for (int i = 0; i < imports.size(); i++) {
                    if (imports.get(i).contains(interface_list.get(a))) {
                        total_repos++;
                        total_usage += usageL.get(b).get(i);
                        total_com += comL.get(b).get(i);
                        String developers = develL.get(b).get(i);
                        String[] splits = developers.split(",");
                        reposString = reposString.concat(projectL.get(b)+" , ");
                        fileNameString = fileNameString.concat(filenameList.get(b)+" , ");
                        for (int j = 0; j < splits.length; j++) {
                            devSet.add(splits[j]);
                            devList.add(splits[j]);
                        }

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

            datas = new Object[]{interface_list.get(a), total_repos, total_usage, total_com, Double.parseDouble(devList.size()+""), Double.parseDouble(devSet.size() + ""), days / 7, reposString, fileNameString};
            allobj.add(datas);

        }
        String f_name = "EclipseInterface-statistics000.xlsx";
        Create_Excel.createExcel2(allobj, 0, path_new + f_name, "stats_unique");

    }
}
