package com.example.yaker;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.RawRes;

import com.google.android.gms.maps.GoogleMap;
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

    static List<LatLng> latLngs = new ArrayList<>();


    static int[] colors = {
            Color.rgb(201, 160, 255), //light purple
             Color.rgb(81, 40, 136)  //dark purple
    };

    static float[] startPoints = {
            0.2f, 1f
    };

    static Gradient gradient = new Gradient(colors, startPoints);

    public static HeatmapTileProvider HeatLayer(LatLng location, int spacing, int number) {
        for (int i = 0; i < number; i++){

            latLngs.add(new LatLng((location.latitude-((Math.random()*50)/spacing)+(Math.random()*50)/spacing),(location.longitude-((Math.random()*50)/spacing)+(Math.random()*50)/spacing)));

        }


        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(latLngs)
                .gradient(gradient)
                .build();

        return provider;



    }


}
