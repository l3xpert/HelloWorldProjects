package nl.whitehorses.hellobeer.service;

import nl.whitehorses.hellobeer.generated.api.HelloBeerApiDelegate;
import nl.whitehorses.hellobeer.generated.model.Beer;
import nl.whitehorses.hellobeer.model.BeerType;
import nl.whitehorses.hellobeer.repository.HelloBeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class HelloBeerService implements HelloBeerApiDelegate {

    private static final Logger logger = LoggerFactory.getLogger(HelloBeerService.class);

    @Autowired
    private BeerTypeTransformer beerTypeTransformer;

    @Autowired
    private HelloBeerRepository helloBeerRepository;

    @Override
    public ResponseEntity<Beer> addToBeerRepositoryUsingPOST(Beer beer) {
        logger.info("POST");

        nl.whitehorses.hellobeer.model.Beer beerEO = helloBeerRepository.save(beerTypeTransformer.fromDTOtoEO(beer));
        return ResponseEntity.ok(beerTypeTransformer.fromEoToDto(beerEO));
    }

    @Override
    public ResponseEntity<List<Beer>> getAllBeersUsingGET(String type) {
        logger.info("POST");

        List<Beer> beerDTOs = new ArrayList<>();
        List<nl.whitehorses.hellobeer.model.Beer> beerEOs = new ArrayList<>();
        if (type == null) {
            beerEOs = helloBeerRepository.findAll();
        } else {
            beerEOs = helloBeerRepository.findByType(BeerType.valueOf(type));
        }

        beerDTOs = beerEOs.stream().map(beerTypeTransformer::fromEoToDto).collect(Collectors.toList());

        return ResponseEntity.ok(beerDTOs);
    }

}