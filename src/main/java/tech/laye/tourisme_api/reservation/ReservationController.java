package tech.laye.tourisme_api.reservation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getAllReservation(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(reservationService.getAllreservation(page,size));
    }

    @PostMapping
    public ResponseEntity<Long> saveReservation(
            @RequestBody @Valid ReservationRequest reservationRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.reservationService.save(reservationRequest,connectedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateReservation(
            @PathVariable Long id,
            @RequestBody @Valid ReservationRequest request,
            Authentication connectedUser
    ) throws AccessDeniedException {
        return ResponseEntity.ok(reservationService.update(id, request, connectedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id,
            Authentication connectedUser
    ) {
        reservationService.delete(id, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
