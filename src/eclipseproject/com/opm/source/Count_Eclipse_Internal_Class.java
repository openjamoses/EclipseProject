/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eclipseproject.com.opm.source;

import eclipseproject.com.core.Create_Excel;
import eclipseproject.com.picks.Pick_GeneralNext;
import eclipseproject.com.read.ReadJavaEclipse;
import eclipseproject.com.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Count_Eclipse_Internal_Class {

    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String file = "Eclipse-projects.xlsx";
        String file2 = "Repos-With-at-least-one-internal-interface.xlsx";
        String path = "";
        String path_google = "";
        String path_new = "";

        List<String> importsList = Pick_GeneralNext.pick(path_google + file, 0, 0, 0);
        List<String> projList = Pick_GeneralNext.pick(path_google + file2, 0, 0, 1);

        int index = 0;
        for (int a = 157; a < 200; a++) {

            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"Project", "Interfaces"};
            allobj.add(datas);
            List<String> import_name_list = ReadJavaEclipse.count(projList.get(a), 0, importsList, "", Constants.getToken(), ct);
            
            ct = Integer.parseInt(import_name_list.get(import_name_list.size() - 1));

            datas = new Object[]{projList.get(a), ""};
            allobj.add(datas);
            for (int i = 0; i < import_name_list.size(); i++) {
                datas = new Object[]{"", import_name_list.get(i)};
                allobj.add(datas);
            }
            String f_name = "Eclipse_Internal-Statistics-Lists_111.xlsx";
            String sheet = projList.get(a).split("/")[0];
            if (sheet.length() > 25) {
                sheet = sheet.substring(0,25);
            }
            Create_Excel.createExcel2(allobj, 0, path_new + f_name, sheet + "_" + a);
        }

        ///Ends here..
    }
}
