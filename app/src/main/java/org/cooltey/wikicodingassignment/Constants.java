package org.cooltey.wikicodingassignment;

/**
 * Created by Cooltey Feng
 * 2017/07/13
 */

public final class Constants {

    public static final String WIKIPEDIA_URL = "https://wikipedia.org/";
    public static final String PLAIN_TEXT_MIME_TYPE = "text/plain";

    public static final String ACCEPT_HEADER_PREFIX = "accept: application/json; charset=utf-8; "
            + "profile=\"https://www.mediawiki.org/wiki/Specs/";
    public static final String ACCEPT_HEADER_SUMMARY = ACCEPT_HEADER_PREFIX + "Summary/1.1.2\"";

    public static final int PREFERRED_THUMB_SIZE = 320;
    public static final int PREFERRED_QUERY_NUMBERS = 50;

    public static final int MAX_TABS = 100;

    private Constants() { }
}