package uk.co.mezpahlan.moshiexample.Model.Adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mpahlan on 30/06/16.
 */
public class DateAdapter {
    @ToJson String dateToJson(Date date) {
        return date.toString();
    }

    @FromJson Date dateFromJson(String dateJson) {
        if (dateJson.startsWith("/Date")) {
            return parseDotNetDateFormat(dateJson);
        } else {
            return parseJsonDateFormat(dateJson);
        }
    }

    private Date parseDotNetDateFormat(String dateJson) {
        // TODO: Convert timezone information. For now discard it.
        // Replace Microsoft guff
        dateJson = dateJson.replace("/Date(", "").replace(")/", "");

        // Check if there is an timezone offset
        Matcher matcher = Pattern.compile("\\D").matcher(dateJson);
        if (matcher.find()) {
            int i = matcher.start();
            dateJson = dateJson.substring(0, i);
        };

        long time = Long.parseLong(dateJson);
        return new Date(time);
    }

    private Date parseJsonDateFormat(String dateJson) {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        Date formattedDate = null;
        try {
            formattedDate = simpleDateFormat.parse(dateJson);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
