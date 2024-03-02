package assessment.parkinglot.api;

import assessment.parkinglot.BaseTest;
import assessment.parkinglot.common.constants.Constants;
import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.route.Route;
import jdk.jfr.Label;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockServerSettings(ports = 1080, perTestSuite = true)
class ParkingSpotControllerTest extends BaseTest {

    @Test
    @Label("get parking spot available should return 2 spots")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void getParkingSpotAvailableShouldReturnTwo() throws Exception {
        String response = fileFromResources("response/two_spots_available_response.json");

        mvc.perform(MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_SPOT_AVAILABLE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @Label("get parking spot available should return 0 spots")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_vehicles.sql"),
            @Sql(scripts = "/db/queries/insert_full_parking_spots.sql")
    })
    public void getParkingSpotShouldReturnEmpty() throws Exception {
        String response = fileFromResources("response/empty_spots_available_response.json");

        mvc.perform(MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_SPOT_AVAILABLE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @Label("get parking spot available by CAR should return 2 spots")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void getParkingSpotByCarShouldReturnTwo() throws Exception {
        String response = fileFromResources("response/two_spots_available_response.json");

        mvc.perform(
                MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_SPOT_PATH)
                        .queryParam(Constants.VEHICLE_TYPE, VehicleType.CAR.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @Label("get parking spot available by VAN should return 0 spots")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_two_empty_parking_spots.sql")
    })
    public void getParkingSpotByVanShouldReturnZero() throws Exception {
        String response = fileFromResources("response/empty_spots_available_response.json");

        mvc.perform(
                MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_SPOT_PATH)
                    .queryParam(Constants.VEHICLE_TYPE, VehicleType.VAN.name())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @Label("get parking spot available by VAN should return 0 spots")
    @SqlGroup({
            @Sql(scripts = "/db/queries/delete_parking_spots.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/delete_vehicles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/db/queries/insert_five_empty_parking_spots.sql")
    })
    public void getParkingSpotByVanShouldReturnOne() throws Exception {
        String response = fileFromResources("response/one_spots_available_response.json");

        mvc.perform(
                MockMvcRequestBuilders.get(Route.BASE_PATH + Route.V1_SPOT_PATH)
                    .queryParam(Constants.VEHICLE_TYPE, VehicleType.VAN.name())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response, true));
    }
}
