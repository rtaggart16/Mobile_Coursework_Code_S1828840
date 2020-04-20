/*
 * Name: Ross Taggart
 * Student Number: S1828840
 */

package com.example.s1828840_traffic_scotland.utilities.services;

// Imports

import android.util.Log;

import com.example.s1828840_traffic_scotland.utilities.models.parser.Channel;
import com.example.s1828840_traffic_scotland.utilities.models.parser.ChannelItem;
import com.example.s1828840_traffic_scotland.utilities.models.parser.ItemPoint;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

// END: Imports

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: IParseService
 * Description: Interface that contains method definitions for parsing data
 */
interface IParseService {
    /*
     * Name: Convert_Stream_To_String
     * Description: Method to convert a stream into a string
     * Params:
     *      - stream: The InputStream to process
     */
    String Convert_Stream_To_String(InputStream stream);

    /*
     * Name: Convert_String_To_Channel
     * Description: Method to convert a string into a TrafficScotland channel
     * Params:
     *      - stringData: String representation of an XML feed
     * Returns: Parsed channel
     */
    Channel Convert_String_To_Channel(String stringData);
}

/*
 * Name: Ross Taggart
 * Student Number: S1828840
 * Class: ParseService
 * Description: Class that contains method bodies for parsing data
 */
public class ParseService implements IParseService {

    /*
     * Name: Convert_Stream_To_String
     * Description: Method to convert a stream into a string
     * Params:
     *      - stream: The InputStream to process
     * Returns: XML as string
    */
    public String Convert_Stream_To_String(InputStream stream) {
        // Variable to store the result
        String result = "";

        // Wrap in try-catch to avoid crashes
        try
        {
            // Initialise a new BufferedReader using the stream parameter
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(stream));

            // Variable to store the current line of the stream
            String line = "";

            // Loop while lines can still be processed
            while ((line = buffReader.readLine()) != null)
            {
                // Add the current line to the result
                result += line;
            }

            // Close the reader
            buffReader.close();
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        return result;
    }

    /*
     * Name: Convert_String_To_Channel
     * Description: Method to convert a string into a TrafficScotland channel
     * Params:
     *      - stringData: String representation of an XML feed
     * Returns: Parsed channel
     */
    public Channel Convert_String_To_Channel(String stringData) {
        // Empty channel
        Channel channel = new Channel();

        // Channel item that will be populated during process
        ChannelItem currentChannelItem = null;

        // String variable that specifies if a channel or an item is currently being processed
        String type = "channel";

        try
        {
            // Create a new PullParser instance and populate it with the string data passed in
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(stringData));

            // Get the type of event
            int eventType = xpp.getEventType();

            // Iterate until the end of the XML file is reached
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // A start tag was encountered
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check the name of the tag to determine how to process
                    switch(xpp.getName()) {
                        // Tag is a title
                        case "title":
                            // If the channel is still being processed, set the channel title
                            if(type == "channel") {
                                channel.setTitle(xpp.nextText());
                            }
                            // If an item is being processed, set the item title
                            else
                            {
                                currentChannelItem.setTitle(xpp.nextText());
                            }
                            break;

                        // Tag is a description
                        case "description":
                            // If the channel is still being processed, set the channel description
                            if(type == "channel") {
                                channel.setDescription(xpp.nextText());
                            }
                            // If an item is being processed, set the item description
                            else
                            {
                                currentChannelItem.setDescription(xpp.nextText());
                            }
                            break;

                        // Tag is a link
                        case "link":
                            // If the channel is still being processed, set the channel link
                            if(type == "channel") {
                                channel.setLink(xpp.nextText());
                            }
                            // If an item is being processed, set the item link
                            else {
                                currentChannelItem.setLink(xpp.nextText());
                            }
                            break;

                        // Tag is language. No need to check type, only Channel's have language attributes
                        case "language":
                            channel.setLanguage(xpp.nextText());
                            break;

                        // Tag is copyright. No need to check type, only Channel's have copyright attributes
                        case "copyright":
                            channel.setCopyright(xpp.nextText());
                            break;

                        // Tag is managingEditor. No need to check type, only Channel's have managingEditor attributes
                        case "managingEditor":
                            channel.setManaging_Editor(xpp.nextText());
                            break;

                        // Tag is webMaster. No need to check type, only Channel's have webMaster attributes
                        case "webMaster":
                            channel.setWeb_Master(xpp.nextText());
                            break;

                        // Tag is lastBuildDate. No need to check type, only Channel's have lastBuildDate attributes
                        case "lastBuildDate":
                            channel.setLast_Build_Date(xpp.nextText());
                            break;

                        // Tag is docs. No need to check type, only Channel's have docs attributes
                        case "docs":
                            channel.setDocs(xpp.nextText());
                            break;

                        // Tag is rating. No need to check type, only Channel's have rating attributes
                        case "rating":
                            channel.setRating(xpp.nextText());
                            break;

                        // Tag is ttl. No need to check type, only Channel's have ttl attributes
                        case "ttl":
                            int channelTtl = Integer.parseInt(xpp.nextText());
                            channel.setTTL(channelTtl);
                            break;

                        // Tag is item. The current channel item should be re-instantiated and the processing type should be changed
                        case "item":
                            currentChannelItem = new ChannelItem();
                            type = "item";
                            break;

                        // Tag is point. No need to check type, only Item's have point attributes
                        case "point":
                            // Create a new ItemPoint
                            ItemPoint point = new ItemPoint();

                            String overallText = xpp.nextText();

                            // Extract and parse the double representation of the latitude and longitude
                            double lat = Double.parseDouble(overallText.substring(0, overallText.indexOf(' ')));
                            double lon = Double.parseDouble(overallText.substring(overallText.indexOf(' ') + 1));

                            // Set the latitude and longitude of the point
                            point.setLat(lat);
                            point.setLon(lon);

                            // Assign the point to the item
                            currentChannelItem.setPoint(point);
                            break;

                        // Tag is author. No need to check type, only Item's have author attributes
                        case "author":
                            currentChannelItem.setAuthor(xpp.nextText());
                            break;

                        // Tag is comments. No need to check type, only Item's have comments attributes
                        case "comments":
                            currentChannelItem.setComments(xpp.nextText());
                            break;

                        // Tag is pubDate. No need to check type, only Item's have pubDate attributes
                        case "pubDate":
                            currentChannelItem.setPub_Date(xpp.nextText());
                            break;
                    }
                }
                // An end tag was encountered
                else if(eventType == XmlPullParser.END_TAG)
                {
                    // Check the name of the item. Only interested in end of items
                    switch(xpp.getName())
                    {
                        // Tag is item
                        case "item":
                            // Add the current channel item to the channel and set its value to null
                            channel.addItem(currentChannelItem);
                            currentChannelItem = null;
                            break;
                    }
                }

                // Get the next event
                eventType = xpp.next();

            }
        }
        // Catch errors related to the PullParses
        catch (XmlPullParserException ppEx)
        {
            Log.e("Error","Parsing error" + ppEx.toString());
        }
        // Catch errors related to reading and writing
        catch (IOException ioEx)
        {
            Log.e("Error","IO error during parsing. An uninstantiated channel was returned");
        }
        // Catch general errors that don't fall into the above categories
        catch(Exception ex) {
            Log.e("General Error", ex.getMessage());
        }

        // If the channel has any items, data is present
        if(channel.getItems().size() > 0) {
            channel.setData_Present(true);
        }
        // Channel has no items, data not present
        else
        {
            channel.setData_Present(false);
        }

        // Return the parsed channel
        return channel;
    }
}
