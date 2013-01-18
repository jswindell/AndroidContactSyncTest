package com.js.android.contactsync;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

	private static final String TAG = "SyncAdapter";
	
	private final Context mContext;
	
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mContext = context;
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		Log.i(TAG, "onPerformSync");
		
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();
		
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
		   .withValue(RawContacts.ACCOUNT_TYPE, account.type)
		   .withValue(RawContacts.ACCOUNT_NAME, account.name)
		   .build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
		   .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		   .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
		   .withValue(StructuredName.DISPLAY_NAME, "FirstName LastName")
		   .build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
		   .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		   .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		   .withValue(Phone.NUMBER, "646-885-2613")
		   .build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
		   .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		   .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
		   .withValue(Email.ADDRESS, "address@host.com")
		   .build());
		
		try {
			mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
