package nl.zvnv.task1.Objects.ship;

import nl.zvnv.task1.Objects.util.CargoType;
import nl.zvnv.task1.Objects.util.ShipVolume;

public interface IShip extends Runnable {
    /**
     * Get ship's volume
     * @return ShipVolume
     */
    ShipVolume getVolume();

    /**
     * Get ship's loaded cargo type
     * @return CargoType
     */
    CargoType getCargoType();

    /**
     * Load cargo onto the ship
     * @param value - goods quantity
     */
    void loadCargo(int value);

    /**
     * Pass a ship through tunnel
     * @param duration - passthrough duration
     */
    void passTunnel(int duration);
}
