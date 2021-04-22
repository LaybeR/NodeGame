package JSONControlling;

import Objects.EventEntry;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.LinkedList;

public class JSONWriter {

    public void saveEventsToWikiJSON(LinkedList<EventEntry> eventEntries) {
        JSONObject oWiki = new JSONObject();
        JSONArray oEvents = new JSONArray();
        for (EventEntry event : eventEntries)
            oEvents.put(getEventEntryAsJSONObject(event));
        oWiki.put("Events", oEvents);
        File wikiJSONFile = new File(String.valueOf(Paths.get("wiki.json")));
        String oWikiAsString = oWiki.toString();
        oWikiAsString = reformatWikiString(oWikiAsString);
        writeToFile(wikiJSONFile, oWikiAsString);
    }

    private JSONObject getEventEntryAsJSONObject(EventEntry event) {
        JSONObject oEvent = OrderedJSONObjectFactory.create();
        oEvent.put("day", event.getDay());
        oEvent.put("month", event.getMonth());
        oEvent.put("year", event.getYear());
        oEvent.put("title", event.getTitle());
        oEvent.put("entry", event.getEntry());
        return oEvent;
    }

    private String reformatWikiString(String oWikiString) {
        oWikiString = oWikiString.replaceAll("\\[","[\n");
        oWikiString = oWikiString.replaceAll("]","\n]");
        oWikiString = oWikiString.replaceAll("\\{","{\n");
        oWikiString = oWikiString.replaceAll("}","\n}");
        oWikiString = oWikiString.replaceAll(",",",\n");
        return oWikiString;
        //oWikiString = oWikiString.replaceAll("","");
    }

    private void writeToFile(File f, String s) {
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
