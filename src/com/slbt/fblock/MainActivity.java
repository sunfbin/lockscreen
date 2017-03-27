package com.slbt.fblock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.util.Log;   

public class MainActivity extends Activity {
  @Override
  protected void onResume() {
    super.onResume();
    Log.d("MainActivity", "onResume");
  }
	
  @Override
  protected void onStart(){
    super.onStart();
    Log.d("MainActivity", "onStart");
  }
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设备安全管理服务
		DevicePolicyManager policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

		ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);
		// check the admin active status of component
    boolean isAdminActive = policyManager.isAdminActive(componentName);
    if(isAdminActive){
      Log.d("MainActivity", "Admin actived. Lock screen now."); 
      // lock Screen
      policyManager.lockNow();
    }else {
      Log.d("MainActivity", "Admin not actived. Try to get administration for this app."); 
      //Get admin privilege
      Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
      intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
      intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "- Lock Screen -"); //additional explanation
      startActivity(intent);
    }
    finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.main, menu);
	  return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
		  return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
