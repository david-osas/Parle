package com.example.parle;

import com.example.parle.models.ChatsModel;
import com.example.parle.models.Counsellor;
import com.example.parle.models.Student;

public class SessionHelper {
    private static SessionHelper mSessionHelper;
    public Counsellor currentCounsellor;
    public Student currentStudent;
    public ChatsModel currentChat;

    private SessionHelper(){}
    public static SessionHelper getInstance()
    {
        if(mSessionHelper==null)
        {
            mSessionHelper  = new SessionHelper();
        }
        return mSessionHelper;
    }

}
