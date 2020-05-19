package com.jkstudio.todonote.callback;

public interface RemoveCallback {
    void onRemovedSuccess(String msg);
    void onRemovedFailure(String error);
}
