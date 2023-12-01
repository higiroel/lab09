
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

    public class DataStreamGUI extends JFrame {

        private JTextArea originalTextArea, filteredTextArea;
        private JTextField searchTextField;

        public DataStreamGUI() {
            initUI();
        }

        private void initUI() {
            // Set up the main frame
            setTitle("DataStreams");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Create components
            originalTextArea = new JTextArea();
            filteredTextArea = new JTextArea();
            searchTextField = new JTextField();

            // Create buttons
            JButton loadButton = new JButton("Load File");
            JButton searchButton = new JButton("Search");
            JButton quitButton = new JButton("Quit");

            // Add components to the frame
            add(new JScrollPane(originalTextArea), BorderLayout.WEST);
            add(new JScrollPane(filteredTextArea), BorderLayout.EAST);

            JPanel controlPanel = new JPanel(new FlowLayout());
            controlPanel.add(new JLabel("Search String: "));
            controlPanel.add(searchTextField);
            controlPanel.add(loadButton);
            controlPanel.add(searchButton);
            controlPanel.add(quitButton);

            add(controlPanel, BorderLayout.SOUTH);

            // Set up button actions
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadFile();
                }
            });

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchFile();
                }
            });

            quitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }

        private void loadFile() {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                Path filePath = fileChooser.getSelectedFile().toPath();
                try {
                    // Handle IOException by wrapping the file reading in a try-catch block
                    Stream<String> lines = Files.lines(filePath);
                    lines.forEach(line -> originalTextArea.append(line + "\n"));
                    lines.close(); // Close the stream
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void searchFile() {
            String searchString = searchTextField.getText().trim();

            if (!searchString.isEmpty()) {
                filteredTextArea.setText(""); // Clear previous results

                originalTextArea.getText()
                        .lines()
                        .filter(line -> line.contains(searchString))
                        .forEach(filteredTextArea::append);
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                DataStreamGUI gui = new DataStreamGUI();
                gui.setVisible(true);
            });
        }
    }

