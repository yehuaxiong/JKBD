package cn.ucai.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Question;

/**
 * Created by LEO on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Question> questionList;

    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
        questionList= ExamApplication.getInstance().getQuestionList();
    }

    @Override
    public int getCount() {
        return (questionList!=null)?questionList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mContext, R.layout.item_question,null);
        ImageView imv_question= (ImageView) view.findViewById(R.id.imv_question);
        TextView tv_no= (TextView) view.findViewById(R.id.tv_no);
        String ua=questionList.get(position).getUserAnswer();
        String ra=questionList.get(position).getAnswer();
        if(ua!=null&&!ua.equals("")){
            imv_question.setImageResource(ua.equals(ra)?R.mipmap.answer24x24:R.mipmap.error_24px);
            //imv_question.setImageResource(R.mipmap.answer24x24);
        }else{
            imv_question.setImageResource(R.mipmap.noselected);
        }
        tv_no.setText("第"+(position+1)+"题");
        return view;
    }
}
