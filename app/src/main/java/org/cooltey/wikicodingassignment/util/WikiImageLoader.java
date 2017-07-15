package org.cooltey.wikicodingassignment.util;

import android.app.Activity;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class WikiImageLoader {

    // setup an universal init method
	public static ImageLoader init(Activity context){

		ImageLoader imageLoader = ImageLoader.getInstance();

        if(!imageLoader.isInited()) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .resetViewBeforeLoading(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(options)
                    .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                    .build();
            imageLoader.init(config);
        }

		return imageLoader;
	}

}
