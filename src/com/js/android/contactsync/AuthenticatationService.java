package com.js.android.contactsync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatationService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new AccountAuthenticator(this).getIBinder();
	}
}
