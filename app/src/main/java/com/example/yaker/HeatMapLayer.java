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

    List<LatLng> latLngs = null;

    int[] colors = {
            Color.rgb(81, 40, 136), // green
            Color.rgb(201, 160, 255)   // red
    };

    float[] startPoints = {
            0.2f, 1f
    };

    Gradient gradient = new Gradient(colors, startPoints);

    private void addHeatMap() {

        try {
            latLngs = readItems(R.raw.LatLang);
        } catch (JSONException e) {
            //Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .data(latLngs)
                .gradient(gradient)
                .build();

        TileOverlay overlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));

    }

    private List<LatLng> readItems(@RawRes int resource) throws JSONException {
        List<LatLng> result = new ArrayList<>();
        InputStream inputStream = Context.getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            result.add(new LatLng(lat, lng));
        }
        return result;
    }
}
