package cn.ucai.jkbd.biz;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.dao.ExamDao;
import cn.ucai.jkbd.dao.IExamDao;

/**
 * Created by LEO on 2017/6/30.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;

    int index=0;
    List<Question> questionList;

    public ExamBiz() {
        this.dao = new ExamDao();
    }
    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void ExamStart() {
        index=0;
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public void Pre() {
        if(index>0){
            index--;
        }
    }

    @Override
    public void Next() {
        this.getQuestion();
        if(index>=0&&index<questionList.size()-1){
            index++;
        }
    }

    @Override
    public int commit() {
        int score=0;
        for(Question question:questionList){
            String userAnswer=question.getUserAnswer();
            if(userAnswer!=null&&!userAnswer.equals("")){
                if(question.getAnswer().equals(userAnswer)){
                    score++;
                }
            }
        }

        return score;
    }


    @Override
    public Question getQuestion() {
        questionList=ExamApplication.instance.getQuestionList();
        if(questionList.get(index)!=null){
            return questionList.get(index);
        }
        return null;
    }
}
