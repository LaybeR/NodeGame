package Windows;

import JSONControlling.JSONReader;
import JSONControlling.JSONWriter;
import Objects.EventEntry;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class WindowWikiView extends JFrame {

    private JTree wikiTree;
    private JTextArea selectedTextArea;
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    LinkedList<EventEntry> eventEntries = new LinkedList<>();
    JSONReader jsonReader = new JSONReader();
    JSONWriter jsonWriter = new JSONWriter();
    EventEntry eventEntry = new EventEntry();
    EventEntry selectedEventEntry = null;

    public WindowWikiView() throws MalformedURLException {
        setWikiTreeUp();
        fillArrayWithEntriesFromJSON();
        setWikiTreeListener();
        setNodeExpandedState(wikiTree,root);
        setSelectedTextAreaUp();
        setUpButtons();
        updateThisFrame();
        setIcon();
    }

    private void setWikiTreeUp() {
        wikiTree = new JTree(root);
        wikiTree.setRootVisible(false);
        add(new JScrollPane(wikiTree));
    }

    private void fillArrayWithEntriesFromJSON() {
        eventEntries = jsonReader.getListOfEventsFromJSON();
        eventEntry.sort(eventEntries);
        fillRootWithAllEvents();
    }

    private void setWikiTreeListener() {
        wikiTree.getSelectionModel().addTreeSelectionListener(e -> {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) wikiTree.getLastSelectedPathComponent();
            if (currentNode == null) return;
            if (currentNode.isLeaf()) {
                selectedEventEntry = eventEntry.getEventEntryFromTreeData(
                        currentNode.toString(),
                        String.valueOf(e.getPath()),
                        eventEntries);
                selectedTextArea.setText(selectedEventEntry.getEntry());
            }
        });
    }

    private void setNodeExpandedState(JTree tree, DefaultMutableTreeNode node) {
        ArrayList<TreeNode> list = Collections.list(node.children());
        for (TreeNode treeNode : list) {
            setNodeExpandedState(tree, (DefaultMutableTreeNode) treeNode);
        }
        TreePath path = new TreePath(node.getPath());
        tree.expandPath(path);
    }

    private void setSelectedTextAreaUp() {
        selectedTextArea = new JTextArea(10, 50);
        add(new JScrollPane(selectedTextArea), BorderLayout.SOUTH);
    }

    private void setUpButtons() {
        JPanel buttons = new JPanel();
        FlowLayout flowOfButtons = new FlowLayout(FlowLayout.LEFT);

        buttons.setLayout(flowOfButtons);

        buttons.add(generateBackToMenu());
        buttons.add(generateSaveButton());
        buttons.add(generateGenerateEventButton());

        add(buttons,BorderLayout.NORTH);
    }

    private JButton generateBackToMenu() {
        JButton backToMenu = new JButton("Back to Main Menu");
        backToMenu.addActionListener(e -> {

        });
        return backToMenu;
    }

    private JButton generateSaveButton() {
        JButton saveChangesToJSON = new JButton("Save Changes to JSON File");
        saveChangesToJSON.addActionListener(e -> {
            if (selectedEventEntry != null) {
                selectedEventEntry.setEntry(selectedTextArea.getText());
                jsonWriter.saveEventsToWikiJSON(eventEntries);
            }
        });
        return saveChangesToJSON;
    }

    private JButton generateGenerateEventButton() {
        JButton generateEventButton = new JButton("Generate New Event");
        generateEventButton.addActionListener(e -> {

        });
        return generateEventButton;
    }

    private void updateThisFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Wiki Tree View");
        this.pack();
        this.setVisible(true);
    }

    private void setIcon() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("icons/globe_icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
    }

    void fillRootWithAllEvents() {
        int currentYear = -2;
        int currentMonth = -2;
        int currentDay = -2;

        DefaultMutableTreeNode currentYearNode = new DefaultMutableTreeNode();
        DefaultMutableTreeNode currentMonthNode = new DefaultMutableTreeNode();
        DefaultMutableTreeNode currentDayNode = new DefaultMutableTreeNode();

        for (EventEntry event : eventEntries) {
            if (currentYear != event.getYear()) {
                currentYear = event.getYear();
                currentMonth = -2;
                currentDay = -2;
                currentYearNode = new DefaultMutableTreeNode(currentYear);
                root.add(currentYearNode);
            }

            if (currentMonth != event.getMonth()) {
                currentMonth = event.getMonth();
                currentDay = -2;
                currentMonthNode = new DefaultMutableTreeNode(event.getMonthAsString());
                currentYearNode.add(currentMonthNode);
            }

            if (currentDay != event.getDay()) {
                currentDay = event.getDay();
                if (currentDay == -1) {
                    currentMonthNode.add(new DefaultMutableTreeNode(event.getTitle()));
                    continue;
                }

                currentDayNode = new DefaultMutableTreeNode(currentDay + ".");
                currentMonthNode.add(currentDayNode);
                currentDayNode.add(new DefaultMutableTreeNode(event.getTitle()));
                continue;
            }

            if (currentDay == -1)
                currentMonthNode.add(new DefaultMutableTreeNode(event.getTitle()));
            else
                currentDayNode.add(new DefaultMutableTreeNode(event.getTitle()));
        }
    }

    public static void boot() {
        SwingUtilities.invokeLater(() -> {
            try {
                new WindowWikiView();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
}
