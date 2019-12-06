/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.picks.Pick_GeneralNumeric;
import eclipseproject.com.utils.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Eclipse_General_Statistics {

    public static void main(String[] args) throws Exception {
        collect();
    }

    private static void collect() throws Exception {
        ///String toDay = "2017-07-06T00:00:00Z";
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        String file1 = "Eclipse_statistics_Draft.xlsx";

        String path = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00eclipse/";
        int ct = 0;

        List<String> project1 = Pick_GeneralNext.pick(path + file1, 0, 0, 1);
        List<Double> Total_Files = Pick_GeneralNumeric.pick_3(path + file1, 0, 1, 1);
         List<Double> Total_Java = Pick_GeneralNumeric.pick_3(path + file1, 0, 2, 1);
         List<Double> Total_Java_Internal = Pick_GeneralNumeric.pick_3(path + file1, 0, 3, 1);
        List<Double> Total_Interface = Pick_GeneralNumeric.pick_3(path + file1, 0, 4, 1);
        List<Double> Total_Internal_interface = Pick_GeneralNumeric.pick_3(path + file1, 0, 5, 1);

        List<String> project2 = Pick_GeneralNext.pick(path + file1, 1, 0, 1);
        List<Double> Forks = Pick_GeneralNumeric.pick_3(path + file1, 1, 1, 1);
        List<Double> Stars = Pick_GeneralNumeric.pick_3(path + file1, 1, 2, 1);

        List<String> project3 = Pick_GeneralNext.pick(path + file1, 2, 0, 1);
        List<Double> PR_Closed = Pick_GeneralNumeric.pick_3(path + file1, 2, 1, 1);
        List<Double> PR_Merged = Pick_GeneralNumeric.pick_3(path + file1, 2, 5, 1);
        List<Double> IS_Closed = Pick_GeneralNumeric.pick_3(path + file1, 2, 9, 1);
        List<Double> Dev = Pick_GeneralNumeric.pick_3(path + file1, 2, 13, 1);
        List<String> Dev_Details = Pick_GeneralNext.pick(path + file1, 2, 14, 1);

        List<Double> pr_closed_mean = Pick_GeneralNumeric.pick_3(path + file1, 2, 2, 1);
        List<Double> pr_closed_median = Pick_GeneralNumeric.pick_3(path + file1, 2, 3, 1);
        List<Double> pr_merged_mean = Pick_GeneralNumeric.pick_3(path + file1, 2, 6, 1);
        List<Double> pr_merged_median = Pick_GeneralNumeric.pick_3(path + file1, 2, 7, 1);
        List<Double> is_closed_mean = Pick_GeneralNumeric.pick_3(path + file1, 2, 10, 1);
        List<Double> is_closed_median = Pick_GeneralNumeric.pick_3(path + file1, 2, 11, 1);

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Total-Files","Total_Java", "Total-Java-Internal", "Total-Interface", "Total-Internal-Interface", "Forks", "Stars", "PR-Closed", "PR-Merged", "PR-Closed-Mean", "PR-Closed-Median", "PR-Merged-Mean", "PR-Merged-Median", "IS-Closed", "IS-Closed-Mean", "IS-Closed-Median","Total-Dev"};// end of assigning the header to the object..
        allobj.add(datas);
        for (int i = 0; i < project2.size(); i++) {
            if (project1.contains(project2.get(i)) && project3.contains(project2.get(i))) {
                int index1 = project1.indexOf(project2.get(i));
                int index2 = project3.indexOf(project2.get(i));
                

                datas = new Object[]{project2.get(i), Total_Files.get(index1),Total_Java.get(index1), Total_Java_Internal.get(index1), Total_Interface.get(index1), Total_Internal_interface.get(index1), Forks.get(i), Stars.get(i), PR_Closed.get(index2), PR_Merged.get(index2), pr_closed_mean.get(index2), pr_closed_median.get(index2), pr_merged_mean.get(index2), pr_merged_median.get(index2), IS_Closed.get(index2), is_closed_mean.get(index2), is_closed_median.get(index2),Dev.get(index2)};// end of assigning the header to the object..
                allobj.add(datas);
                String f_name = "Eclipse-statistics_00000.xlsx";
                Create_Excel.createExcel2(allobj, 0, path_new + f_name, "statistics");
            }
        }

    }
}
