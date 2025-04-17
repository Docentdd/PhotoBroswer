package directory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class PhotoBrowserUI {
    private final JFrame frame;
    private final PhotoCollection photoCollection;

    public PhotoBrowserUI() {
        photoCollection = new PhotoCollection();
        frame = new JFrame("Photo Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        initComponents();
        frame.setVisible(true);
    }

    private void initComponents() { // my first 'window' where I can
        JPanel panel = new JPanel(new BorderLayout());

        JButton addB = new JButton("Add Photo");
        JButton searchB = new JButton("Search Photos");

        JButton showAllPhotosB = new JButton("Show All Photos");
        JButton removeB = new JButton("Remove Photo");
        addB.setMnemonic('A'); // Alt + A
        searchB.setMnemonic('S'); // Alt + S
        removeB.setMnemonic('R'); // Alt + R
        showAllPhotosB.setMnemonic('P');

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addB);
        buttonPanel.add(removeB);
        buttonPanel.add(searchB);
        buttonPanel.add(showAllPhotosB);

        panel.add(buttonPanel, BorderLayout.NORTH);
//that doen't necessary
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
//
        addB.addActionListener(e -> addPhoto());
        removeB.addActionListener(e -> removePhoto());
        searchB.addActionListener(e -> SearchFormWindow());
        showAllPhotosB.addActionListener(e -> showAllPhotos());
    }

    private void addPhoto() { //actionListener for addB -> calls the PhotoForm for inputting the details of the photo
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { // check is the
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            PhotoFormWindow(imagePath); // FORM title has regex 
        } else {
            JOptionPane.showMessageDialog(frame, "No photo selected.");
        }
    }
    private void removePhoto() {
        List<Photo> photos = photoCollection.getPhotos();
        if (photos.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No photos to remove.");
            return;
        }

        JFrame removeFrame = new JFrame("Remove Photo");
        removeFrame.setSize(400, 300);
        removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeFrame.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(listPanel);

        ButtonGroup buttonGroup = new ButtonGroup();
        Map<JRadioButton, Photo> buttonPhotoMap = new HashMap<>();

        for (Photo photo : photos) {
            JRadioButton radioButton = new JRadioButton(photo.getTitle());
            buttonGroup.add(radioButton);
            listPanel.add(radioButton);
            buttonPhotoMap.put(radioButton, photo);
        }

        JButton removeB = new JButton("Remove");

        removeFrame.add(scrollPane, BorderLayout.CENTER);
        removeFrame.add(removeB, BorderLayout.SOUTH);

        removeB.addActionListener(e -> {
            for (Map.Entry<JRadioButton, Photo> entry : buttonPhotoMap.entrySet()) {
                if (entry.getKey().isSelected()) {
                    photoCollection.removePhoto(entry.getValue());
                    JOptionPane.showMessageDialog(frame, "Photo removed: " + entry.getValue().getTitle());
                    removeFrame.dispose();
                    showAllPhotos();
                    return;
                }
            }
            JOptionPane.showMessageDialog(removeFrame, "No photo selected.");
        });

        removeFrame.setVisible(true);
    }

    private void PhotoFormWindow(String imagePath) {
        JFrame detailsFrame = new JFrame("Enter Photo Details");
        detailsFrame.setSize(400, 300);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        JTextField titleField = new JTextField();
        JTextField tagsField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField collectionsField = new JTextField();


        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Tags (comma separated):"));
        formPanel.add(tagsField);
        formPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Collections (comma separated):"));
        formPanel.add(collectionsField);


        JButton submitButton = new JButton("Add Photo");

        detailsFrame.add(formPanel, BorderLayout.CENTER);
        detailsFrame.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                Set<String> tags = new HashSet<>(Arrays.asList(tagsField.getText().split(","))); // get the tags
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
                String description = descriptionField.getText(); // no regex can be whatever 
                Set<String> collections = new HashSet<>(Arrays.asList(collectionsField.getText().split(","))); // the same like tags

                Photo photo = new Photo(date, description, imagePath);
                photo.setTitle(title); // set the title using setTitle where I have regex
                tags.forEach(photo::addTag);
                collections.forEach(photo::addCollection);
                photoCollection.addPhoto(photo);


                detailsFrame.dispose(); // close
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(detailsFrame, "Error adding photo: " + ex.getMessage());
            }
        });
        detailsFrame.setVisible(true);
    }//checked

    private void SearchFormWindow() {
        JFrame searchFrame = new JFrame("Search Photos");
        searchFrame.setSize(400, 300);
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        JTextField tagsField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField descriptionField = new JTextField();
        JCheckBox andOrToggle = new JCheckBox("AND search for tags");

        formPanel.add(new JLabel("Tags (comma separated):"));
        formPanel.add(tagsField);
        formPanel.add(new JLabel("Date range (yyyy-mm-dd,yyyy-mm-dd):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("AND/OR search:"));
        formPanel.add(andOrToggle);

        JButton searchB = new JButton("Search");

        searchFrame.add(formPanel, BorderLayout.CENTER);
        searchFrame.add(searchB, BorderLayout.SOUTH);

        searchB.addActionListener(e -> {
            boolean isAndSearch = andOrToggle.isSelected(); //check box
            //and search works only for tags
            searchPhotos(tagsField, dateField, descriptionField, isAndSearch); // MAY be search obly by one(title or tag or description)
            searchFrame.dispose(); // close
        });

        searchFrame.setVisible(true);
    }//checked

    private void searchPhotos(JTextField tagsField, JTextField dateField, JTextField descriptionField, boolean isAndSearch) {
        try {
            String tags = tagsField.getText();
            String description = descriptionField.getText();
            String dateRange = dateField.getText();

            Set<String> searchTags = new HashSet<>(Arrays.asList(tags.split(",")));
            List<Photo> results = new ArrayList<>();

            if (!tags.isEmpty()) {
                if (isAndSearch) {
                    results.addAll(photoCollection.searchByTags(searchTags, true)); //containsALL
                } else {
                    results.addAll(photoCollection.searchByTags(searchTags, false)); //anyMatch
                }
            }

            if (!description.isEmpty()) {
                results.addAll(photoCollection.searchByDescription(description));
            }

            if (!dateRange.isEmpty()) { //have a range
                String[] dates = dateRange.split(",");
                Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(dates[0]);
                Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(dates[1]);
                results.addAll(photoCollection.searchByDate(fromDate, toDate));
            }

            displaySearchResults(results); //that method get the List<Photo>
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error searching photos: " + ex.getMessage());
        }
    }
    private void displaySearchResults(List<Photo> results) {
        JFrame searchFrame = new JFrame("Search Results");
        searchFrame.setSize(800, 600);
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //will not close the 'first' window

        JPanel resultsPanel = new JPanel(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        for (Photo photo : results) {
            //ImageIcon create from the file path -> a picture icon
            //getImage retuns the image and after that we get scaled version of the photo
            //Scale_Smooth provides a smooth, high-quality scaling algorithm.
            ImageIcon PhotoIcon = new ImageIcon(new ImageIcon(photo.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            JButton thumbnailButton = new JButton(PhotoIcon); // whole picture is a button -> use for informationPhoto
            thumbnailButton.addActionListener(e -> inforamtionPhoto(photo)); //create a new window with the information of the photo(title, tags...)

            JPanel photoPanel = new JPanel(new BorderLayout());
            photoPanel.add(thumbnailButton, BorderLayout.CENTER);
            photoPanel.add(new JLabel(photo.getTitle()), BorderLayout.SOUTH); // at the bootom title
            resultsPanel.add(photoPanel);
        }

        searchFrame.add(scrollPane, BorderLayout.CENTER);
        searchFrame.setVisible(true);
    }
    private void inforamtionPhoto(Photo photo) { //Action Listener
        try {
            JLabel imageLabel = new JLabel(photo.toString()); // 

            JFrame imageFrame = new JFrame(photo.getTitle());
            imageFrame.setSize(600, 400);
            imageFrame.add(imageLabel);
            imageFrame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error displaying photo: " + e.getMessage());
        }
    }

    private void showAllPhotos() {
        JFrame allPhotosFrame = new JFrame("All Photos");
        allPhotosFrame.setSize(800, 600);
        allPhotosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel allPhotosPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(allPhotosPanel);

        for (Photo photo : photoCollection.getPhotos()) {
            JPanel photoPanel = new JPanel(new BorderLayout());

            ImageIcon PhotoIcon = new ImageIcon(new ImageIcon(photo.getImagePath()).getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH));
            JLabel PhotoLabel = new JLabel(PhotoIcon);
            JTextArea photoDetails = new JTextArea(photo.toString());
            photoDetails.setEditable(false);

            photoPanel.add(PhotoLabel, BorderLayout.CENTER); // will be bigger than description
            photoPanel.add(photoDetails, BorderLayout.SOUTH);
//            JSeparator separator = new JSeparator(JSeparator.HORIZONTAL); // it's too big but also may be

            allPhotosPanel.add(photoPanel);
//            allPhotosPanel.add(separator); //don't look good better without
        }

        allPhotosFrame.add(scrollPane);
        allPhotosFrame.setVisible(true);
    }
}
