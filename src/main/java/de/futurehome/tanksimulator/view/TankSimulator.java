package de.futurehome.tanksimulator.view;

import de.futurehome.tanksimulator.controller.MyActionListener;
import de.futurehome.tanksimulator.model.Tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class TankSimulator extends Frame {

    // MODEL
    public Tank myTank = new Tank(0);

    // UI
    public Label lblFuellstand = new Label();
    public Label lblFuellstandinprozent = new Label();

    public Button btnBeenden = new Button("Beenden");
    public Button btnEinfuellen = new Button("Einfuellen");
    public Button btnVerbrauchen = new Button("Verbrauchen");
    public Button btnZuruecksetzen = new Button("Zuruecksetzen");

    public Scrollbar sliderVerbrauch =
            new Scrollbar(Scrollbar.HORIZONTAL, 1, 1, 1, 5);

    public JProgressBar progressBar =
            new JProgressBar(0, (int) Tank.MAX_FUELLSTAND);

    public TextArea logArea = new TextArea(6, 40);

    // CONTROLLER
    private MyActionListener controller = new MyActionListener(this);

    // FILE LOGGING
    private BufferedWriter logWriter;

    public TankSimulator() {
        super("Tank-Simulator");

        createLogFile();   // Create log file

        setLayout(new BorderLayout());

        Label title = new Label("Tank-Simulator", Label.CENTER);
        title.setFont(new Font("", Font.BOLD, 16));

        Panel north = new Panel();
        north.add(title);

        Panel center = new Panel(new GridLayout(5, 1));
        center.add(lblFuellstand);
        center.add(lblFuellstandinprozent);
        center.add(progressBar);

        Panel sliderPanel = new Panel(new FlowLayout());
        sliderPanel.add(new Label("Verbrauch (1–4 Liter):"));
        sliderPanel.add(sliderVerbrauch);
        center.add(sliderPanel);

        logArea.setEditable(false);
        center.add(logArea);

        Panel south = new Panel(new GridLayout(1, 4));
        south.add(btnEinfuellen);
        south.add(btnVerbrauchen);
        south.add(btnZuruecksetzen);
        south.add(btnBeenden);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        btnEinfuellen.addActionListener(controller);
        btnVerbrauchen.addActionListener(controller);
        btnZuruecksetzen.addActionListener(controller);
        btnBeenden.addActionListener(controller);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeLogFile();
                System.exit(0);
            }
        });

        updateUI();
        pack();
        setVisible(true);

        log("Programm gestartet");
    }

    // ---------------- UI UPDATE ----------------
    public void updateUI() {
        double f = myTank.getFuellstand();

        lblFuellstand.setText("Füllstand: " + f + " Liter");
        lblFuellstandinprozent.setText(
                String.format("Füllstand: %.0f %%", (f / Tank.MAX_FUELLSTAND) * 100)
        );

        progressBar.setValue((int) f);
    }

    // ---------------- LOGGING ----------------
    public void log(String message) {
        String time = java.time.LocalTime.now().withNano(0).toString();
        String line = "[" + time + "] " + message;

        // UI log
        logArea.append(line + "\n");

        // File log
        try {
            logWriter.write(line);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            logArea.append("LOG FILE ERROR\n");
        }
    }

    // ---------------- FILE HANDLING ----------------
    private void createLogFile() {
        try {
            // create logs folder if it doesn't exist
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            //  filename with timestamp
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

            String filename = "tank_log_" +
                    LocalDateTime.now().format(formatter) + ".txt";

            // create file inside logs folder
            File logFile = new File(logDir, filename);

            logWriter = new BufferedWriter(new FileWriter(logFile));

        } catch (IOException e) {
            System.err.println("Could not create log file");
        }
    }


    private void closeLogFile() {
        try {
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException ignored) {}
    }

    public static void main(String[] args) {
        new TankSimulator();
    }
}
