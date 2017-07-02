package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.dao.ExamDao;
import cn.ucai.jkbd.dao.IExamDao;

/**
 * Created by LEO on 2017/6/30.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void ExamStart() {
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public void Pre() {

    }

    @Override
    public void Next() {

    }

    @Override
    public void commit() {

    }
}
