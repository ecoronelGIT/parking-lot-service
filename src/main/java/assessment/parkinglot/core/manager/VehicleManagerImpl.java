package assessment.parkinglot.core.manager;


import assessment.parkinglot.common.enums.SpotType;
import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.exception.ApiException;
import assessment.parkinglot.common.exception.ErrorCode;
import assessment.parkinglot.common.exception.ErrorMessage;
import assessment.parkinglot.common.request.VehicleRequest;
import assessment.parkinglot.common.response.ParkingSpotResponse;
import assessment.parkinglot.common.response.VehicleResponse;
import assessment.parkinglot.core.service.ParkingSpotService;
import assessment.parkinglot.domain.entity.ParkingSpotEntity;
import assessment.parkinglot.domain.entity.VehicleEntity;
import assessment.parkinglot.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleManagerImpl implements VehicleManager {

    private final VehicleRepository vehicleRepository;
    private final ParkingSpotService parkingSpotService;
    private final Clock clock;

    public VehicleResponse parkVehicle(VehicleRequest vehicleRequest) {
        int availableSpot = parkingSpotService.getAmountOfParkingSpotAvailable(vehicleRequest.getVehicleType());
        if (availableSpot > 0) {
            VehicleEntity vehicleEntity = VehicleEntity.builder()
                    .type(vehicleRequest.getVehicleType())
                    .number(vehicleRequest.getVehicleNumber())
                    .parkDate(LocalDateTime.now(clock))
                    .build();
            List<ParkingSpotEntity> parkingSpots = getParkingSpotEntities(vehicleRequest.getVehicleType());
            parkingSpots.forEach(parkingSpot -> {
                parkingSpot.setVehicle(vehicleEntity);
                parkingSpot.setType(SpotType.getSpotTypeByVehicleType(vehicleRequest.getVehicleType()));
            });
            vehicleEntity.setParkingSpots(parkingSpots);
            vehicleRepository.save(vehicleEntity);
            return getVehicleResponse(vehicleEntity);
        }
        throw new ApiException(ErrorCode.PARKING_LOT_FULL, HttpStatus.BAD_REQUEST, ErrorMessage.PARKING_LOT_FULL);
    }

    public void unparkVehicle(VehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = vehicleRepository.getByNumber(vehicleRequest.getVehicleNumber())
                .orElseThrow(() -> new ApiException(ErrorCode.VEHICLE_NOT_FOUND, HttpStatus.NOT_FOUND, ErrorMessage.VEHICLE_NOT_FOUND));
        parkingSpotService.removeVehicle(vehicleEntity.getParkingSpots());
        vehicleRepository.delete(vehicleEntity);
    }

    public void unparkVehicleById(Long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VEHICLE_NOT_FOUND, HttpStatus.NOT_FOUND, ErrorMessage.VEHICLE_NOT_FOUND));
        parkingSpotService.removeVehicle(vehicleEntity.getParkingSpots());
        vehicleRepository.delete(vehicleEntity);
    }

    public List<VehicleResponse> getVehicles() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();
        return vehicleEntities.stream().map(this::getVehicleResponse).toList();
    }

    private List<ParkingSpotEntity> getParkingSpotEntities(VehicleType vehicleType) {
        List<ParkingSpotEntity> parkingSpots = parkingSpotService.getParkingSpotAvailable();
        return VehicleType.VAN.equals(vehicleType)?
                parkingSpots.stream().limit(3).toList():
                parkingSpots.stream().limit(1).toList();
    }

    private VehicleResponse getVehicleResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .id(vehicleEntity.getId())
                .vehicleNumber(vehicleEntity.getNumber())
                .vehicleType(vehicleEntity.getType())
                .vehicleParkDate(vehicleEntity.getParkDate())
                .parkingSpots(vehicleEntity.getParkingSpots()
                        .stream().map(spot -> ParkingSpotResponse.builder()
                                .id(spot.getId())
                                .spotNumber(spot.getNumber())
                                .spotType(spot.getType())
                                .build()
                        ).toList())
                .build();
    }

}
