package tech.laye.tourisme_api.circuit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping("/circuit")
@RequiredArgsConstructor
public class CircuitController {

    private final CircuitService circuitService;


    @GetMapping
    public ResponseEntity<Page<CircuitResponse>> getAllCircuit(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size
    ){
        return ResponseEntity.ok(circuitService.getAllCircuit(page,size));
    }

    @GetMapping("/owner")
    public ResponseEntity<Page<CircuitResponse>> getMyCircuit(
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "10" , required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(circuitService.getMyCircuit(page,size,connectedUser));
    }

    @GetMapping("/{id}")
    public CircuitResponse getCircuit(@PathVariable Long id){
        return circuitService.getCircuit(id);
    }

    @PostMapping
    public ResponseEntity<Long> saveCircuit(
            @RequestBody @Valid CircuitRequest circuitRequest,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.circuitService.save(circuitRequest,connectedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateCircuit(
            @PathVariable Long id ,
            @RequestBody @Valid CircuitRequest circuitRequest,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        return ResponseEntity.ok(circuitService.updateCircuit(id,circuitRequest ,connectedUser));
    }

    @DeleteMapping("/{id}")
    public void deleteCircuit(
            @PathVariable Long id ,
            Authentication connectedUser
    ) throws OperationNotSupportedException {
        circuitService.deleteCircuit(id,connectedUser);
    }
}
