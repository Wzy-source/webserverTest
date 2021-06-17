package com.Request_Response;

public class ContentType {
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String JSON = "application/json";
    public static final String X_WWW_form_urlencoded = "application/x-www-form-urlencoded";

    public ContentType() {
    }

    public static boolean isSupproted(String str) {
        return "text/html".equals(str) || "application/json".equals(str) || "application/x-www-form-urlencoded".equals(str);
    }
}
