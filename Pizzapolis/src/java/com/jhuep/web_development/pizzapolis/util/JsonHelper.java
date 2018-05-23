package com.jhuep.web_development.pizzapolis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author David Newcomer<david.newcomer@jhuapl.edu>
 * @created May 10, 2017
 */
public class JsonHelper {

    private final String key;
    private final Object value;

    public JsonHelper(String json) {
        this.key = "root";
        this.value = new JSONObject(json);
    }

    public JsonHelper(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public List<JsonHelper> find(Function<String, Boolean> keyFilter) {
        return handleObject(key, value).stream()
                .filter(helper -> keyFilter.apply(helper.key))
                .collect(Collectors.toList());
    }

    public JsonHelper findSingle(Function<String, Boolean> keyFilter) {
        List<JsonHelper> result = find(keyFilter);
        if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new IllegalStateException("Error: Was expecting exactly 1 result. Instead found " + result.size());
        }
    }

    public List<JsonHelper> handleObject(String key, Object obj) {
        List<JsonHelper> result = new ArrayList<>();
        if (obj instanceof JSONArray) {
            JSONArray ra = (JSONArray) obj;
            result.addAll(handleArray(key, ra));
        } else if (obj instanceof JSONObject) {
            JSONObject newObj = (JSONObject) obj;
            result.addAll(handleObj(key, newObj));
        } else if (obj instanceof String || obj instanceof Double || obj instanceof Integer) {
            result.add(new JsonHelper(key, obj));
        } else {
            System.out.println("===== unrecognized ====");
            System.out.println(obj.getClass());
            throw new IllegalStateException(key + " = " + obj);
        }
        return result;
    }

    public List<JsonHelper> handleArray(String key, JSONArray ra) {
        List<JsonHelper> result = new ArrayList<>();
        result.add(new JsonHelper(key, ra));
        for (int i = 0; i < ra.length(); i++) {
            result.addAll(handleObject(key + "[" + i + "]", ra.get(i)));
        }
        return result;
    }

    public List<JsonHelper> handleObj(String oldKey, JSONObject obj) {
        List<JsonHelper> result = new ArrayList<>();
        result.add(new JsonHelper(oldKey, obj));
        for (String key : obj.keySet()) {
            result.addAll(handleObject(key, obj.get(key)));
        }
        return result;
    }

    @Override
    public String toString() {
        return key + " = " + Objects.toString(value);
    }

    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    //    public static void handleObject(String key, Object obj) {
//        if (obj instanceof JSONArray) {
//            JSONArray ra = (JSONArray) obj;
//            printJson(key, ra);
//        } else if (obj instanceof JSONObject) {
//            JSONObject newObj = (JSONObject) obj;
//            printJson(key, newObj);
//        } else if (obj instanceof String || obj instanceof Double || obj instanceof Integer) {
//            System.out.println(key + " = " + obj);
//        } else {
//            System.out.println("===== unrecognized ====");
//            System.out.println(obj.getClass());
//            throw new IllegalStateException(key + " = " + obj);
//        }
//    }
//
//    public static void printJson(String key, JSONArray ra) {
//        System.out.println("(ra) " + key + " = " + ra);
//        for (int i = 0; i < ra.length(); i++) {
//            handleObject(key + "[" + i + "]", ra.get(i));
//        }
//    }
//
//    public static void printJson(String oldKey, JSONObject obj) {
//        System.out.println("(obj) " + oldKey + " = " + obj);
//        for (String key : obj.keySet()) {
//            handleObject(key, obj.get(key));
//        }
//    }
}
