package tech.laye.tourisme_api.accommodation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;


    @GetMapping
    public ResponseEntity<Page<AccommodationResponse>> getAllAcc(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(accommodationService.getAllAcc(page,size));
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<AccommodationResponse>> getMyAcc(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(accommodationService.getMyAcc(page,size,connectedUser));
    }

    @GetMapping("/{id}")
    public AccommodationResponse getAcc(@PathVariable Long id){
        return accommodationService.getAcc(id);
    }

    @PostMapping
    public ResponseEntity<Long> saveAcc(
            @RequestBody @Valid AccommodationRequest accommodationRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.accommodationService.save(accommodationRequest,connectedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateAcc(
            @PathVariable Long id ,
            @RequestBody @Valid AccommodationRequest accommodationRequest,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        return ResponseEntity.ok(accommodationService.updateAcc(id,accommodationRequest ,connectedUser));
    }

    @DeleteMapping("/{id}")
    public void deleteAcc(
            @PathVariable Long id ,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        accommodationService.deleteAcc(id,connectedUser);
    }
}
