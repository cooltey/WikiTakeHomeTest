package org.cooltey.wikicodingassignment.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Search Response Query Model
 */

public class SearchResponseQuery {

    @SuppressWarnings("unused") @Nullable private List<SearchResponseItem> pages;

    @Nullable public List<SearchResponseItem> pages() {
        return pages;
    }
}