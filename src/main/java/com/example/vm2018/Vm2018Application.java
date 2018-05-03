// I believe in you. You can do the thing!

/*
 * Copyright © 2018 Atomist, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.vm2018;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class Vm2018Application {

		@Bean
		ApplicationRunner runner(ReservationRepository reservationRepository) {
				return args -> Stream.of("Rod", "Josh").forEach(name -> reservationRepository.save(new Reservation(null, name)));
		}

		public static void main(String[] args) {
				SpringApplication.run(Vm2018Application.class, args);
		}
}

@RestController
class ReservationRestController {

		private final ReservationRepository reservationRepository;

		ReservationRestController(ReservationRepository reservationRepository) {
				this.reservationRepository = reservationRepository;
		}

		@GetMapping("/")
		String hi() {
				return "hi" ;
		}

		@GetMapping("/reservations")
		Collection<Reservation> reservations() {
				return this.reservationRepository.findAll();
		}
}

interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Reservation {

		@Id
		@GeneratedValue
		private Long id;
		private String name;
}
