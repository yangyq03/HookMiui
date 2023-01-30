package com.apwy.hookMiui;

import java.util.HashMap;

public class WifiSpliterNew {
    public static HashMap<String, String> wifiSpliterNew(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        int indexOf = str.indexOf("T:");
        int indexOf2 = str.indexOf("P:");
        int indexOf3 = str.indexOf("S:");
        int length = str.length();
        if (str.contains("H:")) {
            length = str.indexOf("H:");
        }
        if (indexOf < indexOf2 && indexOf2 < indexOf3) {
            putWifiBodyHashMap(hashMap, str, indexOf, indexOf2, indexOf3, length, 1);
        } else if (indexOf < indexOf3 && indexOf3 < indexOf2) {
            putWifiBodyHashMap(hashMap, str, indexOf, indexOf3, indexOf2, length, 2);
        } else if (indexOf3 < indexOf && indexOf < indexOf2) {
            putWifiBodyHashMap(hashMap, str, indexOf3, indexOf, indexOf2, length, 3);
        } else if (indexOf3 < indexOf2 && indexOf2 < indexOf) {
            putWifiBodyHashMap(hashMap, str, indexOf3, indexOf2, indexOf, length, 4);
        } else if (indexOf2 >= indexOf3 || indexOf3 >= indexOf) {
            if (indexOf2 < indexOf && indexOf < indexOf3) {
                putWifiBodyHashMap(hashMap, str, indexOf2, indexOf, indexOf3, length, 6);
            }
        } else {
            putWifiBodyHashMap(hashMap, str, indexOf2, indexOf3, indexOf, length, 5);
        }
        return hashMap;
    }

    private static void putWifiBodyHashMap(HashMap<String, String> hashMap, String str, int i, int i2, int i3, int i4, int i5) {
        String src = getSrc(str, i, i2);
        if (src != null) {
            switch (i5) {
                case 1:
                case 2:
                    hashMap.put("T", src);
                    break;
                case 3:
                case 4:
                    hashMap.put("S", src);
                    break;
                case 5:
                case 6:
                    hashMap.put("P", src);
                    break;
            }
        }
        String src2 = getSrc(str, i2, i3);
        if (src2 != null) {
            switch (i5) {
                case 1:
                case 4:
                    hashMap.put("P", src2);
                    break;
                case 2:
                case 5:
                    hashMap.put("S", src2);
                    break;
                case 3:
                case 6:
                    hashMap.put("T", src2);
                    break;
            }
        }
        if (str.charAt(i3 + 1) == ':') {
            String substring = str.substring(i3 + 2, i4 - 1);
            switch (i5) {
                case 1:
                case 6:
                    hashMap.put("S", substring);
                    break;
                case 2:
                case 3:
                    hashMap.put("P", substring);
                    break;
                case 4:
                case 5:
                    hashMap.put("T", substring);
                    break;
                default:
                    break;
            }
        }

    }

    private static String getSrc(String str, int i, int i2) {
        if (str.charAt(i + 1) != ':') {
            return null;
        }
        int i3 = i2 - 1;
        if (str.charAt(i3) == ';') {
            return str.substring(i + 2, i3);
        }
        return null;
    }
}
