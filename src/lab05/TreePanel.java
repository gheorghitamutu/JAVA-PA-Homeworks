package lab05;

import com.teamdev.jxdocument.Document;
import lab04.*;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class TreePanel
        extends JPanel
        implements TreeSelectionListener {
    private JEditorPane documentInfoPane;
    private JTree tree;

    private Catalog catalog;

    TreePanel(Catalog catalog) {
        super();
        this.catalog = catalog;
        this.setMaximumSize(new Dimension(0, 600));

        //Create the nodes.
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(catalog.getName());
        addChildrenToNode(top);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //this.setBorder(BorderFactory.createLineBorder(Color.blue));

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        this.setLayout(gridBagLayout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 40; // --> 800x600



        gridBagLayout.setConstraints(this, gbc);

        JScrollPane treeView = new JScrollPane(tree);


        documentInfoPane = new JEditorPane();
        documentInfoPane.setEditable(false);
        rootMessage();
        documentInfoPane.setPreferredSize(new Dimension(0, 200));

        JScrollPane infoItemView = new JScrollPane(documentInfoPane);

        gbc.insets = new Insets(10, 10, 10, 10); // this crap makes gaps

        gbc.gridy = 0;
        gbc.gridx = 0;
        gridBagLayout.setConstraints(treeView, gbc);
        this.add(treeView, gbc);

        gbc.insets = new Insets(10, 10, 10, 10); // this crap makes gaps

        gbc.gridy = 1;
        gridBagLayout.setConstraints(infoItemView, gbc);
        this.add(infoItemView, gbc);

        gbc.insets = new Insets(10, 10, 10, 10); // this crap makes gaps   t - l - b - r

        gbc.gridy = 2;
        JPanel bottomPanel = new BottomMenu(this.catalog);
        gridBagLayout.setConstraints(bottomPanel, gbc);
        this.add(bottomPanel, gbc);
    }
    
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        changeDisplayPanelText(node);
    }

    private void changeDisplayPanelText(DefaultMutableTreeNode node){
        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        Article article;
        Book book;
        Manual manual;
        try {
            manual = (Manual) nodeInfo;
        }
        catch (ClassCastException e) {
            manual = null;
        }

        try {
            book = (Book) nodeInfo;
        }
        catch (ClassCastException e) {
            book = null;
        }

        try {
            article = (Article) nodeInfo;
        }
        catch (ClassCastException e){
            article = null;
        }

        if (node.isLeaf()) {

            if(article != null) {
                displayObjectFileContent(article);
            }
            else if(book != null) {
                displayObjectFileContent(book);
            }
            else if(manual != null) {
                displayObjectFileContent(manual);
            }
            else if (node == tree.getModel().getRoot()) {
                documentInfoPane.setText("This catalog is empty!\n");
            }
        } else {
            int count = 0;
            StringBuilder sb = new StringBuilder();
            DefaultMutableTreeNode itemNode;

            while(count < tree.getModel().getChildCount(node)){
                itemNode = (DefaultMutableTreeNode) node.getChildAt(count);
                try {
                    manual = (Manual) itemNode.getUserObject();
                }
                catch (ClassCastException e) {
                    manual = null;
                }

                try {
                    book = (Book) itemNode.getUserObject();
                }
                catch (ClassCastException e) {
                    book = null;
                }

                try {
                    article = (Article) itemNode.getUserObject();
                }
                catch (ClassCastException e){
                    article = null;
                }
                if(article != null) {
                    sb.append(article.getTitle());
                }
                else if(book != null) {
                    sb.append(book.getTitle());
                }
                else if(manual != null) {
                    sb.append(manual.getTitle());
                }
                sb.append("\n");
                count++;
            }
            documentInfoPane.setText(String.format("This directory has %d items!\n\n",
                    tree.getModel().getChildCount(node)) + sb.toString());
        }
    }

    private void rootMessage() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getModel().getRoot();

        changeDisplayPanelText(node);
    }

    private void displayObjectFileContent(Object obj) {
        StringBuilder paneContent = new StringBuilder();
        String path = "";
        String formattedTitle = "Title:        %s\n";
        String formattedYear = "Year:       %s\n";
        String formattedPath = "Path:       %s\n";
        String formattedAuthors ="Authors:  %s\n";

        try {
            Manual manual = (Manual) obj;
            paneContent.append(String.format(formattedTitle, manual.getTitle()));

            paneContent.append(String.format(formattedYear, manual.getYear()));

            paneContent.append(String.format(formattedYear, manual.getPath()));
            path = manual.getPath();

            paneContent.append(String.format(formattedAuthors, manual.getAuthors().toString()
                    .replace("[", "")
                    .replace("]", "")));
        }
        catch (ClassCastException e) {
            // do nothing
        }

        try {
            Book book = (Book) obj;

            paneContent.append(String.format(formattedTitle, book.getTitle()));

            paneContent.append(String.format(formattedYear, book.getYear()));

            paneContent.append(String.format(formattedPath, book.getPath()));
            path = book.getPath();

            paneContent.append(String.format(formattedAuthors, book.getAuthors().toString()
                    .replace("[", "")
                    .replace("]", "")));
        }
        catch (ClassCastException e) {
            // do nothing
        }

        try {
            Article article = (Article) obj;

            paneContent.append(String.format(formattedTitle, article.getTitle()));

            paneContent.append(String.format(formattedYear, article.getYear()));

            paneContent.append(String.format(formattedPath, article.getPath()));
            path = article.getPath();

            paneContent.append(String.format(formattedAuthors, article.getAuthors().toString()
                    .replace("[", "")
                    .replace("]", "")));
        }
        catch (ClassCastException e){
            // do nothing
        }

        paneContent.append("\n\n");

        String content;
        try {
            if (path != null && new File(path).exists()) {
                FileInputStream inputStream = new FileInputStream(new File(path));
                if (is_pdf(IOUtils.toByteArray(inputStream))) {
                    content = pdfToText(new File(path));

                } else {
                    content = IOUtils.toString(inputStream);
                }

                paneContent.append(content);
                if(content.isEmpty()){
                    paneContent.append("File is empty or couldn't be opened!");
                }
            } else { //null path
                paneContent.append("File associated with this node does not exist!");
            }
        } catch (IOException e) {
            // do nothing
        }
        finally {
            documentInfoPane.setText(paneContent.toString());
        }
    }

    private void addChildrenToNode(DefaultMutableTreeNode node) {
        for(Item item: catalog.getItems()){
            node.add(new DefaultMutableTreeNode(item));
        }
    }

    private boolean is_pdf(byte[] data) {
        if (data != null && data.length > 4 &&
                data[0] == 0x25 && // %
                data[1] == 0x50 && // P
                data[2] == 0x44 && // D
                data[3] == 0x46 && // F
                data[4] == 0x2D) { // -

            // version 1.3 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x33 &&
                    data[data.length - 7] == 0x25 && // %
                    data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x45 && // E
                    data[data.length - 4] == 0x4F && // O
                    data[data.length - 3] == 0x46 && // F
                    data[data.length - 2] == 0x20 && // SPACE
                    data[data.length - 1] == 0x0A) { // EOL
                return true;
            }

            // version 1.3 file terminator
            if (data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x34 &&
                    data[data.length - 6] == 0x25 && // %
                    data[data.length - 5] == 0x25 && // %
                    data[data.length - 4] == 0x45 && // E
                    data[data.length - 3] == 0x4F && // O
                    data[data.length - 2] == 0x46 && // F
                    data[data.length - 1] == 0x0A) { // EOL
                return true;
            }

            // version ?? file terminator
            return data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x35 &&
                    data[data.length - 6] == 0x25 &&
                    data[data.length - 5] == 0x25 &&
                    data[data.length - 4] == 0x45 &&
                    data[data.length - 3] == 0x4F &&
                    data[data.length - 2] == 0x46 &&
                    data[data.length - 1] == 0x0A;
        }
        return false;
    }

    private String pdfToText(File file) {
        Document document = new Document(file);
        int pageCount = 0;
        String pageText = "";
        if (document.getPageCount() > 0) {
            while(pageCount < document.getPageCount()){
                try {
                    pageText = document.getPageAt(pageCount).getText().getText();
                    document.close();
                }
                catch (Exception e) {
                    pageCount++;
                }
                if(document.isClosed()){
                    return String.format("Showing only the first readable (page %d) page of the PDF document!\n\n\n",
                            pageCount)
                            + pageText;
                }
            }
        }
        else {
            document.close();
            return "PDF is empty!";
        }

        if(!document.isClosed()) {
            document.close();
        }

        return "PDF has no readable pages!";
    }
}