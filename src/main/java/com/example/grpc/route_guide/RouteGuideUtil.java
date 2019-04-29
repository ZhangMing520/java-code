package com.example.grpc.route_guide;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhangming
 * @date 2019/4/20 20:32
 */
public class RouteGuideUtil {
    private static final double COORD_FACTOR = 1e7;

    public static double getLatitude(Point location) {
        return location.getLatitude() / COORD_FACTOR;
    }

    public static double getLongitude(Point location) {
        return location.getLongitude() / COORD_FACTOR;
    }

    public static URL getDefaultFeaturesFile() {
        return RouteGuideServer.class.getResource("route_guide_db.json");
    }

    public static List<Feature> parseFeatures(URL file) throws IOException {
        try (InputStream in = file.openStream(); Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            //FeatureDatabase.Builder database = FeatureDatabase.newBuilder();

        }

        return null;
    }

    public static boolean exists(Feature feature) {
        return feature != null & !feature.getName().isEmpty();
    }
}
