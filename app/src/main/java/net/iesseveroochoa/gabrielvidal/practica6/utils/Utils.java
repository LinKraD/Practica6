package net.iesseveroochoa.gabrielvidal.practica6.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Utils {
    public static int getTipoPantalla(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayout;
    }

    public static int getOrientacionPantalla(Context context) {
        Display getOrient= ((Activity) context).getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        int width;
        int height;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            width = display.getWidth();height = display.getHeight();
        } else {
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        if (width == height) {
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (width < height) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    public static void definirFormatoReciclerView(Context context, RecyclerView rcView) {
        int orientacionPantalla = getOrientacionPantalla(context);
        int tipoPantalla = getTipoPantalla(context);
        if ((tipoPantalla == Configuration.SCREENLAYOUT_SIZE_SMALL) || (tipoPantalla == Configuration.SCREENLAYOUT_SIZE_NORMAL)) {
            if (orientacionPantalla == Configuration.ORIENTATION_PORTRAIT) {
                rcView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            }else{
                rcView.setLayoutManager(new GridLayoutManager(context, 2));
            }
        } else {
            if (orientacionPantalla == Configuration.ORIENTATION_LANDSCAPE) {
                rcView.setLayoutManager(new GridLayoutManager(context, 3));
            } else {
                rcView.setLayoutManager(new GridLayoutManager(context, 2));
            }
        }
    }
}


