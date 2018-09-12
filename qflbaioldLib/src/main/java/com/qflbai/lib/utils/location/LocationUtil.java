package com.qflbai.lib.utils.location;

import android.os.Environment;

import com.qflbai.lib.location.Location;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ProjectName: SunTechProjects
 * ClassDesc: ******
 * CreateUser: 201704012
 * CreateDate: 2018/4/12 16:21
 * UpdateUser: 201704012
 * UpdateDate:  2018/4/12 16:21
 * UpdateDesc: ******
 */
public class LocationUtil {
    private static String mPath = Environment.getDataDirectory().getPath() + "location.ser";

    /**
     * 设置位置信息
     */
    public static void setLocation(Location location) {
        try {
            FileOutputStream fileOut = new FileOutputStream(mPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(location);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    /**
     * 获取位置信息
     *
     * @return
     */
    public static Location getLocation() {
        Location location = null;
        try {
            FileInputStream fileIn = new FileInputStream(mPath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            location = (Location) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return location;
    }

    /**
     * 是否定位成功
     * @return
     */
    public boolean isLocationSucceed() {
        Location location = getLocation();
        return location == null ? false : true;
    }

}
