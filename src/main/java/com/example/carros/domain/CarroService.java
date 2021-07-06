package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList()); //lambda
    }

    public Optional<CarroDTO> getCarroById(Long id) {
       return rep.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO save(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir um registro!");

        return CarroDTO.create(rep.save
                (carro));
    }

    public boolean delete(Long id) {
        Optional<CarroDTO> carro = getCarroById(id);
        if (carro.isPresent()){
            rep.deleteById(id);
            return true;
        }else{
            return false;
        }

    }

    public CarroDTO update(Carro carro, Long id){
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id "+ db.getId());
            rep.save(db);

            return CarroDTO.create(db);
        }else{
            return null;
        }

    }

//    public List<Carro> getCarrosFake(){
//        List<Carro> carros = new ArrayList<>();
//
//        carros.add(new Carro(1L,"Fusca"));
//        carros.add(new Carro(2L,"Gol"));
//        carros.add(new Carro(3L,"Palio"));
//
//        return carros;
//    }



}
