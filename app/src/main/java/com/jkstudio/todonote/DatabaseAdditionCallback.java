package com.jkstudio.todonote;

public interface DatabaseAdditionCallback {
    void onAdditionSuccess(String msg);
    void onAdditionFailure(String error);
}
