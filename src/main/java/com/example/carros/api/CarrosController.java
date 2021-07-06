package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

	@Autowired
	private CarroService service;

	@GetMapping()
	public ResponseEntity<List<CarroDTO>> get() {
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	public ResponseEntity getId(@PathVariable("id") Long id) {
		Optional<CarroDTO> carros = service.getCarroById(id);

		//lambdas
		return carros
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

		//if ternario
//		return carros.isPresent() ?
//				ResponseEntity.ok(carros.get()) :
//				ResponseEntity.notFound().build();

		// conceito normal
//		if (carros.isPresent()) {
//			return ResponseEntity.ok(carros.get());
//		}
//		else
//		{
//			return ResponseEntity.notFound().build();
//		}

	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarrosByTipo(tipo);
		return carros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
	}

	@PostMapping
	public ResponseEntity post(@RequestBody Carro carro) {

		try{
			CarroDTO c = service.save(carro);

			URI location = getUri(c.getId());
			return ResponseEntity.created(location).build();
		}catch (Exception ex){
			return ResponseEntity.badRequest().build();
		}
	}

	private URI getUri (Long id){
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable Long id, @RequestBody Carro carro) {

		carro.setId(id);

		CarroDTO c = service.update(carro, id);

		return c != null ?
				ResponseEntity.ok(c):
				ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		boolean ok = service.delete(id);
		return ok ?
				ResponseEntity.ok().build():
				ResponseEntity.notFound().build();
	}












}
