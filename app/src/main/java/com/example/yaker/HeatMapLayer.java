package com.example.yaker;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.RawRes;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeatMapLayer {

    static List<LatLng> latLngs = null;

    static int[] colors = {
            Color.rgb(81, 40, 136), // green
            Color.rgb(201, 160, 255)   // red
    };

    static float[] startPoints = {
            0.2f, 1f
    };

    static Gradient gradient = new Gradient(colors, startPoints);

    public static HeatmapTileProvider HeatLayer() {

        latLngs.add(new LatLng(39.1836, 96.5717));
        latLngs.add(new LatLng(39.1837, 96.5718));
        latLngs.add(new LatLng(39.1838, 96.5716));
        latLngs.add(new LatLng(39.1835, 96.5719));


        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(latLngs)
                .gradient(gradient)
                .build();

        return provider;

        //TileOverlay overlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

    }

}
