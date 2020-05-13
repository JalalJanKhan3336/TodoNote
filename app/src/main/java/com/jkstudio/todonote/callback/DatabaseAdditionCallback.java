package com.jkstudio.todonote.callback;

public interface DatabaseAdditionCallback {
    void onAdditionSuccess(String msg);
    void onAdditionFailure(String error);
}
