/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.core.File_Details;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.picks.Pick_GeneralNumeric;
import eclipseproject.com.read.CountCommits_Files;
import eclipseproject.com.utils.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Count_Eclipse_Developer {

    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String file = "Eclipse_Internal-Class-Counts.xlsx";
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

        String file_prob = "Problematic.xlsx";

        String output = "Problematic.xlsx";
        String path = "";
        String path_new = "";

        List<String> list = new ArrayList<>();
        list.add("FURCAS-dev/FURCAS");
        list.add("eclipse/xtext-eclipse");
        list.add("eclipse-cdt/cdt");
        //list.add(file);
        String[] FILES = {file9, file10};
        int ccc = 0;
        for (int i = 0; i < FILES.length; i++) {
            int numb = File_Details.getWorksheets(path + FILES[i]);
            while (ccc < numb) {
                String project = File_Details.setProjectName(path + FILES[i], ccc, "A2");
                //List<String> imports = Pick_GeneralNext.pick(path + FILES[i], count, 1, 2);
                System.out.println(" : " + ccc + " \t " + project);
                list.add(project);
                ccc++;
            }
        }

        List<String> Prob_Sheet = Pick_GeneralNext.pick(path + file_prob, 0, 1, 0);

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
        //for (int i = 0; i < FILES.length; i++) {
        int count = 0;
        int numbers = File_Details.getWorksheets(path + file);
        //int numbers = projectList;
        while (count < numbers) {

            String project = File_Details.setProjectName(path + file, count, "A2");
            String sheet = File_Details.getWorksheetName(path + file, count);

            if (Prob_Sheet.contains(sheet) && !list.contains(project)) {
                //if (!projectList.contains(project)) {

                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"Project", "Imports", "Tot-Usage", "Count-COM-Internal", "Count-Dev-Internal", "Weeks", "First-Date", "Last-Date", "Total-COM", "Total-DEV"};
                allobj.add(datas);
                List<String> imports = Pick_GeneralNext.pick(path + file, count, 1, 2);
                List<Double> numList = Pick_GeneralNumeric.pick_3(path + file, count, 2, 2);

                List<Integer> countL = new ArrayList<>();
                for (int a = 0; a < imports.size(); a++) {
                    countL.add(0);
                }

                System.out.println(count + " : " + project);
                List<List<String>> allLists = CountCommits_Files.count(project, imports, countL, "", Constants.cons.NEW_TODAY_DATE, Constants.getToken(), ct);

                List<String> imports_details = allLists.get(0);
                List<String> total_details = allLists.get(1);

                ct = Integer.parseInt(total_details.get(total_details.size() - 1));
                total_details.remove(total_details.size() - 1);

                datas = new Object[]{project, "", "", Double.parseDouble(total_details.get(0).split("/")[0]), Double.parseDouble(total_details.get(0).split("/")[1]), Double.parseDouble(total_details.get(0).split("/")[2]), total_details.get(0).split("/")[3], total_details.get(0).split("/")[4], Double.parseDouble(total_details.get(0).split("/")[5]), Double.parseDouble(total_details.get(0).split("/")[6])};
                allobj.add(datas);

                //System.out.println(imports.size()+" : "+details_com.size()+" : "+details_dev.size()+" : "+details_weeks.size()+" : "+min_maxL.size()+" : ");
                for (int a = 0; a < imports.size(); a++) {
                    datas = new Object[]{"", imports.get(a), numList.get(a), Double.parseDouble(imports_details.get(a).split("/")[0]), Double.parseDouble(imports_details.get(a).split("/")[1]), Double.parseDouble(imports_details.get(a).split("/")[2]), imports_details.get(a).split("/")[3], imports_details.get(a).split("/")[4], "", ""};
                    allobj.add(datas);
                }
                String f_name = "EclipseInternal-Commits-Developer-Counts-00001116.xlsx";
                Create_Excel.createExcel2(allobj, 0, path_new + f_name, sheet);
                // }
            }
            count++;
        }
    }
}
