package nl.zvnv.task1.Objects;

import nl.zvnv.task1.Objects.ship.IShip;

import java.util.concurrent.Semaphore;

public class Tunnel {
    private final Semaphore semaphore;
    private final int duration;

    public Tunnel(int capacity, int duration) {
        semaphore = new Semaphore(capacity);
        this.duration = duration;
    }

    public void goThrough(IShip ship) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException("Lock acquire failed: " + e);
        }
        ship.passTunnel(duration);
        semaphore.release();
    }
}
