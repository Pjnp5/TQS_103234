package tqs.lab3.lab3_2.ControllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.lab3.lab3_2.JU;
import tqs.lab3.lab3_2.controller.Controller;
import tqs.lab3.lab3_2.models.Car;
import tqs.lab3.lab3_2.service.CarService;

import java.util.Arrays;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Controller.class)
class CarControllerTest {
	@Autowired
	MockMvc mvc;
	@MockBean
	private CarService carService;
	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void whenGetCars_thenReturnAllCars() throws  Exception {
		Car car_1 = new Car("Series 1", "BMW");
		Car car_2 = new Car("A5", "Audi");
		when(carService.getAllCars()).thenReturn(Arrays.asList(car_1,car_2));
		mvc.perform(get("/api/v1/cars").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
				.andExpect(jsonPath("$[0].model", is("Series 1")))
				.andExpect(jsonPath("$[1].model", is("A5")));
	}

	@Test
	public void whenGetCar_thenReturnCar() throws Exception {
		when(carService.getCarDetails(anyLong())).thenReturn(
				java.util.Optional.of(
						new Car("Series 1", "BMW"))
		);
		mvc.perform(get("/api/v1/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.model", is("Series 1")));

	}


	@Test
	public void whenPostCar_thenCreateCar() throws Exception {
		Car bmw = new Car("Series 1", "BMW");
		when( carService.saveCar(Mockito.any() )
		).thenReturn(bmw);
		mvc.perform(post("/api/v1/cars")
						.contentType(MediaType.APPLICATION_JSON).content(JU.toJson(bmw)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.model", is("Series 1")));

		verify(carService, times(1)).saveCar(Mockito.any());
	}



}