package com.zdd.autolibrary.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zdd on 2019/11/20
 */
public class RootUtil {
    private RootUtil(){}

    public static boolean isRoot() {
        String str = System.getenv("PATH");
        ArrayList arrayList = new ArrayList();
        for (String file : str.split(":")) {
            File file2 = new File(file, "su");
            if (file2.exists() && file2.canExecute()) {
                return true;
            }
        }
        return false;
    }

}
