package JSONControlling;

import Objects.EventEntry;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class JSONReader {

    public LinkedList<EventEntry> getListOfEventsFromJSON() {
        LinkedList<EventEntry> eventEntries = new LinkedList<>();

        fillLinkedListWithEventEntries(getWikiAsJSONObject(), eventEntries);

        return eventEntries;
    }

    private void fillLinkedListWithEventEntries(JSONObject oWiki, LinkedList<EventEntry> eventEntries) {
        JSONArray oEvents = oWiki.getJSONArray("Events");
        for (int i = 0; i < oEvents.length(); i++) {
            JSONObject oEvent = oEvents.getJSONObject(i);
            EventEntry event = new EventEntry(oEvent);
            if (event.doesEventDateExist())
                eventEntries.add(event);
        }
    }

    public JSONObject getWikiAsJSONObject() {
        JSONObject oWiki = null;
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("wiki.json")));
            oWiki = new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oWiki;
    }
}
