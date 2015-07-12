package it2015.sabanciuniv.edu.erdincozdemir.utils;

import java.util.Date;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class Utilities {

    public static Date getDate(long timeStamp) {
        Date date = (new Date(timeStamp));
        return date;
    }
}
