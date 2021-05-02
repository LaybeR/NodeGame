package Windows;

import JSONControlling.JSONHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class WindowWikiView extends JFrame {

    private JTree gameTree;
    private JTextArea selectedTextArea;
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    JSONHandler jsonHandler;

    public WindowWikiView(JSONHandler generalHandler) throws MalformedURLException {
        jsonHandler = generalHandler;
        jsonHandler.readGameFilesJSON();

        setUpSelectedTextArea();
        setUpButtons();

        setUpGameTree();
        setGameTreeListener();
        setNodeExpandedState(gameTree, root);

        updateThisFrame();
        setMinSizeOfThisFrame();
        setIcon();
    }

    private void setUpGameTree() {
        gameTree = new JTree(root);
        gameTree.setRootVisible(false);
        add(new JScrollPane(gameTree));
    }

    private void setGameTreeListener() {
        gameTree.getSelectionModel().addTreeSelectionListener(e -> {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) gameTree.getLastSelectedPathComponent();
            if (currentNode == null) return;
            if (currentNode.isLeaf()) {
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

    private void setUpSelectedTextArea() {
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

        add(buttons, BorderLayout.NORTH);
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

    private void setMinSizeOfThisFrame() {
        this.setMinimumSize(this.getSize());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeIfNecessary();
            }
        });
    }

    private void resizeIfNecessary() {
        Dimension d = this.getSize();
        Dimension minD = this.getMinimumSize();
        if (d.width < minD.width) d.width = minD.width;
        if (d.height < minD.height) d.height = minD.height;
        this.setSize(d);
    }

    private void setIcon() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("Icons/globe_icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        setIconImage(img);
    }

    public static void boot(JSONHandler jsonHandler) {
        SwingUtilities.invokeLater(() -> {
            try {
                new WindowWikiView(jsonHandler);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
}
