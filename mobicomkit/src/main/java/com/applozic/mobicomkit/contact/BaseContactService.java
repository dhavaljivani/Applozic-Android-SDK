package com.applozic.mobicomkit.contact;

import android.content.Context;
import android.graphics.Bitmap;

import com.applozic.mobicommons.people.contact.Contact;

import java.util.Date;
import java.util.List;

/**
 * Created by adarsh on 7/7/15.
 */
public interface BaseContactService {

    void add(Contact contact);

    void addAll(List<Contact> contactList);

    void deleteContact(Contact contact);

    void deleteContactById(String contactId);

    List<Contact> getAll();

    Contact getContactById(String contactId);

    void updateContact(Contact contact);

    void upsert(Contact contact);

    Bitmap downloadContactImage(Context context, Contact contact);

    Contact getContactReceiver(List<String> items, List<String> userIds);

    boolean isContactExists(String contactId);

    void updateConnectedStatus(String contactId, Date date, boolean connected);

    void updateUserBlocked(String userId,boolean userBlocked);

    void updateUserBlockedBy(String userId,boolean userBlockedBy);
}
