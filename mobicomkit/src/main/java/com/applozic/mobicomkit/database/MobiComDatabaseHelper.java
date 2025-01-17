package com.applozic.mobicomkit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.applozic.mobicomkit.api.MobiComKitClientService;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.UserClientService;
import com.applozic.mobicommons.commons.core.utils.DBUtils;

public class MobiComDatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 11;

    public static final String _ID = "_id";
    public static final String SMS_KEY_STRING = "smsKeyString";
    public static final String STORE_ON_DEVICE_COLUMN = "storeOnDevice";
    public static final String TO_FIELD = "toField";
    public static final String SMS = "sms";
    public static final String TIMESTAMP = "timeStamp";
    public static final String SMS_TYPE = "SMSType";
    public static final String TIME_TO_LIVE = "timeToLive";
    public static final String CONTACTID = "contactId";
    public static final String SCHEDULE_SMS_TABLE_NAME = "ScheduleSMS";
    public static final String SMS_TABLE_NAME = "sms";
    public static final String CONTACT_TABLE_NAME = "contact";
    public static final String FULL_NAME = "fullName";
    public static final String CONTACT_NO = "contactNO";
    public static final String DISPLAY_NAME = "displayName";
    public static final String CONTACT_IMAGE_LOCAL_URI = "contactImageLocalURI";
    public static final String CONTACT_IMAGE_URL = "contactImageURL";
    public static final String USERID = "userId";
    public static final String EMAIL = "email";
    public static final String APPLICATION_ID = "applicationId";
    public static final String CONNECTED = "connected";
    public static final String LAST_SEEN_AT_TIME = "lastSeenAt";
    public static final String MESSAGE_CONTENT_TYPE = "messageContentType";
    public static final String CONVERSATION_ID = "conversationId";
    public static final String TOPIC_ID = "topicId";
    public static final String CHANNEL_DISPLAY_NAME = "channelName";
    public static final String TYPE = "type";
    public static final String CHANNEL_KEY = "channelKey";
    public static final String USER_COUNT = "userCount";
    public static final String STATUS = "status";
    public static final String ADMIN_ID = "adminId";
    public static final String BLOCKED = "blocked";
    public static final String BLOCKED_BY = "blockedBy";
    public static final String UNREAD_COUNT = "unreadCount";
    public static final String TOPIC_DETAIL = "topicDetail";
    public static final String CREATED = "created";
    public static final String SENDER_USER_NAME = "senderUserName";
    public static final String CHANNEL = "channel";
    public static final String CHANNEL_USER_X = "channel_User_X";

    public static final String CREATE_SCHEDULE_SMS_TABLE = "create table " + SCHEDULE_SMS_TABLE_NAME + "( "
            + _ID + " integer primary key autoincrement  ," + SMS
            + " text not null, " + TIMESTAMP + " INTEGER ,"
            + TO_FIELD + " varchar(20) not null, " + SMS_TYPE + " varchar(20) not null ," + CONTACTID + " varchar(20) , " + SMS_KEY_STRING + " varChar(50), " + STORE_ON_DEVICE_COLUMN + " INTEGER DEFAULT 1, source INTEGER, timeToLive integer) ;";
    public static final String CREATE_SMS_TABLE = "create table sms ( "
            + "id integer primary key autoincrement, "
            + "keyString var(100), "
            + "toNumbers varchar(1000), "
            + "contactNumbers varchar(2000), "
            + "message text not null, "
            + "type integer, "
            + "read integer default 0, "
            + "delivered integer default 0, "
            + "storeOnDevice integer default 1, "
            + "sentToServer integer default 1, "
            + "createdAt integer, "
            + "scheduledAt integer, "
            + "source integer, "
            + "timeToLive integer, "
            + "fileMetaKeyStrings varchar(2000), "
            + "filePaths varchar(2000), "
            + "thumbnailUrl varchar(2000), "
            + "size integer, "
            + "name varchar(2000), "
            + "contentType varchar(200), "
            + "metaFileKeyString varchar(2000), "
            + "blobKeyString varchar(2000), "
            + "canceled integer default 0, "
            + "deleted integer default 0,"
            + "applicationId varchar(2000) null,"
            + "messageContentType integer default 0,"
            + "conversationId integer default 0,"
            + "topicId varchar(300) null,"
            + "channelKey integer default 0,"
            + STATUS + " varchar(200) default 0,"
            + "UNIQUE (keyString,contactNumbers,channelKey))";
    private static final String SMS_BACKUP = "sms_backup";
    public static final String INSERT_INTO_SMS_FROM_SMS_BACKUP_QUERY = "INSERT INTO sms (id,keyString,toNumbers,contactNumbers,message,type,read,delivered,storeOnDevice,sentToServer,createdAt,scheduledAt,source,timeToLive,fileMetaKeyStrings,filePaths,thumbnailUrl,size,name,contentType,metaFileKeyString,blobKeyString,canceled,deleted,applicationId,messageContentType,conversationId,topicId)" +
            " SELECT id,keyString,toNumbers,contactNumbers,message,type,read,delivered,storeOnDevice,sentToServer,createdAt,scheduledAt,source,timeToLive,fileMetaKeyStrings,filePaths,thumbnailUrl,size,name,contentType,metaFileKeyString,blobKeyString,canceled,deleted,applicationId,messageContentType,conversationId,topicId" +
            " FROM " + SMS_BACKUP;
    private static final String DROP_SMS_BACKUP = "DROP TABLE " + SMS_BACKUP;
    private static final String ALTER_SMS_TABLE_FOR_DELETE_COLUMN = "ALTER TABLE " + SMS + " ADD COLUMN deleted integer default 0";
    private static final String ALTER_CONTACT_TABLE_FOR_APPLICATION_ID_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN applicationId varchar(2000) null";
    private static final String ALTER_SMS_TABLE_FOR__APPLICATION_ID_COLUMN = "ALTER TABLE " + SMS + " ADD COLUMN " + APPLICATION_ID + " varchar(2000) null";
    private static final String ALTER_SMS_TABLE_FOR_CONTENT_TYPE_COLUMN = "ALTER TABLE " + SMS + " ADD COLUMN " + MESSAGE_CONTENT_TYPE + " integer default 0";
    private static final String ALTER_CONTACT_TABLE_FOR_CONNECTED_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN " + CONNECTED + " integer default 0";
    private static final String ALTER_CONTACT_TABLE_FOR_LAST_SEEN_AT_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN " + LAST_SEEN_AT_TIME + " integer default 0";
    private static final String ALTER_MESSAGE_TABLE_FOR_CONVERSATION_ID_COLUMN = "ALTER TABLE " + SMS + " ADD COLUMN " + CONVERSATION_ID + " integer default 0";
    private static final String ALTER_MESSAGE_TABLE_FOR_TOPIC_ID_COLUMN = "ALTER TABLE " + SMS + " ADD COLUMN " + TOPIC_ID + " varchar(300) null";
    private static final String ALTER_CONTACT_TABLE_UNREAD_COUNT_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN " + UNREAD_COUNT + " integer default 0";
    private static final String ALTER_CHANNEL_TABLE_UNREAD_COUNT_COLUMN = "ALTER TABLE " + CHANNEL + " ADD COLUMN " + UNREAD_COUNT + " integer default 0";
    private static final String ALTER_CONTACT_TABLE_BLOCKED_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN " + BLOCKED+ " integer default 0";
    private static final String ALTER_CONTACT_TABLE_BLOCKED_BY_COLUMN = "ALTER TABLE " + CONTACT_TABLE_NAME + " ADD COLUMN " + BLOCKED_BY+ " integer default 0";
    private static final String ALTER_SMS_TABLE = "ALTER TABLE " + SMS + " RENAME TO " + SMS_BACKUP;
    private static final String CREATE_CONTACT_TABLE = " CREATE TABLE contact ( " +
            USERID + " VARCHAR(50) primary key, "
            + FULL_NAME + " VARCHAR(200), "
            + CONTACT_NO + " VARCHAR(15), "
            + DISPLAY_NAME + " VARCHAR(25), "
            + CONTACT_IMAGE_URL + " VARCHAR(200), "
            + CONTACT_IMAGE_LOCAL_URI + " VARCHAR(200), "
            + EMAIL + " VARCHAR(100), "
            + APPLICATION_ID + " VARCHAR(2000) null, "
            + CONNECTED + " integer default 0,"
            + LAST_SEEN_AT_TIME + " integer, "
            + UNREAD_COUNT + " integer default 0,"
            + BLOCKED + " integer default 0, "
            + BLOCKED_BY + " integer default 0 "
            + " ) ";

    private static final String CREATE_CHANNEL_TABLE = " CREATE TABLE channel ( " +
            _ID + " integer primary key autoincrement, "
            + CHANNEL_KEY + " integer , "
            + CHANNEL_DISPLAY_NAME + " varchar(200), "
            + ADMIN_ID + " varchar(100), "
            + TYPE + " integer default 0, "
            + UNREAD_COUNT + " integer default 0, "
            + USER_COUNT + "integer "
            + " )";

    private static final String CREATE_CHANNEL_USER_X_TABLE = " CREATE TABLE channel_User_X ( " +
            _ID + " integer primary key autoincrement, "
            + CHANNEL_KEY + " integer , "
            + USERID + " varchar(100), "
            + UNREAD_COUNT + " integer, "
            + STATUS + " integer, "
            + "UNIQUE (" + CHANNEL_KEY + ", " + USERID + "))";

    private static final String CREATE_CONVERSATION_TABLE = " CREATE TABLE conversation ( " +
            _ID + " integer primary key autoincrement, "
            + TOPIC_ID + " varchar(100) , "
            + TOPIC_DETAIL + " varchar(100) , "
            + USERID + " varchar(100), "
            + CREATED + " integer, "
            + SENDER_USER_NAME + " varchar(100), "
            + CHANNEL_KEY + " integer, "
            + "UNIQUE (" + CHANNEL_KEY + ", " + USERID + "))";

    private static final String CREATE_INDEX_SMS_TYPE = "CREATE INDEX IF NOT EXISTS INDEX_SMS_TYPE ON sms (type)";
    private static final String TAG = "MobiComDatabaseHelper";
    private static MobiComDatabaseHelper sInstance;
    private Context context;

    private MobiComDatabaseHelper(Context context) {
        this(context, "MCK_" + MobiComKitClientService.getApplicationKey(context), null, DB_VERSION);
        this.context = context;
    }

    public MobiComDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static MobiComDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MobiComDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //Store Database name in shared preference ...
        if (!DBUtils.isTableExists(database, "sms")) {
            database.execSQL(CREATE_SMS_TABLE);
        }
        if (!DBUtils.isTableExists(database, SCHEDULE_SMS_TABLE_NAME)) {
            database.execSQL(CREATE_SCHEDULE_SMS_TABLE);
        }
        if (!DBUtils.isTableExists(database, "contact")) {
            database.execSQL(CREATE_CONTACT_TABLE);
        }
        if (!DBUtils.isTableExists(database, CHANNEL)) {
            database.execSQL(CREATE_CHANNEL_TABLE);
        }
        if (!DBUtils.isTableExists(database, CHANNEL_USER_X)) {
            database.execSQL(CREATE_CHANNEL_USER_X_TABLE);
        }
        //ALL indexes should go here after creating tables.
        database.execSQL(CREATE_INDEX_SMS_TYPE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        //Note: some user might directly upgrade from an old version to the new version, in that case it may happen that
        //schedule sms table is not present.
        if (newVersion > oldVersion) {
            Log.i(TAG, "Upgrading database from version "
                    + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");

            if (!DBUtils.isTableExists(database, "sms")) {
                database.execSQL(CREATE_SMS_TABLE);
            }
            if (!DBUtils.isTableExists(database, SCHEDULE_SMS_TABLE_NAME)) {
                database.execSQL(CREATE_SCHEDULE_SMS_TABLE);
            }
            if (!DBUtils.isTableExists(database, CHANNEL)) {
                database.execSQL(CREATE_CHANNEL_TABLE);
            }
            if (!DBUtils.isTableExists(database, CHANNEL_USER_X)) {
                database.execSQL(CREATE_CHANNEL_USER_X_TABLE);
            }
            if (!DBUtils.existsColumnInTable(database, "sms", "deleted")) {
                database.execSQL(ALTER_SMS_TABLE_FOR_DELETE_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "sms", "applicationId")) {
                database.execSQL(ALTER_SMS_TABLE_FOR__APPLICATION_ID_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", "applicationId")) {
                database.execSQL(ALTER_CONTACT_TABLE_FOR_APPLICATION_ID_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", UNREAD_COUNT)) {
                database.execSQL(ALTER_CONTACT_TABLE_UNREAD_COUNT_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", "connected")) {
                database.execSQL(ALTER_CONTACT_TABLE_FOR_CONNECTED_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", "lastSeenAt")) {
                database.execSQL(ALTER_CONTACT_TABLE_FOR_LAST_SEEN_AT_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", BLOCKED)) {
                database.execSQL(ALTER_CONTACT_TABLE_BLOCKED_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "contact", BLOCKED_BY)) {
                database.execSQL(ALTER_CONTACT_TABLE_BLOCKED_BY_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "sms", MESSAGE_CONTENT_TYPE)) {
                database.execSQL(ALTER_SMS_TABLE_FOR_CONTENT_TYPE_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "sms", CONVERSATION_ID)) {
                database.execSQL(ALTER_MESSAGE_TABLE_FOR_CONVERSATION_ID_COLUMN);
            }
            if (!DBUtils.existsColumnInTable(database, "sms", TOPIC_ID)) {
                database.execSQL(ALTER_MESSAGE_TABLE_FOR_TOPIC_ID_COLUMN);
            }
            if(!DBUtils.existsColumnInTable(database, CHANNEL, UNREAD_COUNT)){
                database.execSQL(ALTER_CHANNEL_TABLE_UNREAD_COUNT_COLUMN);
            }
            database.execSQL(CREATE_INDEX_SMS_TYPE);
            database.execSQL(ALTER_SMS_TABLE);
            database.execSQL(CREATE_SMS_TABLE);
            database.execSQL(INSERT_INTO_SMS_FROM_SMS_BACKUP_QUERY);
            database.execSQL(DROP_SMS_BACKUP);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new UserClientService(context).updateCodeVersion(MobiComUserPreference.getInstance(context).getDeviceKeyString());
                    } catch (Exception e) {

                    }
                }
            }).start();

        } else {
            onCreate(database);
        }
    }

    @Override
    public synchronized void close() {
        //super.close();
    }

    public int delDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + SCHEDULE_SMS_TABLE_NAME);

        db.execSQL("delete from " + SMS_TABLE_NAME);

        db.execSQL("delete from " + CONTACT_TABLE_NAME);

        db.execSQL("delete from " + CHANNEL);

        db.execSQL("delete from " + CHANNEL_USER_X);

        // db.close();

        return 0;
    }
}