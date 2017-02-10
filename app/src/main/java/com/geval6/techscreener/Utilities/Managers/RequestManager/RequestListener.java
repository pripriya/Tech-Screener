package com.geval6.techscreener.Utilities.Managers.RequestManager;

public interface RequestListener {
    void onBeginRequest();
    void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content);
    void onRequestFailed(RequestIdentifier identifier, String message, Object... content);
}
