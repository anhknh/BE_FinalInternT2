package com.example.internfinallyt2.utils;

import org.springframework.stereotype.Service;

@Service
public class ConvertFormat {
    public Long[] convertStringArrayToLongArray(String[] stringArray) {
        if (stringArray == null) {
            return null;
        }
        Long[] longArray = new Long[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            longArray[i] = Long.valueOf(stringArray[i]);
        }
        return longArray;
    }
}
