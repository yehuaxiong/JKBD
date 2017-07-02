package cn.ucai.jkbd.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.jkbd.bean.Exam;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.Result;


public class ResultUtils {
    static String UTF_8 = "utf-8";
    public static Result getListResultFromJson(String jsonStr){
        Result result = new Result();
        Log.e("Utils","jsonStr="+jsonStr);
        try {
            if(jsonStr==null || jsonStr.isEmpty() || jsonStr.length()<3)return null;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if(!jsonObject.isNull("error_code")) {
                result.setError_code(jsonObject.getInt("error_code"));
            }else if(!jsonObject.isNull("msg")){
                result.setError_code(jsonObject.getInt("msg"));
            }
            if(!jsonObject.isNull("reason")) {
                result.setReason(jsonObject.getString("reason"));
            }else if(!jsonObject.isNull("result")){
                result.setReason(jsonObject.getString("result"));
            }
            if(!jsonObject.isNull("result")) {
                JSONArray array = jsonObject.getJSONArray("result");
                if (array != null) {
                    List<Question> list = new ArrayList<Question>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Question ga = new Gson().fromJson(jsonGroupAvatar.toString(), Question.class);
                        list.add(ga);
                    }
                    result.setResult(list);
                    return result;
                }
            }else{
                JSONArray array = new JSONArray(jsonStr);
                if (array != null) {
                    List<Question> list = new ArrayList<Question>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Question ga = new Gson().fromJson(jsonGroupAvatar.toString(), Question.class);
                        list.add(ga);
                    }
                    result.setResult(list);
                    return result;
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
