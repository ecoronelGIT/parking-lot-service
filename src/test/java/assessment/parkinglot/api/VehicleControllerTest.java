package assessment.parkinglot.api;

import assessment.parkinglot.BaseTest;
import assessment.parkinglot.common.enums.SpotType;
import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.exception.ErrorCode;
import assessment.parkinglot.common.route.Route;
import assessment.parkinglot.domain.entity.VehicleEntity;
import assessment.parkinglot.domain.repository.ParkingSpotRepository;
import assessment.parkinglot.domain.repository.VehicleRepository;
import jdk.jfr.Label;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;

@MockServerSettings(ports = 1080, perTestSuite = true)
public class VehicleControllerTest extends BaseTest {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @MockBean
    Clock clock;

    @Test
    @Label("park car vehicle with empty spots should save in database and return success")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void parkCarVehicleShouldReturnSuccess() throws Exception {
        String request = fileFromResources("request/park_car_vehicle_request.json");
        String response = fileFromResources("response/park_car_vehicle_response.json");

        setUpClock();

        mvc.perform(MockMvcRequestBuilders.post(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                    .contentType("application/json")
                    .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true))
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(1, vehicles.size());
                    VehicleEntity vehicle = vehicles.get(0);
                    Assertions.assertEquals(VehicleType.CAR, vehicle.getType());
                    Assertions.assertEquals("ARD123", vehicle.getNumber());
                    Assertions.assertEquals(1, vehicle.getParkingSpots().size());
                    Assertions.assertEquals(SpotType.COMPACT_CAR, vehicle.getParkingSpots().get(0).getType());
                });
    }

    @Test
    @Label("park motorcycle vehicle with empty spots should save in database and return success")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void parkMotorcycleVehicleShouldReturnSuccess() throws Exception {
        String request = fileFromResources("request/park_motorcycle_vehicle_request.json");
        String response = fileFromResources("response/park_motorcycle_vehicle_response.json");

        setUpClock();

        mvc.perform(MockMvcRequestBuilders.post(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                        .contentType("application/json")
                        .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true))
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(1, vehicles.size());
                    VehicleEntity vehicle = vehicles.get(0);
                    Assertions.assertEquals(VehicleType.MOTORCYCLE, vehicle.getType());
                    Assertions.assertEquals("ARD123", vehicle.getNumber());
                    Assertions.assertEquals(1, vehicle.getParkingSpots().size());
                    Assertions.assertEquals(SpotType.MOTORCYCLE, vehicle.getParkingSpots().get(0).getType());
                });
    }

    @Test
    @Label("park car vehicle with empty spots should save in database and return success")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_five_empty_parking_spots.sql")
    })
    public void parkVanVehicleShouldReturnSuccess() throws Exception {
        String request = fileFromResources("request/park_van_vehicle_request.json");
        String response = fileFromResources("response/park_van_vehicle_response.json");

        setUpClock();

        mvc.perform(MockMvcRequestBuilders.post(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                        .contentType("application/json")
                        .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true))
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(1, vehicles.size());
                    VehicleEntity vehicle = vehicles.get(0);
                    Assertions.assertEquals(VehicleType.VAN, vehicle.getType());
                    Assertions.assertEquals("ARD123", vehicle.getNumber());
                    Assertions.assertEquals(3, vehicle.getParkingSpots().size());
                    vehicle.getParkingSpots().forEach(spot -> Assertions.assertEquals(SpotType.REGULAR, spot.getType()));
                });

    }

    @Test
    @Label("park van vehicle with empty spots should save in database and return error")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void parkVanVehicleShouldReturnError() throws Exception {
        String request = fileFromResources("request/park_van_vehicle_request.json");

        setUpClock();

        mvc.perform(MockMvcRequestBuilders.post(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                        .contentType("application/json")
                        .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.PARKING_LOT_FULL))
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(0, vehicles.size());

                    Assertions.assertEquals(2, parkingSpotRepository.getAmountOfParkingSpotAvailable());
                });

    }

    @Test
    @Label("get vehicle with empty spots should return empty array")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void getVehicleShouldReturnEmptyArray() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_VEHICLES_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]", true));
    }

    @Test
    @Label("get vehicle with empty spots should return empty array")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_vehicles.sql"),
            @Sql(scripts = "/db/queries/insert_two_parking_spots.sql")
    })
    public void getVehicleShouldReturnTwoElements() throws Exception {
        String response = fileFromResources("response/get_vehicle_response.json");

        mvc.perform(MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_VEHICLES_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @Label("delete vehicle with should return return success")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_vehicles.sql"),
            @Sql(scripts = "/db/queries/insert_two_parking_spots.sql")
    })
    public void deleteVehicleShouldReturnSuccess() throws Exception {
        String request = fileFromResources("request/unpark_car_vehicle_request.json");

        mvc.perform(MockMvcRequestBuilders.delete(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                    .contentType("application/json")
                    .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(1, vehicles.size());
                    Assertions.assertEquals(1, parkingSpotRepository.getAmountOfParkingSpotAvailable());
                });
    }

    @Test
    @Label("delete vehicle with should return not found")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void deleteVehicleShouldReturnError() throws Exception {
        String request = fileFromResources("request/unpark_car_vehicle_request.json");

        mvc.perform(MockMvcRequestBuilders.delete(Route.BASE_PATH + Route.V1_VEHICLES_PATH)
                        .contentType("application/json")
                        .content(request)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.VEHICLE_NOT_FOUND));
    }

    @Test
    @Label("delete vehicle by id with should return return success")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_vehicles.sql"),
            @Sql(scripts = "/db/queries/insert_two_parking_spots.sql")
    })
    public void deleteVehicleByIdShouldReturnSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(Route.BASE_PATH + Route.V1_VEHICLES_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo((result) -> {
                    List<VehicleEntity> vehicles =  vehicleRepository.findAll();
                    Assertions.assertEquals(1, vehicles.size());
                    Assertions.assertEquals(1, parkingSpotRepository.getAmountOfParkingSpotAvailable());
                });
    }

    @Test
    @Label("delete vehicle by id with should return not found")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void deleteVehicleByIdShouldReturnError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(Route.BASE_PATH + Route.V1_VEHICLES_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.VEHICLE_NOT_FOUND));
    }

    private void setUpClock() throws ParseException {
        String dateString = "01/03/2024 12:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date parsedDate = dateFormat.parse(dateString);
        given(clock.instant()).willReturn(parsedDate.toInstant());
        given(clock.getZone()).willReturn(ZoneId.systemDefault());
    }


}
