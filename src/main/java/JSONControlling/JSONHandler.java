package JSONControlling;

import org.json.JSONObject;

public class JSONHandler {

    private boolean readJSON = false;
    private JSONObject oGameFile = null;
    private final JSONReader jsonReader = new JSONReader();
    private final JSONWriter jsonWriter = new JSONWriter();

    public void readGameFilesJSON() {
        if (readJSON) return;
        oGameFile = jsonReader.readGamesFile();
        readJSON = true;
    }
}
