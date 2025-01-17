package com.applozic.mobicomkit.uiwidgets;

import android.content.Context;
import android.content.SharedPreferences;

import com.applozic.mobicomkit.api.MobiComKitClientService;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;

/**
 * Created by devashish on 8/21/2015.
 */
public class ApplozicSetting {

    private Context context;
    public SharedPreferences sharedPreferences;
    private static final String START_NEW_FLOATING_ACTION_BUTTON_DISPLAY = "SETTING_START_NEW_FLOATING_ACTION_BUTTON_DISPLAY";
    private static final String START_NEW_BUTTON_DISPLAY = "SETTING_START_NEW_BUTTON_DISPLAY";
    private static final String NO_CONVERSATION_LABEL = "SETTING_NO_CONVERSATION_LABEL";
    private static final String CONVERSATION_CONTACT_IMAGE_VISIBILITY = "CONVERSATION_CONTACT_IMAGE_VISIBILITY";
    public static final String CUSTOM_MESSAGE_BACKGROUND_COLOR = "CUSTOM_MESSAGE_BACKGROUND_COLOR";
    private static final String SENT_MESSAGE_BACKGROUND_COLOR = "SENT_MESSAGE_BACKGROUND_COLOR";
    private static final String RECEIVED_MESSAGE_BACKGROUND_COLOR = "RECEIVED_MESSAGE_BACKGROUND_COLOR";
    private static final String ONLINE_STATUS_MASTER_LIST = "ONLINE_STATUS_MASTER_LIST";
    private static final String PRICE_WIDGET = "PRICE_WIDGET";
    private static final String SEND_BUTTON_BACKGROUND_COLOR ="SEND_BUTTON_BACKGROUND_COLOR";
    private static final String START_NEW_GROUP ="START_NEW_GROUP";
    private static final String IMAGE_COMPRESSION ="IMAGE_COMPRESSION";
    private static final String MAX_ATTACHMENT_ALLOWED= "MAX_ATTACHMENT_ALLOWED";
    private static final String LOCATION_SHARE_VIA_MAP = "LOCATION_SHARE_VIA_MAP";
    private static final String MAX_ATTACHMENT_SIZE_ALLOWED= "MAX_ATTACHMENT_SIZE_ALLOWED";


    public static ApplozicSetting applozicSetting;

    private ApplozicSetting(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MobiComKitClientService.getApplicationKey(context), context.MODE_PRIVATE);
    }

    public static ApplozicSetting getInstance(Context context) {
        if (applozicSetting == null) {
            applozicSetting = new ApplozicSetting(context);
        }

        return applozicSetting;
    }

    public ApplozicSetting setColor(String key, int color) {
        sharedPreferences.edit().putInt(key, color).commit();
        return this;
    }

    public int getColor(String key) {
        return sharedPreferences.getInt(key, R.color.applozic_theme_color_primary);
    }

    public ApplozicSetting setSentMessageBackgroundColor(int color) {
        sharedPreferences.edit().putInt(SENT_MESSAGE_BACKGROUND_COLOR, color).commit();
        return this;
    }

    public ApplozicSetting setReceivedMessageBackgroundColor(int color) {
        sharedPreferences.edit().putInt(RECEIVED_MESSAGE_BACKGROUND_COLOR, color).commit();
        return this;
    }

    public int getSentMessageBackgroundColor() {
        return sharedPreferences.getInt(SENT_MESSAGE_BACKGROUND_COLOR, R.color.applozic_theme_color_primary);
    }

    public int getReceivedMessageBackgroundColor() {
        return sharedPreferences.getInt(RECEIVED_MESSAGE_BACKGROUND_COLOR, R.color.white);
    }

    public ApplozicSetting showOnlineStatusInMasterList() {
        sharedPreferences.edit().putBoolean(ONLINE_STATUS_MASTER_LIST, true).commit();
        return this;
    }

    public ApplozicSetting hideOnlineStatusInMasterList() {
        sharedPreferences.edit().putBoolean(ONLINE_STATUS_MASTER_LIST, false).commit();
        return this;
    }

    public boolean isOnlineStatusInMasterListVisible() {
        return sharedPreferences.getBoolean(ONLINE_STATUS_MASTER_LIST, false);
    }

    public ApplozicSetting showConversationContactImage() {
        sharedPreferences.edit().putBoolean(CONVERSATION_CONTACT_IMAGE_VISIBILITY, true).commit();
        return this;
    }

    public ApplozicSetting hideConversationContactImage() {
        sharedPreferences.edit().putBoolean(CONVERSATION_CONTACT_IMAGE_VISIBILITY, false).commit();
        return this;
    }

    public boolean isConversationContactImageVisible() {
        return sharedPreferences.getBoolean(CONVERSATION_CONTACT_IMAGE_VISIBILITY, true);
    }

    public ApplozicSetting showStartNewButton() {
        sharedPreferences.edit().putBoolean(START_NEW_BUTTON_DISPLAY, true).commit();
        return this;
    }

    public ApplozicSetting hideStartNewButton() {
        sharedPreferences.edit().putBoolean(START_NEW_BUTTON_DISPLAY, false).commit();
        return this;
    }

    public boolean isStartNewButtonVisible() {
        return sharedPreferences.getBoolean(START_NEW_BUTTON_DISPLAY, false);
    }

    public ApplozicSetting showStartNewFloatingActionButton() {
        sharedPreferences.edit().putBoolean(START_NEW_FLOATING_ACTION_BUTTON_DISPLAY, true).commit();
        return this;
    }

    public ApplozicSetting hideStartNewFloatingActionButton() {
        sharedPreferences.edit().putBoolean(START_NEW_FLOATING_ACTION_BUTTON_DISPLAY, false).commit();
        return this;
    }

    public boolean isStartNewFloatingActionButtonVisible() {
        return sharedPreferences.getBoolean(START_NEW_BUTTON_DISPLAY, false);
    }

    public String getNoConversationLabel() {
        return sharedPreferences.getString(NO_CONVERSATION_LABEL, context.getResources().getString(R.string.no_conversation));
    }

    public ApplozicSetting setNoConversationLabel(String label) {
        sharedPreferences.edit().putString(NO_CONVERSATION_LABEL, label).commit();
        return this;
    }

    public ApplozicSetting showPriceOption() {
        sharedPreferences.edit().putBoolean(PRICE_WIDGET, true).commit();
        return this;
    }

    public ApplozicSetting hidePriceOption() {
        sharedPreferences.edit().putBoolean(PRICE_WIDGET, false).commit();
        return this;
    }

    public boolean isPriceOptionVisible() {
        return sharedPreferences.getBoolean(PRICE_WIDGET, false);
    }

    public ApplozicSetting setSendButtonBackgroundColor(int color) {
        sharedPreferences.edit().putInt(SEND_BUTTON_BACKGROUND_COLOR, color).commit();
        return this;
    }

    public int getSendButtonBackgroundColor() {
        return sharedPreferences.getInt(SEND_BUTTON_BACKGROUND_COLOR, R.color.applozic_theme_color_primary);
    }

    public ApplozicSetting showStartNewGroupButton() {
        sharedPreferences.edit().putBoolean(START_NEW_GROUP, true).commit();
        return this;
    }

    public ApplozicSetting hideStartNewGroupButton() {
        sharedPreferences.edit().putBoolean(START_NEW_GROUP, false).commit();
        return this;
    }

    public boolean isStartNewGroupButtonVisible() {
        return sharedPreferences.getBoolean(START_NEW_GROUP, false);
    }


    public ApplozicSetting enableImageCompression() {
        MobiComUserPreference.getInstance(context).setImageCompressionEnabled(true);
        return this;
    }

    public ApplozicSetting disableImageCompression() {
        MobiComUserPreference.getInstance(context).setImageCompressionEnabled(false);
        return this;
    }

    public boolean isImageCompressionEnabled() {
        return MobiComUserPreference.getInstance(context).isImageCompressionEnabled();
    }

    public ApplozicSetting setCompressedImageSizeInMB(int size) {
        MobiComUserPreference.getInstance(context).setCompressedImageSizeInMB(size);
        return this;

    }

    //==== LOCATION SHARING PREFERENCE =====================


    public ApplozicSetting enableLocationSharingViaMap() {
        setLocationSharingViaMap(true);
        return this;
    }

    public ApplozicSetting disableLocationSharingViaMap() {
        setLocationSharingViaMap(false);
        return this;
    }

    public boolean isLocationSharingViaMap() {
       return sharedPreferences.getBoolean(LOCATION_SHARE_VIA_MAP, true);
    }

    public ApplozicSetting setLocationSharingViaMap(boolean value) {
        sharedPreferences.edit().putBoolean(LOCATION_SHARE_VIA_MAP, value).commit();
        return this;
    }


    //===== END ========================================

    public int getCompressedImageSizeInMB() {
        return MobiComUserPreference.getInstance(context).getCompressedImageSizeInMB();
    }

    public ApplozicSetting setMaxAttachmentAllowed(int maxAttachment) {
        sharedPreferences.edit().putInt(MAX_ATTACHMENT_ALLOWED, maxAttachment).commit();
        return this;
    }

    //Default value is 5.
    public int getMaxAttachmentAllowed(){
       return  sharedPreferences.getInt(MAX_ATTACHMENT_ALLOWED, 5);
    }

    public ApplozicSetting setMaxAttachmentSize(int maxAttachmentSize) {
        sharedPreferences.edit().putInt(MAX_ATTACHMENT_SIZE_ALLOWED, maxAttachmentSize).commit();
        return this;
    }

    //Default file size is 10.
    public int getMaxAttachmentSizeAllowed(){
        return  sharedPreferences.getInt(MAX_ATTACHMENT_SIZE_ALLOWED, 10);
    }

    public boolean clearAll() {
        return sharedPreferences.edit().clear().commit();
    }

}