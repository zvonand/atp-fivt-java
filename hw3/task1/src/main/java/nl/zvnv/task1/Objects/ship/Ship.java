package nl.zvnv.task1.Objects.ship;

import nl.zvnv.task1.Objects.CargoPier;
import nl.zvnv.task1.Objects.Tunnel;
import nl.zvnv.task1.Objects.util.CargoType;
import nl.zvnv.task1.Objects.util.ShipVolume;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static nl.zvnv.task1.Objects.ship.ShipStatus.*;

public class Ship implements IShip {
    private static final int LOAD_TIME = 100;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final int shipId;
    private final CargoType cargoType;
    private final ShipVolume volume;
    private Tunnel tunnel;
    private CargoPier cargoPier;
    private ShipStatus state;

    public Ship(int shipId, CargoType cargoType, ShipVolume volume, Tunnel tunnel, CargoPier cargoPier) {
        this.shipId = shipId;
        this.cargoType = cargoType;
        this.volume = volume;
        this.tunnel = tunnel;
        this.cargoPier = cargoPier;

        setShipStatus(EN_ROUTE);
    }

    @Override
    public void run() {
        setShipStatus(TUNNEL_QUEUE);
        tunnel.goThrough(this);
        cargoPier.checkAndLoad(this);
    }

    public void passTunnel(int duration) {
        if (state != TUNNEL_QUEUE) {
            return;
        }

        setShipStatus(IN_TUNNEL);

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception while sleep: " + e.getMessage());
        }

        setShipStatus(LOADING_QUEUE);
        tunnel = null;
    }

    public void loadCargo(int value) {
        if (state == FINISHED_LOADING || state == LOADING) {
            return;
        }

        setShipStatus(LOADING);

        try {
            Thread.sleep((long) LOAD_TIME * value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setShipStatus(FINISHED_LOADING);
        cargoPier = null;
    }

    public ShipVolume getVolume() {
        return volume;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    private void setShipStatus(ShipStatus state) {
        String time = DATE_FORMAT.format(Calendar.getInstance().getTime());
        String logEntry = String.format("[%s] | Ship %d | %-10s | %-7s | %-15s |",
                time, shipId, cargoType.toString(), volume.toString(), state.toString());
        System.out.println(logEntry);
        this.state = state;
    }
}
