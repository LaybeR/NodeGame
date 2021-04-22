package Objects;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedList;

public class EventEntry {
    int day = -2;
    int month = -2;
    int year = -2;
    String title = "";
    String entry = "";

    final int ILLEGAL_DATE = -2;
    final int NO_DAY_SPECIFIED = -1;
    final String[] MONTH = {"Kein Monat", "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

    public EventEntry(JSONObject event) {
        day = event.getInt("day");
        month = event.getInt("month");
        year = event.getInt("year");
        title = event.getString("title");
        entry = event.getString("entry");
        if (!doesEventDateExist()) {
            day = month = year = -2;
        }
    }

    public EventEntry() {
    }

    public boolean doesEventDateExist() {
        if (day == ILLEGAL_DATE || month == ILLEGAL_DATE || year == ILLEGAL_DATE) return false;
        if (day == NO_DAY_SPECIFIED && 0 < month && month < 13 && year < 0) return true;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(String.format("%04d-%02d-%02d", year, month, day));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasEqualPath(String path) {
        if (day != NO_DAY_SPECIFIED)
            return path.equals("[Root, " + year + ", " + MONTH[month] + ", " + day + "., " + title + "]");
        else
            return path.equals("[Root, " + year + ", " + MONTH[month] + ", " + title + "]");
    }

    public EventEntry getEventEntryFromTreeData(String name, String path, LinkedList<EventEntry> entries) {
        LinkedList<EventEntry> resultsLeft = new LinkedList<>();
        for (EventEntry event : entries) {
            if (event.getTitle().equals(name))
                resultsLeft.add(event);
        }
        if (resultsLeft.size() == 0)
            return null;
        if (resultsLeft.size() == 1)
            return resultsLeft.get(0);
        resultsLeft.removeIf(event -> !event.hasEqualPath(path));
        if (resultsLeft.size() == 1)
            return resultsLeft.get(0);
        return null;
    }

    public void sort(LinkedList<EventEntry> entries) {
        Comparator<EventEntry> eventEntryComparator = (o1, o2) -> {
            if (o1.getDay() == o2.getDay() && o1.getMonth() == o2.getMonth() && o1.getYear() == o2.getYear())
                return 0;
            int returnValue = o1.getYear() - o2.getYear();
            if (returnValue != 0)
                return returnValue;
            else {
                returnValue = o1.getMonth() - o2.getMonth();
                if (returnValue != 0)
                    return returnValue;
                else {
                    return o1.getDay() - o2.getDay();
                }
            }
        };
        entries.sort(eventEntryComparator);
    }

    @Override
    public String toString() {
        return title;
    }

    /*Getter*/

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public String getMonthAsString() {
        return MONTH[month];
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getEntry() {
        return entry;
    }

    /*Setter*/

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
