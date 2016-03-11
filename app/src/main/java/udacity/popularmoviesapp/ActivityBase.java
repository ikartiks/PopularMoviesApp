package udacity.popularmoviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kartiks.ui.BaseDialog;
import com.kartiks.ui.GifImageView;
import com.kartiks.utility.LoggerGeneral;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

/**
 * Created by kartikshah on 28/06/15.
 */
public abstract class ActivityBase extends AppCompatActivity {

    //PS somehow getting resources as instance variable is creating memory issues,and app to crash
    AlertDialog ad;
    BaseDialog baseDialog;

    int screenWidth,screenHeight,statusBarHeight,navigationBarHeight;

    int RC_SIGN_IN =1;
    int REQUEST_READ_CONTACTS =2;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        Resources resources=this.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }else{
            statusBarHeight=0;
        }
        resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        }else{
            navigationBarHeight=0;
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }

    public void showLoader(){
        GifImageView gif=(GifImageView) findViewById(R.id.Loading);
        if(gif!=null)
            gif.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        GifImageView gif=(GifImageView) findViewById(R.id.Loading);
        if(gif!=null)
            gif.setVisibility(View.GONE);
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
            return activeNetworkInfo.isConnected();
        else
            return false;
    }

    public void hideSoftInput(IBinder binder) {
        //myEditText.getWindowToken()
        InputMethodManager mgrs = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgrs.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void showSoftInput(View view) {
        InputMethodManager mgrs = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgrs.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public AlertDialog showCustomMessage(String message) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(this.getResources().getString(R.string.app_name));
        adb.setMessage(message);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = adb.create();
        ad.show();
        return ad;
    }

    public void showToast( String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public boolean isTablet() {

        boolean xlarge = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public boolean isPackagePresent(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = this.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    public int resolveAttribute(int attr){
        TypedValue typedvalueattr = new TypedValue();
        this.getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }



    public void showEndPop() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.app_name));
        adb.setMessage("Are you sure you want to quit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
                finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();

            }
        });
        ad = adb.create();
        ad.show();
    }

    public void handleFailure(Response response){

        try {
            String error=IOUtils.toString(response.errorBody().byteStream());
            try {
                JSONObject vString=new JSONObject(error);
                showToast(vString.getString("value"));

            } catch (JSONException e) {
                LoggerGeneral.e("failed :: " + error);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
