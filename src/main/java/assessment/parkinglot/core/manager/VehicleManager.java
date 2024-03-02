package assessment.parkinglot.core.manager;


import assessment.parkinglot.common.request.VehicleRequest;
import assessment.parkinglot.common.response.VehicleResponse;

import java.util.List;

public interface VehicleManager {

    /**
     * Parks a vehicle based on the given vehicle request.
     *
     * @param  vehicleRequest  the request containing information about the vehicle
     * @return                 the response indicating the result of the parking operation
     */
    VehicleResponse parkVehicle(VehicleRequest vehicleRequest);

    /**
     * Unparks the specified vehicle request.
     *
     * @param  vehicleRequest   the vehicle request to unpark
     */
    void unparkVehicle(VehicleRequest vehicleRequest);

    /**
     * Unparks a vehicle by its ID.
     *
     * @param  vehicleId  the ID of the vehicle to unpark
     */
    void unparkVehicleById(Long vehicleId);

    /**
     * Retrieves a list of vehicle responses.
     *
     * @return  a list of vehicle responses
     */
    List<VehicleResponse> getVehicles();

}
