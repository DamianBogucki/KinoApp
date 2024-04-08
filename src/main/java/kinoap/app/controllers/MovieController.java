package kinoap.app.controllers;

import jakarta.validation.Valid;
import kinoap.app.Entity.FilmShow;
import kinoap.app.Entity.Movie;
import kinoap.app.Exceptions.MovieAlreadyExistsException;
import kinoap.app.Exceptions.MovieNotFoundException;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.MovieRequestDto;
import kinoap.app.dtoObjects.ResponseDto.MovieResponseDto;
import kinoap.app.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    public MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(){
        List<MovieResponseDto> allMovies = movieService.findAllMovies().stream().map((Mapper::movieToResponseDto)).collect(Collectors.toList());
        return new ResponseEntity<>(allMovies,HttpStatus.OK);
    }

    @GetMapping("/getMovies")
    public ResponseEntity<Map<String,Object>> getAllMoviess(
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

        Page<Movie> movies;
        if (title == null){
            movies = movieService.findMoviesPage(pageable);
        } else {
            movies =  movieService.findMoviesPageByTitle(title,pageable);
        }

        Map<String,Object> response = new HashMap<>();


        response.put("movies",movies.getContent().stream().map(Mapper::movieToResponseDto).toList());
        response.put("currentPage",movies.getNumber());
        response.put("totalItems",movies.getTotalElements());
        response.put("totalPages",movies.getTotalPages());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getMovie/{movieId}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable long movieId) throws MovieNotFoundException{
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if (movie.isEmpty()){
            throw new MovieNotFoundException("There is no Movie with this id " + movieId);
        }
        MovieResponseDto movieResponseDto = Mapper.movieToResponseDto(movie.get());
        return new ResponseEntity<>(movieResponseDto,HttpStatus.OK);
    }

    @PostMapping("/addMovie")
    public ResponseEntity<?> addNewMovie(@RequestBody @Valid MovieRequestDto movieRequestDto){
        Optional<Movie> movie = movieService.findMovieByTitle(movieRequestDto.getTitle());
        if (movie.isPresent()){
            throw new MovieAlreadyExistsException("Movie with title " + movieRequestDto.getTitle() + " already exists");
        }
        movieService.saveMovie(movieRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editMovie/{movieId}")
    public ResponseEntity<MovieResponseDto> editFilm(@PathVariable long movieId, @RequestBody @Valid MovieRequestDto movieRequestDto){
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if (movie.isEmpty()){
            throw new MovieNotFoundException("There is no film with id of " + movieId);
        }
        MovieResponseDto movieResponseDto = Mapper.movieToResponseDto(movieService.updateMovie(movieId,movieRequestDto));
        return new ResponseEntity<>(movieResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/editMovie/{movieId}")
    public ResponseEntity<MovieResponseDto> editFilmVariables(@PathVariable long movieId, @RequestBody Map<String,Object> variablesToUpdate){
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if (movie.isEmpty()){
            throw new MovieNotFoundException("There is no film with id of " + movieId);
        }
        MovieResponseDto movieResponseDto = Mapper.movieToResponseDto(movieService.updateMovie(movieId,variablesToUpdate));
        return new ResponseEntity<>(movieResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/deleteMovie/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable long movieId){
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if (movie.isEmpty()){
            throw new MovieNotFoundException("There is no Movie with id of " + movieId);
        }
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public boolean checkIfUrlIsCorrect(int page, int size, String sortWay){
        List<String> sortProperites = new ArrayList<>(List.of("title","duration","avgRating"));
        if (page<0 || size<0 || !sortProperites.contains(sortWay.split(",")[0]) || !List.of("ASC","DESC").contains(sortWay.split(",")[1])){
            return false;
        }
        return true;
    }
}
