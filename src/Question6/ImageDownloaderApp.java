package Question6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/*
 * This Swing application facilitates the downloading of images, offering four text fields for users to input image
 *  URLs. Utilizing threads allows for the simultaneous download of all four images. This concurrent downloading
 *  ensures that each task runs in parallel without hindering the others. A thread pool with three threads 
 * has been configured for this purpose. When downloading four images concurrently, the first three are assigned 
 * to the available threads for immediate downloading. Upon the completion of any one thread's task, it proceeds 
 * to download the fourth image, ensuring efficient use of resources and minimizing waiting time.
 */

public class ImageDownloaderApp extends JFrame {
    // Declaration of UI elements
    private JTextField urlTextField;
    private JButton downloadButton;
    private JButton pauseButton;
    private JButton cancelButton;
    private JProgressBar progressBar;
    private JPanel imagePanel;
    //Executor service provides a framework to execute asynchronous tasks
    // Asynchronous tasks are those tasks which can run in background, concurrently with other tasks
    private ExecutorService executorService;
    // isPaused variable used to check if the download process is paused or not
    private boolean isPaused;
    // isCanceled variable used to check if the download process is canceled or not
    private boolean isCanceled;
    private JButton downloadButton1;
    private JButton downloadButton2;
    private JButton downloadButton3;
    private JButton downloadButton4;

    public ImageDownloaderApp() {
        setTitle("Image Downloader");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setupLayout();
        setupListeners();

        // executorService is created with a fixed size thread pool consisting of 3 threads
        // With three threads, they each execute a particular task
        // In the case of there being multiple tasks, until the threads are busy, the rest of the tasks are kept in a queue
        // The threads are reused here, so once a thread executes a task, it moves on to the next in the queue
        executorService = Executors.newFixedThreadPool(3);
        isPaused = false;
        isCanceled = false;
    }

    // This function initializes the UI elements
    private void initComponents() {
        urlTextField = new JTextField(40);
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause");
        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 4, 10, 10));

        // Set fonts and styles
        Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        downloadButton.setFont(buttonFont);
        pauseButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        // Show progress string
        progressBar.setStringPainted(true);
        progressBar.setFont(buttonFont);
        urlTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    }

    // This function sets up the layout of the main frame of the application
    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = createInputPanel();
        JPanel progressPanel = createProgressPanel();
        JPanel centerPanel = createCenterPanel();

        mainPanel.add(inputPanel);
        mainPanel.add(progressPanel);
        mainPanel.add(centerPanel);

        // Add some padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(mainPanel);
    }

    // Function to set up input panel of the application
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 1)); // Use a grid layout to stack components vertically

        // First URL input sub-panel
        JPanel urlPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel1 = new JLabel("URL: ");
        JTextField urlTextField1 = new JTextField(40);
        downloadButton1 = new JButton("Download");

        urlPanel1.add(urlLabel1);
        urlPanel1.add(urlTextField1);
        urlPanel1.add(downloadButton1);

        // An action listener is added to the download button, which on clicking, the actionPerformed method is triggered
        downloadButton1.addActionListener(new ActionListener() {
            @Override
            // This function extracts the url from the text field and is passed as argument, with the button, to the function: downloadImage
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField1.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton1);
                    // Clear text field after download
                    urlTextField1.setText("");
                    // Reset progress bar
                    progressBar.setValue(0);
                    // Disable download button during download
                    downloadButton1.setEnabled(false);
                }
            }
        });

        // Second URL input sub-panel
        JPanel urlPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel2 = new JLabel("URL: ");
        JTextField urlTextField2 = new JTextField(40);
        downloadButton2 = new JButton("Download");

        urlPanel2.add(urlLabel2);
        urlPanel2.add(urlTextField2);
        urlPanel2.add(downloadButton2);

        downloadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField2.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton2);
                    urlTextField2.setText("");
                    progressBar.setValue(0);
                    downloadButton2.setEnabled(false);
                }
            }
        });

        // Third url input sub-panel
        JPanel urlPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel3 = new JLabel("URL: ");
        JTextField urlTextField3 = new JTextField(40);
        downloadButton3 = new JButton("Download");

        urlPanel3.add(urlLabel3);
        urlPanel3.add(urlTextField3);
        urlPanel3.add(downloadButton3);

        downloadButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField3.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton3);
                    urlTextField3.setText("");
                    progressBar.setValue(0);
                    downloadButton3.setEnabled(false);
                }
            }
        });

        // Fourth url input sub-panel
        JPanel urlPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel urlLabel4 = new JLabel("URL: ");
        JTextField urlTextField4 = new JTextField(40);
        downloadButton4 = new JButton("Download");

        urlPanel4.add(urlLabel4);
        urlPanel4.add(urlTextField4);
        urlPanel4.add(downloadButton4);

        downloadButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField4.getText().trim();
                if (!url.isEmpty()) {
                    downloadImage(url, downloadButton4);
                    urlTextField4.setText("");
                    progressBar.setValue(0);
                    downloadButton4.setEnabled(false);
                }
            }
        });

        inputPanel.add(urlPanel1);
        inputPanel.add(urlPanel2);
        inputPanel.add(urlPanel3);
        inputPanel.add(urlPanel4);

        return inputPanel;
    }

    // Function to create panel with progress bar, pause button and cancel button
    private JPanel createProgressPanel() {
        JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressPanel.add(progressBar);
        progressPanel.add(pauseButton);
        progressPanel.add(cancelButton);
        return progressPanel;
    }

    // Function to create a central panel that displays the downloaded images
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        return centerPanel;
    }

    private void setupListeners() {
        // Action listener added to pause button which triggers actionPerformed function
        // that changes isPaused to true and changes the text of the button conditionally
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = !isPaused;
                pauseButton.setText(isPaused ? "Resume" : "Pause");
            }
        });

        // Action listener added to cancel button, connected to actionPerformed function which when triggered
        // isCanceled becomes true, the progress bar is set to 0, a pop-up message appears saying download canceled and then isCanceled is switched back to false
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCanceled = true;
                progressBar.setValue(0); // Reset progress bar
                isPaused = false;
                pauseButton.setText("Pause");
                downloadButton.setEnabled(true); // Enable download button
                JOptionPane.showMessageDialog(ImageDownloaderApp.this, "Download canceled.");
                isCanceled = false;
            }
        });
    }

    // Main function that downloads the given url via the given button
    private void downloadImage(String urlString, JButton downloadButton) {
        // This function assigns the task to available threads
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int totalBytesRead = 0;
                int fileSize = 0;

                try {
                    // A URL object is created from the given url, which is a string representation
                    URL url = new URL(urlString);

                    // A URLConnected object is created using the above URL object to open a connection to the provided url
                    // The object "connection" provides HTTP headers and input, output streams associated with the url connection
                    URLConnection connection = url.openConnection();

                    // .getContentLength retrieves the size of the resources in bytes
                    // On knowing the size of the resource, in this case, the size of the image
                    // it is easy to keep track of the progress of the download
                    fileSize = connection.getContentLength();

                    // connection.getInputStream() returns input stream, used to read data from source
                    // BufferedInputStream improves the functionality of read operations of input stream by reading data in larger chunks rather than smaller chunks
                    // the input stream is wrapped with BufferedInputStream
                    InputStream in = new BufferedInputStream(connection.getInputStream());

                    byte[] buf = new byte[1024];
                    int n;
                    totalBytesRead = 0;

                    // Create new ByteArrayOutputStream for each download
                    // Used to write data to memory
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    // .read method reads data from input stream "in" into array "buf"
                    // the number of bytes is assigned to variable n
                    // The loop continues as long as n != -1 which means there aren't any more bytes remaining
                    while (-1 != (n = in.read(buf))) {
                        // If isCanceled is true, or the current thread executing the task is interrupted,
                        // the input and output streams are closed, terminating read and write operations
                        if (isCanceled || Thread.currentThread().isInterrupted()) {
                            in.close();
                            out.close();

                            //SwingUtilities.invokeLater: safely updates Swing components from background threads by queuing the update operation on the EDT (Event Dispatch Thread)
                            // EDT is a dedicated thread that handles the GUI events, updates Swing components
                            // prevents any concurrency issues that could arise from modifying Swing components from non-EDT threads.
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    downloadButton.setEnabled(true); // Re-enable the download button
                                }
                            });
                            return;
                        }

                        // Wait until the pause is lifted
                        while (isPaused) {
                            try {
                                // While paused, the execution of the current thread is paused for 100 milliseconds
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        // The bytes from buf array are written to output stream
                        out.write(buf, 0, n);
                        // keeping track of total bytes read uptil now
                        totalBytesRead += n;
                        // Calculation of the progress of the download via percentage
                        int progress = (int) ((double) totalBytesRead / fileSize * 100);

                        // Updates the progress bar
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                progressBar.setValue(progress);
                            }
                        });
                    }

                    // After download has finished, the output and input streams are closed to avoid data leaks
                    out.close();
                    in.close();
                    // Then the bytes written to output stream are stored in "response" array
                    final byte[] response = out.toByteArray();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //ImageIcon instance that holds the image data contained in the byte array
                            ImageIcon imageIcon = new ImageIcon(response);
                            // The image is displayed in the image label, which is added to a panel
                            JLabel imageLabel = new JLabel(imageIcon);
                            imagePanel.add(imageLabel);
                            JOptionPane.showMessageDialog(null, "Image downloaded successfully from URL: " + urlString);
                            // to properly reflect changes to the UI
                            revalidate();
                            // to makes UI changes immediately visible
                            repaint();
                            downloadButton.setEnabled(true); // Re-enable download button after download completes

                            try {
                                // An input stream is created from response array and the bytes are read in BufferedImage object
                                // BufferedImage is a class that represents images stored in memory
                                BufferedImage image = ImageIO.read(new ByteArrayInputStream(response));
                                saveImage(image);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (IOException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "Failed to download image from URL: " + urlString + "\nError: " + e.getMessage());
                            downloadButton.setEnabled(true);
                        }
                    });
                    e.printStackTrace();
                }
                finally {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(0); // Reset progress bar
                        }
                    });
                }
            }
        });
    }

    private void saveImage(BufferedImage image) {
        try {
            // The folder where the image is to be downloaded
            String downloadsFolder = System.getProperty("user.home") + File.separator + "Downloads";

            // The timestamp of download
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            // Creation of a unique name for the download using timestamp and appending .png extension
            String fileName = dateFormat.format(new Date()) + ".png";

            // outPath represents the complete path to the ouput file using the filename and folder path
            Path outputPath = Paths.get(downloadsFolder, fileName);
            // The image is written to the outPath
            ImageIO.write(image, "png", outputPath.toFile());

        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ImageDownloaderApp imageDownloader = new ImageDownloaderApp();
                imageDownloader.setLocationRelativeTo(null);
                imageDownloader.setVisible(true);
            }
        });
    }
}