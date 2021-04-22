package JSONControlling;

import Objects.EventEntry;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONHandler {

    private boolean readJSON = false;
    private JSONObject oWiki = null;
    private final JSONReader jsonReader = new JSONReader();
    private final JSONWriter jsonWriter = new JSONWriter();

    public void updateWikiEvents(LinkedList<EventEntry> eventEntries) {
        oWiki.put("Events", jsonWriter.getJSONArrayOfEventEntries(eventEntries));
    }

    public void writeWikiToJSON() {
        jsonWriter.writeToWikiJSON(oWiki);
    }

    public void readWikiJSON() {
        if (readJSON) return;
        oWiki = jsonReader.getWikiAsJSONObject();
        readJSON = true;
    }

    public LinkedList<EventEntry> getEventEntriesFromWikiJSON() {
        LinkedList<EventEntry> eventEntries = new LinkedList<>();
        if (oWiki == null)
            return eventEntries;
        jsonReader.fillLinkedListWithEventEntries(oWiki, eventEntries);
        return eventEntries;
    }
}
