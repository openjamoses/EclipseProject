/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.utils.Constants;
import eclipseproject.com.read.ReadJavaFile;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author john
 */
public class Collection_JavaFiles {

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
        //String file1 = "alt236_68.txt";
        String file = "Eclipse-projects.xlsx";
        String file2 = "ReposEclispse_InternalFiles_2.xlsx";

        String file1 = "ReposEClipse_Files.xlsx";
        //String file1 = "test_file.xlsx";

        String path = "";
        String path_google = "";
        String path_new = "";

        List<String> importsList = Pick_GeneralNext.pick(path_google + file, 0, 0, 0);
        List<String> projectsList = Pick_GeneralNext.pick(path_google + file2, 0, 0, 1);
        String[] FILES = {file1};

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project"};
        allobj.add(datas);
        for (int x = 0; x < FILES.length; x++) {
            String file_name = "ReposEclipse_NOInternalFile_333.xlsx";

            int count = 2;
            int numbers = 3;
            while (count < numbers) {
                List<String> projectL = Pick_GeneralNext.pick(path_google + FILES[x], count, 0, 1);
                //TODO:::: 
                Set<String> projSet = new HashSet<>();
                projSet.addAll(projectL);
                List<String> projList = new ArrayList<>();
                projList.addAll(projSet);
                int index = 0;
                for (int a = 0; a < projList.size(); a++) {
                    if (!projectsList.contains(projList.get(a))) {
                        String fileDetails = ReadJavaFile.count(projList.get(a), 0, importsList, "", tokens, ct);
                        if (fileDetails.contains("XXXXXXXX")) {
                            double total_counts = Double.parseDouble(fileDetails.split("XXXXXXXX")[0]);
                            double total = Double.parseDouble(fileDetails.split("XXXXXXXX")[1]);
                            double totalJava = Double.parseDouble(fileDetails.split("XXXXXXXX")[2]);
                            String files = fileDetails.split("XXXXXXXX")[3];
                            String downloads = fileDetails.split("XXXXXXXX")[4];
                            ct = Integer.parseInt(fileDetails.split("XXXXXXXX")[5]);
                            System.out.println(a + "     :    " + projList.get(a) + "    :   " + total_counts);
                            if (total_counts > 0) {
                                datas = new Object[]{projList.get(a)};
                                allobj.add(datas);

                                Create_Excel.createExcel2(allobj, 0, path_new + file_name, "eclipse_three");
                            }
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
