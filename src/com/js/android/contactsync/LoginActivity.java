package com.js.android.contactsync;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AccountAuthenticatorActivity {

	private static final String ACCOUNT_TYPE = "com.js.contact_account";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText loginName = (EditText)findViewById(R.id.editText1),
					passwordText = (EditText)findViewById(R.id.editText2);
				
				// authenticate user - return on failure
				
				Account account = new Account(loginName.getText().toString(), ACCOUNT_TYPE);
				AccountManager accountManager = AccountManager.get(getApplicationContext());
				if (!accountManager.addAccountExplicitly(account, passwordText.getText().toString(), null)) {
					Toast.makeText(getApplicationContext(), "Unable to create account", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Bundle result = new Bundle();
				result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
				result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
				setAccountAuthenticatorResult(result);
				
				finish();
			}
		});
	}
}
