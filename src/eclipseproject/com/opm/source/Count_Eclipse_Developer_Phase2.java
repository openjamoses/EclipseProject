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
import eclipseproject.com.read.CountCommits_Files;
import eclipseproject.com.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Count_Eclipse_Developer_Phase2 {

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
        String file6 = "EclipseInternal-Commits-Developer-Counts-00001112.xlsx";
        String file7 = "EclipseInternal-Commits-Developer-Counts-00001113.xlsx";
        String file8 = "EclipseInternal-Commits-Developer-Counts-00001114.xlsx";
        String file9 = "EclipseInternal-Commits-Developer-Counts-00001115.xlsx";
        String file10 = "EclipseInternal-Commits-Developer-Counts-00001116.xlsx";

        //String file_prob = "Problematic.xlsx";
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
        
        String file_out13 = "EclipseInternal-Developer-Phase2_00076.xlsx";

        //String output = "Problematic.xlsx";
        String path = "";
        String path_new = "";

        List<String> list = new ArrayList<>();
        //list.add("FURCAS-dev/FURCAS");
        //list.add("eclipse/xtext-eclipse");
        //list.add("eclipse-cdt/cdt");
        //list.add(file);
        String[] FILES1 = {file_out1, file_out2, file_out3, file_out4, file_out5,file_out6,file_out7,file_out8,file_out9,file_out10, file_out11,file_out12,file_out13};
        for (int i = 0; i < FILES1.length; i++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path + FILES1[i]);
            //int numbers = projectList;
            while (count < numbers) {
                try {
                    String project = File_Details.setProjectName(path + FILES1[i], count, "A2");
                    System.out.println(count+"  "+project);
                    list.add(project);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count++;
            }
        }

        String[] FILES = { file7};
        /**
         * int ccc = 0;
         *
         * for (int i = 0; i < FILES.length; i++) { int numb =
         * File_Details.getWorksheets(path + FILES[i]); while (ccc < numb) {
         * String project = File_Details.setProjectName(path + FILES[i], ccc,
         * "A2"); //List<String> imports = Pick_GeneralNext.pick(path +
         * FILES[i], count, 1, 2); System.out.println(" : " + ccc + " \t " +
         * project); list.add(project); ccc++; } }*
         */
        //List<String> Prob_Sheet = Pick_GeneralNext.pick(path + file_prob, 0, 1, 0);
        /**
         * String[] FILES = {file1, file2, file3, file4, file5, file6, file7,
         * file8}; List<String> projectList = new ArrayList<>();
         * List<List<String>> importsLists = new ArrayList<>(); for (int i = 0;
         * i < FILES.length; i++) { int count = 0;
         *
         * int numbers = File_Details.getWorksheets(path + FILES[i]); while
         * (count < numbers) { String project = File_Details.setProjectName(path
         * + FILES[i], count, "A2"); //List<String> imports =
         * Pick_GeneralNext.pick(path + FILES[i], count, 1, 2);
         * System.out.println(i + " : " + count + " \t " + project);
         * projectList.add(project); count++; }
         *
         * }
         * *
         */
        //projectList.add("amelentev/eclipse.jdt-oo");
        //String[] FILES = {file1};
        for (int i = 0; i < FILES.length; i++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path + FILES[i]);
            //int numbers = projectList;
            while (count < numbers) {

                String project = File_Details.setProjectName(path + FILES[i], count, "A2");

                if (!list.contains(project)) {
                    //String sheet = File_Details.getWorksheetName(path + FILES[i], count);
                    //if (Prob_Sheet.contains(sheet) && !list.contains(project)) {
                    //if (!projectList.contains(project)) {
                    ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                    datas = new Object[]{"Project", "Imports", "Tot-Usage", "Count-COM-Internal", "Count-Dev-Internal", "Weeks", "First-Date", "Last-Date", "Shaa", "Developers"};
                    allobj.add(datas);
                    List<String> imports = Pick_GeneralNext.pick(path + FILES[i], count, 1, 2);
                    List<Double> numList = Pick_GeneralNumeric.pick_3(path + FILES[i], count, 2, 2);
                    List<String> first = Pick_GeneralNext.pick(path + FILES[i], count, 6, 2);
                    List<String> last = Pick_GeneralNext.pick(path + FILES[i], count, 7, 2);
                    List<String> firstL = new ArrayList<>();
                    List<String> lastL = new ArrayList<>();
                    for (int j = 0; j < first.size(); j++) {
                        if (!first.get(j).equals("-")) {
                            firstL.add(first.get(j));
                        }
                    }

                    for (int j = 0; j < last.size(); j++) {
                        if (!last.get(j).equals("-")) {
                            lastL.add(last.get(j));
                        }
                    }
                    String minDate = "", maxDate = "";
                    if (firstL.size() > 0 && lastL.size() > 0) {
                        minDate = DateOperations.sorts(firstL, firstL).split("/")[0];
                        maxDate = DateOperations.sorts(lastL, lastL).split("/")[1];
                    }

                    List<Integer> countL = new ArrayList<>();
                    for (int a = 0; a < imports.size(); a++) {
                        countL.add(0);
                    }

                    System.out.println(count + " : " + project);

                    if (!minDate.equals("") && !maxDate.equals("")) {
                        List<List<String>> allLists = CountCommits_Files.count2(project, imports, countL, minDate, maxDate, Constants.getToken(), ct);

                        List<String> imports_details = allLists.get(0);
                        List<String> total_details = allLists.get(1);
                        //List<String> developersL = allLists.get(2);
                        //List<String> shaaaL = allLists.get(3);

                        ct = Integer.parseInt(total_details.get(total_details.size() - 1));
                        total_details.remove(total_details.size() - 1);

                        datas = new Object[]{project, "", "", "", "", "", "", "", "", ""};
                        allobj.add(datas);
                        //datas = new Object[]{project, "", "", Double.parseDouble(total_details.get(0).split("/")[0]), Double.parseDouble(total_details.get(0).split("/")[1]), Double.parseDouble(total_details.get(0).split("/")[2]), total_details.get(0).split("/")[3], total_details.get(0).split("/")[4], Double.parseDouble(total_details.get(0).split("/")[5]), Double.parseDouble(total_details.get(0).split("/")[6])};
                        //allobj.add(datas);
                        //System.out.println(imports.size()+" : "+details_com.size()+" : "+details_dev.size()+" : "+details_weeks.size()+" : "+min_maxL.size()+" : ");
                        for (int a = 0; a < imports.size(); a++) {

                            datas = new Object[]{"", imports.get(a), numList.get(a), Double.parseDouble(imports_details.get(a).split("/")[0]), Double.parseDouble(imports_details.get(a).split("/")[1]), Double.parseDouble(imports_details.get(a).split("/")[2]), imports_details.get(a).split("/")[3], imports_details.get(a).split("/")[4], imports_details.get(a).split("/")[5], imports_details.get(a).split("/")[6]};
                            allobj.add(datas);
                        }

                        String sheet = project.split("/")[0];
                        if (sheet.length() > 25) {
                            sheet = sheet.substring(0, 25);
                        }
                        sheet = sheet + "_" + count;
                        String f_name = "EclipseInternal-Developer-Phase2_00061.xlsx";
                        Create_Excel.createExcel2(allobj, 0, path_new + f_name, sheet);
                    }
                }

                // }
                //}
                count++;
            }
        }
    }
}
