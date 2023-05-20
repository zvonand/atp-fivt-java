package nl.zvnv.task1;

import nl.zvnv.task1.Objects.CargoPier;
import nl.zvnv.task1.Objects.Tunnel;
import nl.zvnv.task1.Objects.ship.Ship;
import nl.zvnv.task1.Objects.util.CargoType;
import nl.zvnv.task1.Objects.util.ShipGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class App {
    private static final int SHIPS = 9;
    private static final int GENERATION_INTERVAL = 10;
    private static final int DEFAULT_TUNNEL_CAP = 5;
    private static final int DEFAULT_TUNNEL_PASSTHRU_MS = 1000;

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Tunnel tunnel = new Tunnel(DEFAULT_TUNNEL_CAP, DEFAULT_TUNNEL_PASSTHRU_MS);

        Map<CargoType, CargoPier> piers = new HashMap<>();
        for (CargoType cargoType : CargoType.values()) {
            piers.put(cargoType, new CargoPier(cargoType));
        }

        ShipGenerator shipGenerator = new ShipGenerator(tunnel, piers);
        ExecutorService pool = Executors.newFixedThreadPool(SHIPS);
        for (int i = 0; i < SHIPS; i++) {
            pool.execute(shipGenerator.generateShip());
            try {
                Thread.sleep(GENERATION_INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private App() {
    }
}