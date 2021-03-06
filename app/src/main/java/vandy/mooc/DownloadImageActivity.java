package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
    Handler handler;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
        super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.

        final Uri uri=this.getIntent().getData();
        Log.d("data:",uri.toString());
        try {
            URL url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.
        final Intent returnIntent=new Intent();
        handler=new Handler();
        Runnable downloadRunnable=new Runnable() {
            @Override
            public void run() {
                final Uri uriData=DownloadUtils.downloadImage(DownloadImageActivity.this,uri);
                Log.d("PATH:",uriData.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        returnIntent.putExtra("dataUri",uriData.toString());
                        setResult(RESULT_OK,returnIntent);
                        finish();
                    }
                });
            }
        };
        Thread thread=new Thread(downloadRunnable);
        thread.start();
    }
}
