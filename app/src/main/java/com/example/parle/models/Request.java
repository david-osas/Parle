package com.example.parle.models;

import androidx.annotation.Nullable;

public class Request
{
    private String studentId;
    private String counsellorId;
    private String requestId;

    public Request() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



    public String getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(String counsellorId) {
        this.counsellorId = counsellorId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj.getClass().equals(this.getClass()))
        {
            if(obj!=null)
            {
                Request request = (Request) obj;
                if(this.getCounsellorId().equals(request.getCounsellorId())
                        && this.getStudentId()==request.getStudentId())
                    return true;

            }
            else if(this==null)
                return true;
        }
        return false;
    }
}
