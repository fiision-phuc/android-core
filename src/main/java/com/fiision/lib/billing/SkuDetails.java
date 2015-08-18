package com.fiision.lib.billing;


import com.fasterxml.jackson.annotation.*;

import org.json.*;


/** Represents an in-app product's listing details. */
public final class SkuDetails {

    @JsonProperty("productId")
    public String mSku;

    @JsonProperty("type")
    public String mType;

    @JsonProperty("price")
    public String mPrice;

    @JsonProperty("title")
    public String mTitle;

    @JsonProperty("description")
    public String mDescription;

    @JsonIgnore
    String mItemType;


    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        mItemType = itemType;
//        mJson = jsonSkuDetails;
//        JSONObject o = new JSONObject(mJson);
//        mSku = o.optString("productId");
//        mType = o.optString("type");
//        mPrice = o.optString("price");
//        mTitle = o.optString("title");
//        mDescription = o.optString("description");
    }

    public String getSku() { return mSku; }
    public String getType() { return mType; }
    public String getPrice() { return mPrice; }
    public String getTitle() { return mTitle; }
    public String getDescription() { return mDescription; }

//    @Override
//    public String toString() {
//        return "SkuDetails:" + mJson;
//    }
}
