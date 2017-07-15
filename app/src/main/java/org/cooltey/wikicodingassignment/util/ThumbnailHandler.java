package org.cooltey.wikicodingassignment.util;

import org.cooltey.wikicodingassignment.Constants;

/**
 * ThumbnailHandler
 */

public class ThumbnailHandler{

    private String mImgUrl;

    public ThumbnailHandler(String url){
        if(url != null){
            mImgUrl = url;
        }
    }

    public String getLargerImage() {

        if(mImgUrl != null) {
            // replace item
            mImgUrl = mImgUrl.replace(Constants.PREFERRED_THUMB_SIZE + "px", Constants.PREFERRED_LARGE_THUMB_SIZE + "px");
        }

        return mImgUrl;
    }
}
