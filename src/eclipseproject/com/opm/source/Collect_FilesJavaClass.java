/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.utils.Constants;
import eclipseproject.com.read.ReadJavaClass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Collect_FilesJavaClass {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;
        String file = "Eclipse-projects.xlsx";
        String file2 = "Eclipse_TotalFiles.xlsx";
        String file3 = "Eclipse_statistics_Draft.xlsx";
        String path = "";
        String path_google = "";
        String path_new = "";
        String[] FILES = {file2};

        List<String> projects = Pick_GeneralNext.pick(path_google + file3, 0, 0, 1);

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Total-Files", "Total-Java", "Java-Class-Internal", "Total Interfaces", "Total-Internal-Interface", "Total Interfaces-Unique", "Total-Internal-Interface-Unique"};
        allobj.add(datas);
        List<String> importsList = Pick_GeneralNext.pick(path_google + file, 0, 0, 0);
        for (int x = 0; x < FILES.length; x++) {
            String file_name = "EclipseFile_JavaClass_Interfaces_Remaining_0001.xlsx";

            int count = 0;
            int numbers = 2;
            while (count < numbers) {
                List<String> projList = Pick_GeneralNext.pick(path_google + FILES[x], count, 0, 1);

                int index = 0;
                for (int a = 0; a < projList.size(); a++) {
                    if (!projects.contains(projList.get(a))) {
                        String fileDetails = ReadJavaClass.count(projList.get(a), 0, importsList, "", tokens, ct);
                        if (fileDetails.contains("XXXXXXXX")) {
                            double total_counts = Double.parseDouble(fileDetails.split("XXXXXXXX")[0]);
                            double total = Double.parseDouble(fileDetails.split("XXXXXXXX")[1]);
                            double totalJava = Double.parseDouble(fileDetails.split("XXXXXXXX")[2]);
                            double totalInterface = Double.parseDouble(fileDetails.split("XXXXXXXX")[3]);
                            double totalInterfacesInternal = Double.parseDouble(fileDetails.split("XXXXXXXX")[4]);
                            double totalInterface_unique = Double.parseDouble(fileDetails.split("XXXXXXXX")[5]);
                            double totalInterfacesInternal_unique = Double.parseDouble(fileDetails.split("XXXXXXXX")[6]);

                            ct = Integer.parseInt(fileDetails.split("XXXXXXXX")[7]);
                            System.out.println(a + "     :    " + projList.get(a) + "    :   " + total_counts);
                            //if (total_counts > 0) {
                            datas = new Object[]{projList.get(a), total, totalJava, total_counts, totalInterface, totalInterfacesInternal};
                            allobj.add(datas);
                            Create_Excel.createExcel2(allobj, 0, path_new + file_name, "files_five");
                            //}
                        }
                    }
                }
                ///Ends here..
                count++;
            }
            //System.out.println("\nFile: " + FILES[x] + " Completed....!\n\n");
        }
    }
}
