package kinoap.app.controllers;

import jakarta.persistence.criteria.Order;
import jakarta.validation.Valid;
import kinoap.app.Entity.FilmShow;
import kinoap.app.ExceptionResponses.FilmShowErrorResponse;
import kinoap.app.Exceptions.FilmShowAlreadyExistsEx;
import kinoap.app.Exceptions.FilmShowNotExistsEx;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.FilmShowRequestDto;
import kinoap.app.dtoObjects.ResponseDto.FilmShowResponseDto;
import kinoap.app.services.FilmShowService;
import net.bytebuddy.description.type.TypeDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

@RestController
@RequestMapping("/filmShows")
public class FilmShowContoller {
    private FilmShowService filmShowService;

    public FilmShowContoller(FilmShowService filmShowService) {
        this.filmShowService = filmShowService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FilmShowResponseDto>> getAllFilmShows(){
        List<FilmShowResponseDto> allFilmShows = filmShowService.findAllFilmShows().stream().map(Mapper::filmShowToResponseDto).toList();
        return new ResponseEntity<>(allFilmShows,HttpStatus.OK);
    }

    @GetMapping("/getFilmShows")
    public ResponseEntity<Map<String,Object>> getFilmShowsPage(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,ASC") String sortWay){

        Pageable pageable;

        if (checkIfUrlIsCorrect(page,size,sortWay)){
            pageable = PageRequest.of(page,size, Sort.by(
                    new Sort.Order(Sort.Direction.valueOf(sortWay.split(",")[1]),sortWay.split(",")[0])));
        } else {
            pageable = PageRequest.of(0,10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
        }

        Page<FilmShow> filmShows;
        if (title == null){
            filmShows = filmShowService.findFilmShowsPage(pageable);
        } else {
            filmShows =  filmShowService.findFilmShowsPageByTitle(title,pageable);
        }

        Map<String,Object> response = new HashMap<>();

        response.put("filmShows",filmShows.getContent().stream().map(Mapper::filmShowToResponseDto).toList());
        response.put("currentPage",filmShows.getNumber());
        response.put("totalItems",filmShows.getTotalElements());
        response.put("totalPages",filmShows.getTotalPages());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/getFilmShow/{filmShowId}")
    public ResponseEntity<FilmShowResponseDto> getFilmShow(@PathVariable long filmShowId){
        Optional<FilmShow> filmShowOpt = filmShowService.findFilmShowById(filmShowId);
        if (filmShowOpt.isEmpty()){
            throw new FilmShowNotExistsEx("There is no film show with this id " + filmShowId);
        }
        FilmShowResponseDto filmShowResponseDto = Mapper.filmShowToResponseDto(filmShowService.findFilmShowById(filmShowId).get());
        return new ResponseEntity<>(filmShowResponseDto,HttpStatus.OK);
    }

    @PostMapping("/addFilmShow")
    public ResponseEntity<?> addNewFilmShow(@RequestBody @Valid FilmShowRequestDto filmShowRequestDto){
        Optional<FilmShow> filmShowOpt = filmShowService.findFilmShow(filmShowRequestDto);
        if (filmShowOpt.isPresent()){
            throw new FilmShowAlreadyExistsEx("Film Show already exists");
        }
        filmShowService.saveFilmShow(filmShowRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editFilmShow/{filmShowId}")
    public ResponseEntity<FilmShowResponseDto> editFilmShow(@PathVariable long filmShowId,@RequestBody @Valid FilmShowRequestDto filmShowRequestDto){
        Optional<FilmShow> filmShowOpt = filmShowService.findFilmShowById(filmShowId);
        if (filmShowOpt.isEmpty()){
            throw new FilmShowNotExistsEx("There is no film show with this id " + filmShowId);
        }
        FilmShowResponseDto filmShowResponseDto = Mapper.filmShowToResponseDto(filmShowService.updateFilmShow(filmShowId,filmShowRequestDto));
        return new ResponseEntity<>(filmShowResponseDto,HttpStatus.OK);
    }


    @DeleteMapping("/deleteFilmShow/{filmShowId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFilmShow(@PathVariable long filmShowId){
        Optional<FilmShow> filmShowOpt = filmShowService.findFilmShowById(filmShowId);
        if (filmShowOpt.isEmpty()){
            throw new FilmShowNotExistsEx("There is no film with id of " + filmShowId);
        }
        filmShowService.deleteFilmShow(filmShowId);
    }

    public boolean checkIfUrlIsCorrect(int page, int size, String sortWay){
        List<String> sortProperites = new ArrayList<>(List.of("title","duration","avgRating"));
        if (page<0 || size<0 || !sortProperites.contains(sortWay.split(",")[0]) || !List.of("ASC","DESC").contains(sortWay.split(",")[1])){
            return false;
        }
        return true;
    }
}



