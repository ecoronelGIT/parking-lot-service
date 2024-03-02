package assessment.parkinglot.api;

import assessment.parkinglot.common.request.VehicleRequest;
import assessment.parkinglot.common.response.VehicleResponse;
import assessment.parkinglot.common.route.Route;
import assessment.parkinglot.core.manager.VehicleManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Vehicle API")
@RestController
@RequiredArgsConstructor
@RequestMapping(Route.BASE_PATH)
public class VehicleController {

    private final VehicleManager vehicleManager;

    /**
     * A method to park a vehicle using the given vehicle request.
     *
     * @param  vehicleRequest	request containing vehicle information
     * @return                	response containing information about the parked vehicle
     */
    @Operation(description = "Park a vehicle", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Park vehicle and return the result",
                    content = { @Content(
                            schema = @Schema(implementation = VehicleResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(examples = {
                            @ExampleObject(
                                    description = "Parkin lot is full",
                                    value = "{\"code\": \"parking.lot.full\", \"message\":\"The parking lot is full\"}")
                    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping(Route.V1_VEHICLES_PATH)
    public VehicleResponse parkVehicle(@RequestBody VehicleRequest vehicleRequest) {
        return vehicleManager.parkVehicle(vehicleRequest);
    }

    /**
     * Unpark vehicle from the specified route.
     *
     * @param  vehicleRequest   the request object for the vehicle to be unparked
     */
    @Operation(description = "Unpark vehicle", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Unpark vehicle and return the result",
                    content = { @Content(
                            schema = @Schema(implementation = VehicleResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(examples = {
                            @ExampleObject(
                                    description = "Vehicle not found",
                                    value = "{\"code\": \"parking.lot.vehicle_not_found\", \"message\":\"The vehicle is not found\"}")
                    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping(Route.V1_VEHICLES_PATH)
    public void unparkVehicle(@RequestBody VehicleRequest vehicleRequest) {
        vehicleManager.unparkVehicle(vehicleRequest);
    }

    /**
     * Unparks a vehicle by its ID.
     *
     * @param  vehicleId   the ID of the vehicle to unpark
     */
    @Operation(description = "Unpark vehicle by id", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Unpark vehicle by id and return the result",
                    content = { @Content(
                            schema = @Schema(implementation = VehicleResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(examples = {
                            @ExampleObject(
                                    description = "Vehicle not found",
                                    value = "{\"code\": \"parking.lot.vehicle_not_found\", \"message\":\"The vehicle is not found\"}")
                    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping(Route.V1_VEHICLES_ID_PATH)
    public void unparkVehicleById(@PathVariable("id") Long vehicleId) {
        vehicleManager.unparkVehicleById(vehicleId);
    }

    /**
     * Retrieves a list of vehicle responses.
     *
     * @return         	list of vehicle responses
     */
    @Operation(description = "Get all vehicles", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get all vehicles in the parking lot",
                    content = { @Content(
                            schema = @Schema(implementation = VehicleResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            )
    })
    @GetMapping(Route.V1_VEHICLES_PATH)
    public List<VehicleResponse> getVehicles() {
        return vehicleManager.getVehicles();
    }
}
