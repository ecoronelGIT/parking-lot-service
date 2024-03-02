package assessment.parkinglot.domain.repository;

import assessment.parkinglot.domain.entity.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {

    @Query(value = "select count(*) from parking_spot where vehicle_id is null", nativeQuery = true)
    int getAmountOfParkingSpotAvailable();

    @Query(value = "from ParkingSpotEntity where vehicle is null")
    List<ParkingSpotEntity> getParkingSpotAvailable();
}
