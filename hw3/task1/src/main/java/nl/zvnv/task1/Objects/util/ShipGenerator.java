package nl.zvnv.task1.Objects.util;

import nl.zvnv.task1.Objects.CargoPier;
import nl.zvnv.task1.Objects.Tunnel;
import nl.zvnv.task1.Objects.ship.IShip;
import nl.zvnv.task1.Objects.ship.Ship;

import java.util.Map;
import java.util.Random;

public class ShipGenerator {
    private int nextShipId = 0;
    private final Tunnel tunnel;
    private final Map<CargoType, CargoPier> piers;

    public ShipGenerator(Tunnel tunnel, Map<CargoType, CargoPier> piers) {
        this.tunnel = tunnel;
        this.piers = piers;
    }

    /**
     * Generates a ship
     * @return Newly generated Ship
     */
    public synchronized IShip generateShip() {
        CargoType cargoType = CargoType.values()[new Random().nextInt(CargoType.values().length)];
        ShipVolume shipVolume = ShipVolume.values()[new Random().nextInt(ShipVolume.values().length)];

        return new Ship(nextShipId++, cargoType, shipVolume, tunnel, piers.get(cargoType));
    }
}
