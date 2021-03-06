package com.example.asobhy.riverwatch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 10/25/2017.
 */

public class XMLPullParserHandler {

    private List<StationRiverForecast> stationRiverForecasts = new ArrayList<StationRiverForecast>();
    private StationRiverForecast stationRiverForecast;
    private String text;
    private ArrayList<String> date;

    public List<StationRiverForecast> getStationRiverForecasts() {
        return stationRiverForecasts;
    }

    public ArrayList<String> getDate(){
        return date;
    }

    public List<StationRiverForecast> parse(InputStream is) {

        date = new ArrayList<String>();

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("station")) {
                            // create a new instance of stationRiverForecast
                            stationRiverForecast = new StationRiverForecast();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("station")) {
                            // add stationRiverForecast object to list
                            stationRiverForecasts.add(stationRiverForecast);
                        }else if (tagname.equalsIgnoreCase("stationID")) {
                            stationRiverForecast.setStationID(Integer.parseInt(text));
                        }  else if (tagname.equalsIgnoreCase("name")) {
                            stationRiverForecast.setStationName(text);
                        } else if (tagname.equalsIgnoreCase("forecast_cur")) {
                            stationRiverForecast.setForecast_cur(Float.parseFloat(text));
                        } else if (tagname.equalsIgnoreCase("forecast_24")) {
                            stationRiverForecast.setForecast_24(Float.parseFloat(text));
                        } else if (tagname.equalsIgnoreCase("forecast_48")) {
                            stationRiverForecast.setForecast_48(Float.parseFloat(text));
                        } else if (tagname.equalsIgnoreCase("dates_in")) {
                            date.add(text);
                        } else if (tagname.equalsIgnoreCase("dates_24")) {
                            date.add(text);
                        } else if (tagname.equalsIgnoreCase("dates_48")) {
                            date.add(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationRiverForecasts;

    }

}
