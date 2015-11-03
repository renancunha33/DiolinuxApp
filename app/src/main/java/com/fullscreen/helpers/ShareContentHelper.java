package com.fullscreen.helpers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jackson on 02/11/15.
 */
public class ShareContentHelper {

    public static void share(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("text/plain");
        context.startActivity(intent);
    }
}
