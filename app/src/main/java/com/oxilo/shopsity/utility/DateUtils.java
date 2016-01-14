package com.oxilo.shopsity.utility;

import android.net.ParseException;

import com.oxilo.shopsity.POJO.CampList;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ericbasendra on 09/12/15.
 */
public final class DateUtils<T> implements Comparator<T> {
    List<T> campLists;

    @Override
    public int compare(T t, T t1) {

        try {
            //Pass String Date Format To Set UserDefined Date
            //Parse given STRING date to DATE format through df
            Date d1 = new Date(((CampList)t).getStartDate() * 1000);
            Calendar cT = Calendar.getInstance();
            cT.setTime(d1);

            //Pass String Date Format To Set UserDefined Date
            //Parse given STRING date to DATE format through df
            Date d2 = new Date(((CampList)t1).getStartDate() * 1000);
            Calendar cT1 = Calendar.getInstance();
            cT1.setTime(d2);

            if(cT.getTimeInMillis() > cT1.getTimeInMillis())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }catch (ParseException e) {

            e.printStackTrace();
        }

        return 0;
    }

    public List<T> getCampLists() {
        return campLists;
    }

    public void setCampLists(List<T> campLists) {
        this.campLists = campLists;
    }
}
