package assessment.parkinglot.core.service;

import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.domain.entity.ParkingSpotEntity;
import assessment.parkinglot.domain.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ParkingSpotService {

    /**
     * Get the amount of parking spots available.
     *
     * @return the amount of parking spots available
     */
    int getAmountOfParkingSpotAvailable();

    /**
     * Get the amount of parking spots available for the given vehicle type.
     *
     * @param  vehicleType   the type of vehicle
     * @return               the amount of available parking spots
     */
    int getAmountOfParkingSpotAvailable(VehicleType vehicleType);

    /**
     * Get the list of available parking spots.
     *
     * @return         	List of available parking spots
     */
    List<ParkingSpotEntity> getParkingSpotAvailable();

    /**
     * Removes vehicles from the list of parking spots.
     *
     * @param  parkingSpots  the list of parking spots
     */
    void removeVehicle(List<ParkingSpotEntity> parkingSpots);
}
