package com.fiision.lib.billing;


import com.fasterxml.jackson.annotation.*;

import org.json.*;


/** Represents an in-app billing purchase. */
public class Purchase {


    @JsonProperty("orderId")
    public String mOrderId;
    @JsonProperty("packageName")
    public String mPackageName;
    @JsonProperty("productId")
    public String mSku;
    @JsonProperty("purchaseTime")
    public long mPurchaseTime;
    @JsonProperty("purchaseState")
    public int mPurchaseState;
    @JsonProperty("developerPayload")
    public String mDeveloperPayload;
    @JsonProperty("purchaseToken")
    public String mToken;

    @JsonIgnore
    String mItemType;  // ITEM_TYPE_INAPP or ITEM_TYPE_SUBS
    @JsonIgnore
    String mOriginalJson;
    @JsonIgnore
    String mSignature;


    public Purchase(String itemType, String jsonPurchaseInfo, String signature) throws JSONException {
        mItemType = itemType;
        mOriginalJson = jsonPurchaseInfo;
        JSONObject o = new JSONObject(mOriginalJson);
//        mOrderId = o.optString("orderId");
//        mPackageName = o.optString("packageName");
//        mSku = o.optString("productId");
//        mPurchaseTime = o.optLong("purchaseTime");
//        mPurchaseState = o.optInt("purchaseState");
//        mDeveloperPayload = o.optString("developerPayload");
//        mToken = o.optString("token", o.optString("purchaseToken"));
        mSignature = signature;
    }

    public String getItemType() { return mItemType; }
    public String getOrderId() { return mOrderId; }
    public String getPackageName() { return mPackageName; }
    public String getSku() { return mSku; }
    public long getPurchaseTime() { return mPurchaseTime; }
    public int getPurchaseState() { return mPurchaseState; }
    public String getDeveloperPayload() { return mDeveloperPayload; }
    public String getToken() { return mToken; }
    public String getOriginalJson() { return mOriginalJson; }
    public String getSignature() { return mSignature; }

    @Override
    public String toString() { return "PurchaseInfo(type:" + mItemType + "):" + mOriginalJson; }
}
