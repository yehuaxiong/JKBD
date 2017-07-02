package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.dao.IExamDao;

/**
 * Created by LEO on 2017/6/30.
 */

public interface IExamBiz {
    public void ExamStart();
    public void Pre();
    public void Next();
    public void commit();
}
