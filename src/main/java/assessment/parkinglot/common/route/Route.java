package assessment.parkinglot.common.route;

public interface Route {

    String BASE_PATH = "/api";

    String V1_PATH = "/v1";
    String V1_SPOT_PATH = V1_PATH + "/spots";
    String V1_SPOT_AVAILABLE_PATH = V1_SPOT_PATH + "/available";

    String V1_VEHICLES_PATH = V1_PATH + "/vehicles";
    String V1_VEHICLES_ID_PATH = V1_VEHICLES_PATH + "/{id}";
}
