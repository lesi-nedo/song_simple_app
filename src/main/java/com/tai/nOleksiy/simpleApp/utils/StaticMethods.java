package com.tai.nOleksiy.simpleApp.utils;

import com.tai.nOleksiy.simpleApp.repo.SongsSortingRepository;

import java.util.Set;

public class StaticMethods {

    //returns a string in format: " VALUETOORDER DSC|ASC, etc.."
    public static String buildStringQuery(Set<String> valuesOrder){

        if(valuesOrder == null)
            return SongsSortingRepository.ORDER_BY_DEFAULT;
        StringBuilder res = new StringBuilder();
        String prefix = " ";
        for(var entry : valuesOrder){
            res.append(prefix)
                    .append(entry);
            prefix=", ";
        }
        return res.toString();
    }
}
