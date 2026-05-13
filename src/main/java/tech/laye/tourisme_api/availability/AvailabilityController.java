package tech.laye.tourisme_api.availability;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;


    @GetMapping("/owner")
    public ResponseEntity<Page<AvailabilityResponse>> getMyAvailabilities(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(availabilityService.getMyAvailabilities(page,size,connectedUser));
    }

    @GetMapping("/{id}")
    public AvailabilityResponse getAvailability(@PathVariable Long id){
        return availabilityService.getAvailability(id);
    }


    @PostMapping
    public ResponseEntity<Long> saveAvailability(
            @RequestBody @Valid AvailabilityRequest availabilityRequest,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        return ResponseEntity.ok(this.availabilityService.save(availabilityRequest,connectedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateAvailability(
            @PathVariable Long id ,
            @RequestBody @Valid AvailabilityRequest availabilityRequest,
            Authentication connectedUser
    ) throws OperationNotSupportedException, AccessDeniedException {
        return ResponseEntity.ok(availabilityService.updateAvailability(id,availabilityRequest ,connectedUser));
    }
    @PatchMapping("/{id}/state")
    public ResponseEntity<Long> updateIsAvailable(
            @PathVariable Long id ,
            @RequestParam Boolean state,
            Authentication connectedUser
    ) throws OperationNotSupportedException, AccessDeniedException {
        return ResponseEntity.ok(availabilityService.updateIsAvailable(id,state ,connectedUser));
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(
            @PathVariable Long id ,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        availabilityService.deleteAvailability(id,connectedUser);
    }
}
