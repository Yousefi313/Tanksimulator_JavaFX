package de.futurehome.tanksimulator.controller;

import de.futurehome.tanksimulator.view.TankSimulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {

    private TankSimulator view;

    public MyActionListener(TankSimulator view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if (src == view.btnBeenden) {
            view.log("Programm beendet");
            System.exit(0);
        }

        if (src == view.btnEinfuellen) {
            view.myTank.fuellen();
            view.log("Einfüllen (+5 Liter)");
        }

        if (src == view.btnVerbrauchen) {
            int liter = view.sliderVerbrauch.getValue();
            view.myTank.verbrauchen(liter);
            view.log("Verbrauch (" + liter + " Liter)");
        }

        if (src == view.btnZuruecksetzen) {
            view.myTank.reset();
            view.log("Tank zurückgesetzt");
        }

        view.updateUI();
    }
}
